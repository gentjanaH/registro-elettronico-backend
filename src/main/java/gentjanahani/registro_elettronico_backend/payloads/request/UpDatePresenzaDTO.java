package gentjanahani.registro_elettronico_backend.payloads.request;

import gentjanahani.registro_elettronico_backend.entities.StatoPresenza;
import jakarta.validation.constraints.NotNull;

public record UpDatePresenzaDTO(
        @NotNull(message = "Lo stato della presenza è obbligatorio")
        StatoPresenza stato
) {
}
