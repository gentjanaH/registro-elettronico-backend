package gentjanahani.registro_elettronico_backend.payloads.request;

import java.util.UUID;

public record FiglioDTO(
        UUID idStudente,
        String nome,
        String cognome

) {
}
