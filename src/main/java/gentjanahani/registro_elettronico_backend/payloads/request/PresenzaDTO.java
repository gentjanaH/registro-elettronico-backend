package gentjanahani.registro_elettronico_backend.payloads.request;

import gentjanahani.registro_elettronico_backend.entities.StatoPresenza;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PresenzaDTO(
        @NotNull(message = "Lo stato della presenza è obbligatorio")
        StatoPresenza stato,

        @NotNull(message = "L'id della lezione è obbligatorio")
        UUID idLezione
) {
}
