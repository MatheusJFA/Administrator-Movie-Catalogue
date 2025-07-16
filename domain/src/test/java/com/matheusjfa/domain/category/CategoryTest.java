package com.matheusjfa.domain.category;

import com.matheusjfa.domain.exceptions.DomainException;
import com.matheusjfa.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tags({
        @Tag("unit"),
        @Tag("domain"),
        @Tag("category")
})
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
    @DisplayName("Deve lançar uma exceção quando chamar o método 'validate' passando um nome vazio")
    public void givenAnInvalidCategoryWithEmptyName_whenCallingMethodCreate_thenThrowException() {
        // Arrange
        String expectedName = "";
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
        String expectedName = "ab";
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

}