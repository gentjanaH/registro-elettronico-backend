package gentjanahani.registro_elettronico_backend.payloads.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotBlank(message = "Email obbligatoria")
        @Email(message = "Email non valida")
        String email,

        @NotBlank(message = "Password obbligatoria")
        @Size(min = 6, message = "Password deve essere almeno 6 caratteri")
        String password
) {
}
