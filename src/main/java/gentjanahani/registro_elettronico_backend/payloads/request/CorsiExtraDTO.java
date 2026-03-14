package gentjanahani.registro_elettronico_backend.payloads.request;

import gentjanahani.registro_elettronico_backend.entities.GiornoSettimana;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;
import java.util.UUID;

public record CorsiExtraDTO(
        @NotBlank(message = "Il nome del corso è obbligatorio")
        @Size(min = 3, max = 50, message = "Il nome deve essere tra 3 e 50 caratteri")
        String nome,
        @NotNull(message = "L'orario di inizio è obbligatorio")
        LocalTime inizio,
        @NotNull(message = "L'orario di fine è obbligatorio")
        LocalTime fine,
        @NotNull(message = "Il giorno della settimana è obbligatorio")
        GiornoSettimana giorno,
        @NotNull(message = "L'ID del professore è obbligatorio")
        UUID idProfessore,
        @NotNull(message = "L'ID della classe è obbligatorio")
        UUID idClasse
) {
}
