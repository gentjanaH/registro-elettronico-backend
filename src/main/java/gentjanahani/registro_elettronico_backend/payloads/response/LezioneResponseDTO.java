package gentjanahani.registro_elettronico_backend.payloads.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record LezioneResponseDTO(
        LocalDate data,
        LocalTime inizioLezione,
        LocalTime fineLezione,
        UUID idClasse,
        UUID idMateria,
        UUID idProfessore
) {
}
