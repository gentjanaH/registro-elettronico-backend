package gentjanahani.registro_elettronico_backend.payloads.response;

import java.time.LocalDate;
import java.util.UUID;

public record CompitoResponseDTO(
        UUID idCompito,
        String descrizione,
        LocalDate dataConsegna,
        UUID idClasse,
        String nomeClasse,
        UUID idMateria,
        String nomeMateria,
        UUID idProfessore,
        String nomeProfessore,
        String cognomeProfessore
) {
}
