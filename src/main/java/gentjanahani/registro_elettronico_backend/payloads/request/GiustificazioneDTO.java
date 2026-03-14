package gentjanahani.registro_elettronico_backend.payloads.request;

import jakarta.validation.constraints.NotBlank;

public record GiustificazioneDTO(
        @NotBlank(message = "Il motivo della giustificazione è obbligatorio")
        String motivo
) {
}
