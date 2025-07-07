package com.matheusjfa.domain.category;

import com.matheusjfa.domain.AggregateRoot;
import com.matheusjfa.domain.validation.ValidationHandler;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryID> {
	private String name;
	private String description;
	private boolean active;
	private Instant createdAt;
	private Instant updatedAt;
	private Instant deletedAt;

	private Category(
					final CategoryID id,
					final String name,
					final String description,
					final boolean active,
					final Instant createdAt,
					final Instant updatedAt,
					final Instant deletedAt
	) {
		super(id);
		this.name = name;
		this.description = description;
		this.active = active;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public static Category create(
					final String name,
					final String description,
					final boolean active
	) {
		final var id = CategoryID.generate();
		final var now = Instant.now();
		final var deletedAt = active ? null : now;

		return new Category(id, name, description, active, now, now, deletedAt);
	}

	public Category update(String newName, String newDescription, boolean expectedIsActive) {
		this.name = newName;
		this.description = newDescription;
		this.active = expectedIsActive;

		this.updatedAt = Instant.now();

		return this;
	}

	@Override
	public void validate(final ValidationHandler handler) {
		final var validator = new CategoryValidator(this, handler);
		validator.validate();
	}

	public Category activate() {
		if (this.deletedAt != null) {
			this.deletedAt = null;
		}

		this.active = true;
		this.updatedAt = Instant.now();

		return this;
	}

	public Category deactivate() {
		if (this.deletedAt == null) {
			this.deletedAt = Instant.now();
		}

		this.active = false;
		this.updatedAt = Instant.now();
		return this;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean isActive() {
		return active;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public Instant getDeletedAt() {
		return deletedAt;
	}
}
