package com.matheusjfa.domain.category;

import com.matheusjfa.domain.validation.ErrorMessage;
import com.matheusjfa.domain.validation.ValidationHandler;
import com.matheusjfa.domain.validation.Validator;

public class CategoryValidator extends Validator {
    private final int MIN_NAME_LENGTH = 3;
    private final int MAX_NAME_LENGTH = 255;

    private final Category category;

    public CategoryValidator(Category category, ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    protected void validate() throws Exception {
        checkNameConstraints();
    }

    private void checkNameConstraints() throws Exception {
        final var name = category.getName();
        final var handler = getHandler();

        if (name == null) {
            handler.append(new ErrorMessage("O nome da categoria não pode ser nulo"));
            return;
        }

        if (name.isBlank()) {
            handler.append(new ErrorMessage("O nome da categoria não pode ser vazio"));
            return;
        }

        final var nameLength = category.getName().trim().length();

        if (nameLength < MIN_NAME_LENGTH || nameLength > MAX_NAME_LENGTH) {
            handler.append(new ErrorMessage("O nome da categoria deve ter entre " + MIN_NAME_LENGTH + " e " + MAX_NAME_LENGTH + " caracteres"));
        }
    }
}
