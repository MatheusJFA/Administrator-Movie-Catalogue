package com.matheusjfa.domain.category;

import com.matheusjfa.domain.exceptions.DomainException;
import com.matheusjfa.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {
    @Test
    @DisplayName("Deve retornar uma categoria válida quando chamar o método 'create' passando dados válidos")
    public void givenAnValidCategory_whenCallingMethodCreate_thenReturnCategory() {
        // Arrange
        String expectedName = "A Category";
        String expectedDescription = "A Description";
        boolean expectedIsActive = true;

        // Act
        Category category = Category.create(expectedName, expectedDescription, expectedIsActive);

        // Assert
        assertNotNull(category);
        assertNotNull(category.getId());
        assertEquals(expectedName, category.getName());
        assertEquals(expectedDescription, category.getDescription());
        assertTrue(category.isActive());
        assertNotNull(category.getCreatedAt());
        assertNotNull(category.getUpdatedAt());
        assertNull(category.getDeletedAt());
    }


    @Test
    @DisplayName("Deve retornar uma categoria válida quando chamar o método 'create' passando dados válidos com descrição nula")
    public void givenAnValidCategoryWithoutDescription_whenCallingMethodCreate_thenReturnCategory() {
        // Arrange
        String expectedName = "A Category";
        String expectedDescription = null;
        boolean expectedIsActive = true;

        // Act
        Category category = Category.create(expectedName, expectedDescription, expectedIsActive);

        // Assert
        assertNotNull(category);
        assertNotNull(category.getId());
        assertEquals(expectedName, category.getName());
        assertNull(category.getDescription());
        assertTrue(category.isActive());
        assertNotNull(category.getCreatedAt());
        assertNotNull(category.getUpdatedAt());
        assertNull(category.getDeletedAt());
    }

    @Test
    @DisplayName("Deve retornar uma categoria válida quando chamar o método 'create' passando dados válidos com descrição vazia")
    public void givenAnValidCategoryWithEmptyDescription_whenCallingMethodCreate_thenReturnCategory() {
        // Arrange
        String expectedName = "A Category";
        String expectedDescription = "";
        boolean expectedIsActive = true;

        // Act
        Category category = Category.create(expectedName, expectedDescription, expectedIsActive);

        // Assert
        assertNotNull(category);
        assertNotNull(category.getId());
        assertEquals(expectedName, category.getName());
        assertEquals(expectedDescription, category.getDescription());
        assertTrue(category.isActive());
        assertNotNull(category.getCreatedAt());
        assertNotNull(category.getUpdatedAt());
        assertNull(category.getDeletedAt());
    }

    @Test
    @DisplayName("Deve retornar uma categoria inativa quando chamar o método 'create' passando dados válidos com isActive false")
    public void givenAnValidCategoryWithIsActiveFalse_whenCallingMethodCreate_thenReturnCategory() {
        // Arrange
        String expectedName = "A Category";
        String expectedDescription = "A Description";
        boolean expectedIsActive = false;

        // Act
        Category category = Category.create(expectedName, expectedDescription, expectedIsActive);

        // Assert
        assertNotNull(category);
        assertNotNull(category.getId());
        assertEquals(expectedName, category.getName());
        assertEquals(expectedDescription, category.getDescription());
        assertFalse(category.isActive());
        assertNotNull(category.getCreatedAt());
        assertNotNull(category.getUpdatedAt());
        assertNotNull(category.getDeletedAt());
    }

    @Test
    @DisplayName("Deve retornar uma categoria inativa quando chamar o método 'create' passando dados válidos com descrição vazia e isActive false")
    public void givenAnValidCategoryWithEmptyDescriptionAndIsActiveFalse_whenCallingMethodCreate_thenReturnCategory() {
        // Arrange
        String expectedName = "A Category";
        String expectedDescription = "";
        boolean expectedIsActive = false;

        // Act
        Category category = Category.create(expectedName, expectedDescription, expectedIsActive);

        // Assert
        assertNotNull(category);
        assertNotNull(category.getId());
        assertEquals(expectedName, category.getName());
        assertEquals(expectedDescription, category.getDescription());
        assertFalse(category.isActive());
        assertNotNull(category.getCreatedAt());
        assertNotNull(category.getUpdatedAt());
        assertNotNull(category.getDeletedAt());
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando chamar o método 'validate' passando um nome nulo")
    public void givenAnInvalidCategory_whenCallingMethodCreate_thenThrowException() {
        // Arrange
        String expectedName = null;
        String expectedDescription = "A Description";
        boolean expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);

        // Act
        final var exception = assertThrows(DomainException.class, () -> {
            category.validate(new ThrowsValidationHandler());
        });

        // Assert
        assertNotNull(exception);
        String expectedMessage = "O nome da categoria não pode ser nulo";
        assertEquals(expectedMessage, exception.getMessage());

        int expectedErrorCount = 1;
        assertEquals(expectedErrorCount, exception.getErrors().size());
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando chamar o método 'validate' passando um nome vazio (contendo apenas espaços)")
    public void givenAnInvalidCategoryWithEmptyName_whenCallingMethodCreate_thenThrowException() {
        // Arrange
        String expectedName = "   ";
        String expectedDescription = "A Description";
        boolean expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);

        // Act
        final var exception = assertThrows(DomainException.class, () -> {
            category.validate(new ThrowsValidationHandler());
        });

        // Assert
        assertNotNull(exception);
        String expectedMessage = "O nome da categoria não pode ser vazio";
        assertEquals(expectedMessage, exception.getMessage());

        int expectedErrorCount = 1;
        assertEquals(expectedErrorCount, exception.getErrors().size());
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando chamar o método 'validate' passando um nome com menos de 3 caracteres")
    public void givenAnInvalidCategoryWithShortName_whenCallingMethodCreate_thenThrowException() {
        // Arrange
        String expectedName = "ab ";
        String expectedDescription = "A Description";
        boolean expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);

        // Act
        final var exception = assertThrows(DomainException.class, () -> {
            category.validate(new ThrowsValidationHandler());
        });

        // Assert
        assertNotNull(exception);
        String expectedMessage = "O nome da categoria deve ter entre 3 e 255 caracteres";
        assertEquals(expectedMessage, exception.getMessage());

        int expectedErrorCount = 1;
        assertEquals(expectedErrorCount, exception.getErrors().size());
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando chamar o método 'validate' passando um nome com mais de 255 caracteres")
    public void givenAnInvalidCategoryWithLongName_whenCallingMethodCreate_thenThrowException() {
        // Arrange
        String expectedName = "a".repeat(256);
        String expectedDescription = "A Description";
        boolean expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);

        // Act
        final var exception = assertThrows(DomainException.class, () -> {
            category.validate(new ThrowsValidationHandler());
        });

        // Assert
        assertNotNull(exception);
        String expectedMessage = "O nome da categoria deve ter entre 3 e 255 caracteres";
        assertEquals(expectedMessage, exception.getMessage());

        int expectedErrorCount = 1;
        assertEquals(expectedErrorCount, exception.getErrors().size());
    }

    @Test
    @DisplayName("Não deve lançar uma exceção quando chamar o método 'validate' passando parametros válidos")
    public void givenAValidCategory_whenCallingMethodCreate_thenNotThrowException() {
        // Arrange
        String expectedName = "A Category";
        String expectedDescription = "A Description";
        boolean expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);

        // Act & Assert
        assertDoesNotThrow(() -> {
            category.validate(new ThrowsValidationHandler());
        });
    }

    @Test
    @DisplayName("Dado uma categoria activa, ao chamar o método 'deactivate', a categoria deve ser marcada como inativa")
    public void givenAValidCategory_whenCallingMethodDeactivate_thenCategoryShouldBeInactive() {
        // Arrange
        String expectedName = "A Category";
        String expectedDescription = "A Description";
        boolean expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);
        final var updatedAt = category.getUpdatedAt();

        assertTrue(category.isActive());
        assertNull(category.getDeletedAt());

        // Act
        final var deactivatedCategory = category.deactivate();
        final var updatedAtAfterDeactivation = deactivatedCategory.getUpdatedAt();

        // Assert
        assertFalse(category.isActive());
        assertNotNull(category.getDeletedAt());

        assertTrue(updatedAt.isBefore(updatedAtAfterDeactivation) || updatedAt.equals(updatedAtAfterDeactivation));
    }

    @Test
    @DisplayName("Dado uma categoria inativa, ao chamar o método 'activate', a categoria deve ser marcada como ativa")
    public void givenAValidInactiveCategory_whenCallingMethodActivate_thenCategoryShouldBeActive() {
        // Arrange
        String expectedName = "A Category";
        String expectedDescription = "A Description";
        boolean expectedIsActive = false;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);
        final var updatedAt = category.getUpdatedAt();

        assertFalse(category.isActive());
        assertNotNull(category.getDeletedAt());

        // Act
        final var activatedCategory = category.activate();
        final var updatedAtAfterActivation = activatedCategory.getUpdatedAt();

        // Assert
        assertTrue(category.isActive());
        assertNull(category.getDeletedAt());

        assertTrue(updatedAt.isBefore(updatedAtAfterActivation) || updatedAt.equals(updatedAtAfterActivation));
    }


    @Test
    @DisplayName("Dado uma categoria ativa, ao chamar o método 'activate', a categoria deve permanecer ativa")
    public void givenAnActiveCategory_whenCallingMethodActivate_thenCategoryShouldRemainActive() {
        // Arrange
        String expectedName = "A Category";
        String expectedDescription = "A Description";
        boolean expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);
        final var updatedAt = category.getUpdatedAt();
        final var deletedAt = category.getDeletedAt();

        assertTrue(category.isActive());
        assertNull(deletedAt);

        // Act
        final var activatedCategory = category.activate();
        final var updatedAtAfterActivation = activatedCategory.getUpdatedAt();

        // Assert
        assertTrue(activatedCategory.isActive());
        assertNull(activatedCategory.getDeletedAt());

        assertTrue(updatedAt.isBefore(updatedAtAfterActivation) || updatedAt.equals(updatedAtAfterActivation));
    }


    @Test
    @DisplayName("Dado uma categoria inativa, ao chamar o método 'deactivate', a categoria deve permanecer inativa")
    public void givenAnInactiveCategory_whenCallingMethodDeactivate_thenCategoryShouldRemainInactive() {
        // Arrange
        String expectedName = "A Category";
        String expectedDescription = "A Description";
        boolean expectedIsActive = false;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);
        final var updatedAt = category.getUpdatedAt();
        final var deletedAt = category.getDeletedAt();

        assertFalse(category.isActive());
        assertNotNull(deletedAt);

        // Act
        final var deactivatedCategory = category.deactivate();
        final var updatedAtAfterDeactivation = deactivatedCategory.getUpdatedAt();

        // Assert
        assertFalse(deactivatedCategory.isActive());
        assertNotNull(deactivatedCategory.getDeletedAt());

        assertTrue(updatedAt.isBefore(updatedAtAfterDeactivation) || updatedAt.equals(updatedAtAfterDeactivation));
    }

    @Test
    @DisplayName("Dado uma categoria válida, ao chamar o método 'update' com argumentos válidos, deve atualizar os campos corretamente")
    public void givenAValidCategory_whenCallingMethodUpdate_thenFieldsShouldBeUpdated() {
        // Arrange
        String expectedName = "A Category";
        String expectedDescription = "A Description";
        boolean expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);
        final var updatedAt = category.getUpdatedAt();
        assertDoesNotThrow(() -> {
            category.validate(new ThrowsValidationHandler());
        });

        // Act
        String updatedName = "Updated Name";
        String updatedDescription = "Updated Description";

        final var updatedCategory = category.update(updatedName, updatedDescription, false);
        final var updatedAtAfterUpdate = updatedCategory.getUpdatedAt();

        assertDoesNotThrow(() -> {
            updatedCategory.validate(new ThrowsValidationHandler());
        });

        // Assert
        assertNotNull(updatedCategory);
        assertEquals(updatedName, updatedCategory.getName());
        assertEquals(updatedDescription, updatedCategory.getDescription());
        assertFalse(updatedCategory.isActive());

        assertTrue(updatedAt.isBefore(updatedAtAfterUpdate) || updatedAt.equals(updatedAtAfterUpdate));
    }

    @Test
    @DisplayName("Dado uma categoria válida, ao chamar o método 'update' com argumentos inválidos, deve lançar uma exceção")
    public void givenAValidCategory_whenCallingMethodUpdateWithInvalidArguments_thenThrowException() {
        // Arrange
        String expectedName = "A Category";
        String expectedDescription = "A Description";
        boolean expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);
        final var updatedAt = category.getUpdatedAt();
        assertDoesNotThrow(() -> {
            category.validate(new ThrowsValidationHandler());
        });

        // Act & Assert
        String updatedName = null; // Invalid name
        String updatedDescription = "Updated Description";

        Category updatedCategory = category.update(updatedName, updatedDescription, false);

        final var exception = assertThrows(DomainException.class, () -> {
            updatedCategory.validate(new ThrowsValidationHandler());
        });

        assertNotNull(exception);
        String expectedMessage = "O nome da categoria não pode ser nulo";
        assertEquals(expectedMessage, exception.getMessage());

        int expectedErrorCount = 1;
        assertEquals(expectedErrorCount, exception.getErrors().size());
    }
}