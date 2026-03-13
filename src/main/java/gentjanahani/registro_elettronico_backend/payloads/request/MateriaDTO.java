package gentjanahani.registro_elettronico_backend.payloads.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MateriaDTO(
        @NotBlank(message = "Materia obbligatoria")
        @Size(min = 2, max = 50, message = "Il nome della materia deve essere tra 2 e 50 caratteri")
        String nome
) {
}
