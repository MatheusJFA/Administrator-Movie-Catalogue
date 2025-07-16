package com.matheusjfa.application.category.update;

import com.matheusjfa.domain.category.Category;
import com.matheusjfa.domain.category.CategoryGateway;
import com.matheusjfa.domain.category.CategoryID;
import com.matheusjfa.domain.exceptions.DomainException;
import com.matheusjfa.domain.validation.ErrorMessage;
import com.matheusjfa.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.function.Supplier;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    private final CategoryGateway gateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway gateway) {
        this.gateway = gateway;
    }

    private static Supplier<Exception> notFound(CategoryID id) {
        return () -> DomainException.with(new ErrorMessage("Não foi encontrado nenhuma categoria com o ID %s".formatted(id.getValue())));
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand command) throws Exception {
        final var id = CategoryID.from(command.id());
        final var name = command.name();
        final var description = command.description();
        final var isActive = command.isActive();

        final var notification = Notification.create();

        final var category = this.gateway.findById(id)
                .orElseThrow(notFound(id));

        category.update(name, description, isActive);
        category.validate(notification);

        return notification.hasErrors() ? Left(notification) : update(category);
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category category) {
        return Try(() -> this.gateway.update(category)).toEither().bimap(Notification::create, UpdateCategoryOutput::from);
    }

}