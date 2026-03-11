package gentjanahani.registro_elettronico_backend.payloads.errorPayload;

import java.util.List;

public record ErrorsListDTO(String message, List<String> errors) {
}
