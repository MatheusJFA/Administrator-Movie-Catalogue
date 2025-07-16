package com.matheusjfa.application.category.create;

import com.matheusjfa.domain.category.Category;
import com.matheusjfa.domain.category.CategoryGateway;
import com.matheusjfa.domain.validation.handler.Notification;
import io.vavr.control.Either;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway gateway;

    public DefaultCreateCategoryUseCase(CategoryGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(final CreateCategoryCommand command) throws Exception {
        final var name = command.name();
        final var description = command.description();
        final var isActive = command.isActive();

        final var notification = Notification.create();
        final var category = Category.create(name, description, isActive);
        category.validate(notification);

        return notification.hasErrors()
                ? Left(notification)
                : create(category);

    }

    private Either<Notification, CreateCategoryOutput> create(final Category category) {
        return Try(() -> this.gateway.create(category))
                .toEither()
                .bimap(Notification::create, CreateCategoryOutput::from);
    }
}
