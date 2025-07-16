package com.matheusjfa.application.category.update;

import com.matheusjfa.application.UseCase;
import com.matheusjfa.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {
}

