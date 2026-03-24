package gentjanahani.registro_elettronico_backend.payloads.response;

import java.time.LocalDate;
import java.util.UUID;

public record StudenteResponseDTO(
        UUID idStudente,
        String nome,
        String cognome,
        LocalDate dataDiNascita,
        String email,
        String nomeGenitore,
        String cognomeGenitore

) {
}
