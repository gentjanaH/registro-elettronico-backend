package gentjanahani.registro_elettronico_backend.payloads.request;

import gentjanahani.registro_elettronico_backend.entities.TipoValutazione;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.UUID;

public record ValutazioneDTO(
        @Min(4)
        @Max(10)
        int valore,
        TipoValutazione tipo,
        UUID idLezione
) {
}
