package gentjanahani.registro_elettronico_backend.payloads.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CompitoDTO(
        @NotBlank(message = "La descrizione del compito è obbligatoria")
        String descrizione,
        @NotNull(message = "La data di consegna è obbligatoria")
        @Future(message = "La data di consegna deve essere futura")
        LocalDate dataDiConsegna,
        @NotNull(message = "La materia è obbligatoria")
        UUID idMateria

) {
}
