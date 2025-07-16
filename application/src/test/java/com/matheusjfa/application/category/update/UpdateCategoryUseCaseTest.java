package com.matheusjfa.application.category.update;

import com.matheusjfa.domain.category.Category;
import com.matheusjfa.domain.category.CategoryGateway;
import com.matheusjfa.domain.exceptions.DomainException;
import com.matheusjfa.domain.validation.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
class UpdateCategoryUseCaseTest {

    @Test
    @DisplayName("Dado uma categoria válida, quando chamar o método 'execute', deve retornar uma categoria válida")
    public void givenAValidCategory_whenCallingExecute_shouldReturnAValidCategory() throws Exception {
        final var expectedName = "Valid Category";
        final var expectedDescription = "This is a valid category description.";
        final var expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);
        final var expectedId = category.getId();

        final var command = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        final var categoryGateway = Mockito.mock(CategoryGateway.class);

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(Category.with(category)));

        Mockito.when(categoryGateway.update(Mockito.any()))
                .thenAnswer(returnsFirstArg());

        final var useCase = new DefaultUpdateCategoryUseCase(categoryGateway);
        final var updatedCategory = useCase.execute(command).get();

        assertNotNull(updatedCategory);
        assertNotNull(updatedCategory.id());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .findById(Mockito.any());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .update(Mockito.argThat(entity ->
                        {
                            return Objects.equals(entity.getName(), expectedName)
                                    && Objects.equals(entity.getDescription(), expectedDescription)
                                    && Objects.equals(entity.isActive(), expectedIsActive)
                                    && Objects.nonNull(entity.getCreatedAt())
                                    && Objects.nonNull(entity.getUpdatedAt())
                                    && Objects.isNull(entity.getDeletedAt());
                        }
                ));
    }

    @Test
    @DisplayName("Dado uma categoria válida, porem inativa, quando chamar o método 'execute', deve retornar uma categoria válida")
    public void givenAValidInactiveCategory_whenCallingExecute_shouldReturnAValidCategory() throws Exception {
        final var expectedName = "Valid Category";
        final var expectedDescription = "This is a valid category description.";
        final var expectedIsActive = false;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);
        final var expectedId = category.getId();

        final var command = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        final var categoryGateway = Mockito.mock(CategoryGateway.class);

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(Category.with(category)));

        Mockito.when(categoryGateway.update(Mockito.any()))
                .thenAnswer(returnsFirstArg());

        final var useCase = new DefaultUpdateCategoryUseCase(categoryGateway);
        final var updatedCategory = useCase.execute(command).get();

        assertNotNull(updatedCategory);
        assertNotNull(updatedCategory.id());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .findById(Mockito.any());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .update(Mockito.argThat(entity ->
                        {
                            return Objects.equals(entity.getName(), expectedName)
                                    && Objects.equals(entity.getDescription(), expectedDescription)
                                    && Objects.equals(entity.isActive(), expectedIsActive)
                                    && Objects.nonNull(entity.getCreatedAt())
                                    && Objects.nonNull(entity.getUpdatedAt())
                                    && Objects.nonNull(entity.getDeletedAt());
                        }
                ));
    }

    @Test
    @DisplayName("Dado uma categoria com nome inválido, quando chamar o método 'executar', deve lançar uma exceção")
    public void givenAnInvalidCategoryName_whenCallingExecute_shouldThrowAnException() throws Exception {
        final var invalidName = "  ";
        final var expectedName = "Valid Category";
        final var expectedDescription = "This is a valid category description.";
        final var expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);
        final var expectedId = category.getId();

        final var command = UpdateCategoryCommand.with(
                expectedId.getValue(),
                invalidName,
                expectedDescription,
                expectedIsActive
        );

        final var categoryGateway = Mockito.mock(CategoryGateway.class);

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(Category.with(category)));

        final var useCase = new DefaultUpdateCategoryUseCase(categoryGateway);
        final var notification = useCase.execute(command).getLeft();

        assertNotNull(notification);
        final var expectedErrorMessage = "O nome da categoria não pode ser vazio";
        assertEquals(expectedErrorMessage, notification.getErrors().get(0).message());

        int expectedErrorCount = 1;
        assertEquals(expectedErrorCount, notification.getErrors().size());

    }

    @Test
    @DisplayName("Dado um Id inválido, quando chamar o método 'executar', deve lançar uma exceção")
    public void givenAnInvalidId_whenCallingExecute_thenShouldThrowAnException() throws Exception {
        final var expectedId = "invalid-id";
        final var expectedName = "Valid category";
        final var expectedDescription = "This is a valid category description.";
        final var expectedIsActive = true;

        final var command = UpdateCategoryCommand.with(expectedId, expectedName, expectedDescription, expectedIsActive);

        final var categoryGateway = Mockito.mock(CategoryGateway.class);

        Mockito.when(categoryGateway.findById(Mockito.any()))
                .thenThrow(DomainException.with(new ErrorMessage("Não foi encontrado nenhuma categoria com o ID invalid-id")));

        final var useCase = new DefaultUpdateCategoryUseCase(categoryGateway);

        final var exception = assertThrows(DomainException.class, () -> useCase.execute(command));

        assertNotNull(exception);
        String expectedErrorMessage = "Não foi encontrado nenhuma categoria com o ID invalid-id";
        assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());

        int expectedErrorCount = 1;
        assertEquals(expectedErrorCount, exception.getErrors().size());

        Mockito.verify(categoryGateway, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(categoryGateway, Mockito.times(0)).update(Mockito.any());
    }

    @Test
    @DisplayName("Deve lançar um erro quando o Gateway falhar")
    public void givenAGatewayFailure_whenCallingExecute_shouldThrowAnException() throws Exception {
        final var expectedId = "valid-id";
        final var expectedName = "Valid Category";
        final var expectedDescription = "This is a valid category description.";
        final var expectedIsActive = true;

        final var category = Category.create(expectedName, expectedDescription, expectedIsActive);

        final var command = UpdateCategoryCommand.with(expectedId, expectedName, expectedDescription, expectedIsActive);

        final var categoryGateway = Mockito.mock(CategoryGateway.class);

        Mockito.when(categoryGateway.findById(Mockito.any()))
                .thenReturn(Optional.of(Category.with(category)));

        Mockito.when(categoryGateway.update(Mockito.any()))
                .thenThrow(new RuntimeException("Falha no Gateway"));

        final var useCase = new DefaultUpdateCategoryUseCase(categoryGateway);

        final var notification = useCase.execute(command).getLeft();

        assertNotNull(notification);
        String expectedMessage = "Falha no Gateway";
        assertEquals(expectedMessage, notification.getErrors().get(0).message());

        final var expectedErrorCount = 1;
        assertEquals(expectedErrorCount, notification.getErrors().size());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .findById(Mockito.any());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .update(Mockito.any());
    }
}
