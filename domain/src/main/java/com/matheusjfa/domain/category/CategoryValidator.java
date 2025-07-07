package com.matheusjfa.domain.category;

import com.matheusjfa.domain.validation.Failure;
import com.matheusjfa.domain.validation.Validation;
import com.matheusjfa.domain.validation.ValidationHandler;

public class CategoryValidator extends Validation {
	public static final int NAME_MAX_LENGTH = 255;
	public static final int NAME_MIN_LENGTH = 3;

	private final Category category;

	public CategoryValidator(Category category, ValidationHandler handler) {
		super(handler);
		this.category = category;
	}

	@Override
	public void validate() {
		checkNameConstraints();
	}

	private void checkNameConstraints() {
		final var name = this.category.getName();
		final var handler = this.getHandler();

		if (name == null){
			handler.append(new Failure("'Nome' não pode ser nulo."));
			return;
		}

		if (name.isBlank()){
			handler.append(new Failure("'Nome' não pode ser vazio."));
			return;
		}

		final int length = name.trim().length();

		if (length < NAME_MIN_LENGTH || length > NAME_MAX_LENGTH) {
			handler.append(new Failure("'Nome' deve ter no mínimo "+ NAME_MIN_LENGTH +" e "+ NAME_MAX_LENGTH +" caracteres."));
			return;
		}
	}
}
