package gentjanahani.registro_elettronico_backend.payloads.response;

import java.util.UUID;

public record AdminResponseDTO(
        UUID idUser,
        String nome,
        String cognome,
        String email

) {
}
