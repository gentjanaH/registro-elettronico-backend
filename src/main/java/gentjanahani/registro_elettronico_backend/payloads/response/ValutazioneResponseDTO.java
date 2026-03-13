package gentjanahani.registro_elettronico_backend.payloads.response;

import gentjanahani.registro_elettronico_backend.entities.TipoValutazione;

import java.time.LocalDate;
import java.util.UUID;

public record ValutazioneResponseDTO(
        UUID idValutazione,
        int valore,
        TipoValutazione tipo,
        String materia,
        LocalDate dataLezione
) {
}
