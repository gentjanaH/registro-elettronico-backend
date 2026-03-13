package gentjanahani.registro_elettronico_backend.payloads.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record RegisterDTO(
        @NotBlank(message = "Il nome è obbligatorio")
        @Size(min = 3, max = 50, message = "Il nome deve essere tra 3 e 50 caratteri")
        String nome,

        @NotBlank(message = "Il cognome è obbligatorio")
        @Size(min = 3, max = 50, message = "Il cognome deve essere tra 3 e 50 caratteri")
        String cognome,

        @Past(message = "La data di nascita deve essere nel passato")
        LocalDate dataDiNascita,

        @NotBlank(message = "Email obbligatoria")
        @Email(message = "Email non valida")
        String email,

        @NotBlank(message = "Password obbligatoria")
        @Size(min = 6, message = "Password deve essere almeno 6 caratteri")
        String password,
        @NotBlank(message = "Ruolo obbligatorio")
        String ruolo,           // "GENITORE", "STUDENTE", "PROFESSORE"

        List<UUID> idMaterie,   // solo se RUOLO = PROFESSORE
        UUID idFiglio,          // opzionale → solo se ruolo = GENITORE
        UUID idClasse           // solo se STUDENTE
) {
}
