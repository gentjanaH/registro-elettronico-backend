package gentjanahani.registro_elettronico_backend.payloads.response;

import java.util.UUID;

public record ProfessoreMateriaResponseDTO(
        UUID idProfessore,
        String nome,
        String cognome

) {
}
