package gentjanahani.registro_elettronico_backend.payloads.response;

import gentjanahani.registro_elettronico_backend.entities.User;

public record LoginResponseDTO(
        String accessToken,
        User user) {
}
