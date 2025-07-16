package com.matheusjfa.application.category.create;

import com.matheusjfa.domain.category.CategoryGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
class CreateCategoryUseCaseTest {
    @Test
    @DisplayName("Dado uma categoria válida, quando chamar o método 'execute', deve retornar uma categoria válida")
    public void givenAValidCategory_whenCallingExecute_shouldReturnAValidCategory() throws Exception {
        final var expectedName = "Valid Category";
        final var expectedDescription = "This is a valid category description.";
        final var expectedIsActive = true;

        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var categoryGateway = Mockito.mock(CategoryGateway.class);
        Mockito.when(categoryGateway.create(Mockito.any()))
                .thenAnswer(returnsFirstArg());

        final var useCase = new DefaultCreateCategoryUseCase(categoryGateway);
        final var category = useCase.execute(command).get();

        assertNotNull(category);
        assertNotNull(category.id());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .create(Mockito.argThat(entity ->
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
        final var expectedName = "Valid Inactive Category";
        final var expectedDescription = "This is a valid inactive category description.";
        final var expectedIsActive = false;

        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var categoryGateway = Mockito.mock(CategoryGateway.class);
        Mockito.when(categoryGateway.create(Mockito.any()))
                .thenAnswer(returnsFirstArg());

        final var useCase = new DefaultCreateCategoryUseCase(categoryGateway);
        final var category = useCase.execute(command).get();

        assertNotNull(category);
        assertNotNull(category.id());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .create(Mockito.argThat(entity ->
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
        final var expectedName = "  ";
        final var expectedDescription = "This is a valid category description.";
        final var expectedIsActive = true;

        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var categoryGateway = Mockito.mock(CategoryGateway.class);
        final var useCase = new DefaultCreateCategoryUseCase(categoryGateway);

        final var notification = useCase.execute(command).getLeft();

        assertNotNull(notification);
        String expectedErrorMessage = "O nome da categoria não pode ser vazio";
        assertEquals(expectedErrorMessage, notification.getErrors().get(0).message());

        int expectedErrorCount = 1;
        assertEquals(expectedErrorCount, notification.getErrors().size());
        Mockito.verify(categoryGateway, Mockito.times(0)).create(Mockito.any());
    }

    @Test
    @DisplayName("Deve lançar um erro quando o Gateway falhar")
    public void givenAGatewayFailure_whenCallingExecute_shouldThrowAnException() {
        final var expectedName = "Valid Category";
        final var expectedDescription = "This is a valid category description.";
        final var expectedIsActive = true;

        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var categoryGateway = Mockito.mock(CategoryGateway.class);
        Mockito.when(categoryGateway.create(Mockito.any()))
                .thenThrow(new RuntimeException("Gateway failure"));

        final var useCase = new DefaultCreateCategoryUseCase(categoryGateway);

        assertThrows(RuntimeException.class, () -> useCase.execute(command));

        Mockito.verify(categoryGateway, Mockito.times(1))
                .create(Mockito.any());
    }
}