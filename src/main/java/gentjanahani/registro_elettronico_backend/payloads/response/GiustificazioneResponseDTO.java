package gentjanahani.registro_elettronico_backend.payloads.response;

import gentjanahani.registro_elettronico_backend.entities.Giustificazione;
import gentjanahani.registro_elettronico_backend.entities.StatoPresenza;

import java.time.LocalDate;
import java.util.UUID;

public record GiustificazioneResponseDTO(
        UUID idPresenza,
        StatoPresenza statoPresenza,
        UUID idGiustificazione,
        String motivo
) {
}
