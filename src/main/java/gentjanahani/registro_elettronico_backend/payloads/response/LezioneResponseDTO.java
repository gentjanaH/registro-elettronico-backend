package gentjanahani.registro_elettronico_backend.payloads.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record LezioneResponseDTO(
        UUID idLezione,
        LocalDate data,
        LocalTime inizioLezione,
        LocalTime fineLezione,
        String descrizione,
        UUID idClasse,
        String nomeClasse,
        UUID idMateria,
        String nomeMateria,
        UUID idProfessore,
        String nomeProfessore,
        String cognomeProfessore
) {
}
