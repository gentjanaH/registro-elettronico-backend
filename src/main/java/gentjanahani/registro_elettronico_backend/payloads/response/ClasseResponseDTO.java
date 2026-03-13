package gentjanahani.registro_elettronico_backend.payloads.response;

import java.util.UUID;

public record ClasseResponseDTO(
        UUID idClasse,
        String nome,
        int capienzaMax
) {
}
