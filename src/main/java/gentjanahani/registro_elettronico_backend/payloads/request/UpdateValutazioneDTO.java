package gentjanahani.registro_elettronico_backend.payloads.request;

import gentjanahani.registro_elettronico_backend.entities.TipoValutazione;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record UpdateValutazioneDTO(
        @Min(4)
        @Max(10)
        int valore,
        TipoValutazione tipo
) {
}
