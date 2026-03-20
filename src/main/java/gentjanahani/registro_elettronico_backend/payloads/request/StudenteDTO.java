package gentjanahani.registro_elettronico_backend.payloads.request;

import java.util.UUID;

public record StudenteDTO(
        UUID idStudente,
        String nome,
        String cognome,
        UUID idClasse,
        String classe
) {
}
