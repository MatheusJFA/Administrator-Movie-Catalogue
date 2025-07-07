package com.matheusjfa.domain.category;

import com.matheusjfa.domain.exceptions.DomainException;
import com.matheusjfa.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
        /**
         * Cenários de teste:
         * 1. Sendo informado parametros válidos, deve criar uma categoria com sucesso.
         * 2. Sendo informado um nome vazio, deve lançar uma exceção de validação.
         * 3. Sendo informado um nome nulo, deve lançar uma exceção de validação.
         * 4. Sendo informado um nome com mais de 255 caracteres, deve lançar uma exceção de validação.
         * 5. Sendo informado um nome com menos de 3 caracteres, deve lançar uma exceção de validação.
         * 6. Sendo informado uma descrição vazia, deve criar uma categoria com sucesso, pois a descrição é opcional.
         * 7. Sendo informado uma descrição nula, deve criar uma categoria com sucesso, pois a descrição é opcional.
         * 8. Sendo informado uma categoria inativa, deve criar uma categoria com sucesso.
         * 9. Sendo informado uma categoria válida inativa, quando chamada o método activate, deve ativar a categoria com sucesso.
         * 10. Sendo informado uma categoria válida ativa, quando chamada o método deactivate, deve desativar a categoria com sucesso.
         * 11. Sendo informada uma categoria válida, ao chamar o método update, deve atualizar os dados da categoria com sucesso.
         * 12. Sendo informada uma categoria válida, ao chamar o método update com dados inválidos, deve lançar uma exceção de validação.
         * */

    @Test
    public void givenValidParams_whenCallsCreate_shouldReturnCategory() {
        final var expectedName = "Test Category";
        final var expectedDescription = "Test Description";
        final var expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);

        assertNotNull(category);
        assertNotNull(category.getId());
        assertEquals(expectedName, category.getName());
        assertEquals(expectedDescription, category.getDescription());
        assertEquals(expectedIsActive, category.isActive());
        assertNotNull(category.getCreatedAt());
        assertNotNull(category.getUpdatedAt());
        assertNull(category.getDeletedAt());
    }

    @Test
    public void givenAnEmptyName_whenCallsCreate_shouldReturnDomainException() {
        final String expectedName = "";
        final String expectedDescription = "Test Description";
        final boolean expectedIsActive = true;

        final var exception = assertThrows(DomainException.class, () ->
            Category.create(expectedName, expectedDescription, expectedIsActive).validate(new ThrowsValidationHandler())
        );

        String expectedMessage = "'Nome' não pode ser vazio.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenANullName_whenCallsCreate_shouldReturnDomainException() {
        final String expectedName = null;
        final String expectedDescription = "Test Description";
        final boolean expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);

        final var exception = assertThrows(DomainException.class, () ->
                category.validate(new ThrowsValidationHandler())
        );

        String expectedMessage = "'Nome' não pode ser nulo.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenNameWithMoreThan255Chars_whenCallsCreate_shouldReturnDomainException() {
        final String expectedName = "a".repeat(256);
        final String expectedDescription = "Test Description";
        final boolean expectedIsActive = true;

        final var exception = assertThrows(DomainException.class, () ->
            Category.create(expectedName, expectedDescription, expectedIsActive).validate(new ThrowsValidationHandler())
        );

        final var expectedMessage = "'Nome' deve ter no mínimo 3 e 255 caracteres.";
        assertEquals(expectedMessage, exception.getMessage());
        int expectedFailures = 1;
        assertEquals(expectedFailures, exception.getFailures().size());
    }

    @Test
    public void givenNameWithLessThan3Chars_whenCallsCreate_shouldReturnDomainException() {
        final String expectedName = "ab";
        final String expectedDescription = "Test Description";
        final boolean expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);

        final var exception = assertThrows(DomainException.class, () ->
            category.validate(new ThrowsValidationHandler())
        );

        final var expectedMessage = "'Nome' deve ter no mínimo 3 e 255 caracteres.";
        assertEquals(expectedMessage, exception.getMessage());

        int expectedFailures = 1;
        assertEquals(expectedFailures, exception.getFailures().size());
    }

    @Test
    public void givenAnEmptyDescription_whenCallsCreate_shouldReturnCategory() {
        final String expectedName = "Test Category";
        final String expectedDescription = "";
        final boolean expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);

        assertNotNull(category);
        assertEquals(expectedName, category.getName());
        assertEquals(expectedDescription, category.getDescription());
        assertTrue(category.isActive());
        assertNotNull(category.getCreatedAt());
        assertNotNull(category.getUpdatedAt());
        assertNull(category.getDeletedAt());
    }

    @Test
    public void givenANullDescription_whenCallsCreate_shouldReturnCategory() {
        final String expectedName = "Test Category";
        final String expectedDescription = null;
        final boolean expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);

        assertNotNull(category);
        assertEquals(expectedName, category.getName());
        assertNull(category.getDescription());
        assertTrue(category.isActive());
        assertNotNull(category.getCreatedAt());
        assertNotNull(category.getUpdatedAt());
        assertNull(category.getDeletedAt());
    }

    @Test
    public void givenAnInactiveCategory_whenCallsCreate_shouldReturnCategory() {
        final String expectedName = "Test Category";
        final String expectedDescription = "Test Description";
        final boolean expectedIsActive = false;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);

        assertNotNull(category);
        assertEquals(expectedName, category.getName());
        assertEquals(expectedDescription, category.getDescription());
        assertFalse(category.isActive());
        assertNotNull(category.getCreatedAt());
        assertNotNull(category.getUpdatedAt());
        assertNotNull(category.getDeletedAt());
    }

    @Test
    public void givenAnInactiveCategory_whenCallsActivate_shouldReturnActivatedCategory() {
        final String expectedName = "Test Category";
        final String expectedDescription = "Test Description";
        final boolean expectedIsActive = false;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);
        assertFalse(category.isActive());
        assertNotNull(category.getDeletedAt());

        assertDoesNotThrow(() ->
                category.validate(new ThrowsValidationHandler())
        );

        category.activate();

        assertDoesNotThrow(() ->
                category.validate(new ThrowsValidationHandler())
        );
        assertTrue(category.isActive());
        assertNull(category.getDeletedAt());
    }

    @Test
    public void givenAnActiveCategory_whenCallsDeactivate_shouldReturnDeactivatedCategory() {
        final String expectedName = "Test Category";
        final String expectedDescription = "Test Description";
        final boolean expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);
        assertTrue(category.isActive());
        assertNull(category.getDeletedAt());

        assertDoesNotThrow(() ->
                category.validate(new ThrowsValidationHandler())
        );

        category.deactivate();

        assertDoesNotThrow(() ->
                category.validate(new ThrowsValidationHandler())
        );

        assertFalse(category.isActive());
        assertNotNull(category.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallsUpdate_shouldReturnUpdatedCategory() {
        final String expectedName = "Test Category";
        final String expectedDescription = "Test Description";
        final boolean expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);

        assertDoesNotThrow(() ->
                category.validate(new ThrowsValidationHandler())
        );

        assertEquals(expectedName, category.getName());
        assertEquals(expectedDescription, category.getDescription());

        final String newName = "Updated Category";
        final String newDescription = "Updated Description";

        category.update(newName, newDescription, expectedIsActive);

        assertDoesNotThrow(() ->
                category.validate(new ThrowsValidationHandler())
        );

        assertEquals(newName, category.getName());
        assertEquals(newDescription, category.getDescription());
    }

    @Test
    public void givenAValidCategory_whenCallsUpdateWithInvalidData_shouldReturnDomainException() {
        final String expectedName = "Test Category";
        final String expectedDescription = "Test Description";
        final boolean expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);

        assertDoesNotThrow(() ->
            category.validate(new ThrowsValidationHandler())
        );

        final String newName = "ab"; // Nome inválido (menos de 3 caracteres)
        final String newDescription = "Updated Description";

        final var newCategory = category.update(newName, newDescription, expectedIsActive);

        final var exception = assertThrows(DomainException.class, () ->
            newCategory.validate(new ThrowsValidationHandler())
        );

        final var expectedMessage = "'Nome' deve ter no mínimo 3 e 255 caracteres.";
        assertEquals(expectedMessage, exception.getMessage());

        int expectedFailures = 1;
        assertEquals(expectedFailures, exception.getFailures().size());
    }



}