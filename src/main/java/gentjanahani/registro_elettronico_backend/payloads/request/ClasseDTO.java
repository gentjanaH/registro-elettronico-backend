package gentjanahani.registro_elettronico_backend.payloads.request;

import gentjanahani.registro_elettronico_backend.entities.Studente;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ClasseDTO(
        @NotBlank(message = "Nome classe obbligatorio")
        @Size(min = 2, max = 50, message = "Il nome della classe deve essere tra 2 e 30 caratteri")
        String nome,
        @Min(10)
        @Max(20)
        int capienzaMax
) {
}
