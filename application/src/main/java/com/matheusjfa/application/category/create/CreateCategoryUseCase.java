package com.matheusjfa.application.category.create;

import com.matheusjfa.application.UseCase;
import com.matheusjfa.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {
}