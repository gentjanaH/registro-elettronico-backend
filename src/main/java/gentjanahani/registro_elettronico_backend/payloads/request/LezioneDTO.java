package gentjanahani.registro_elettronico_backend.payloads.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record LezioneDTO(
        @NotNull(message = "La data della lezione è obbligatoria")
        @FutureOrPresent(message = "La data della lezione non può essere nel passato")
        LocalDate data,
        @NotNull(message = "L'orario di inizio è obbligatorio")
        LocalTime inizioLezione,
        @NotNull(message = "L'orario di fine è obbligatorio")
        LocalTime fineLezione,
        @NotBlank(message = "La descrizione della lezione è obbligatoria")
        String descrizione,
        @NotNull(message = "L'ID della materia è obbligatorio")
        UUID idMateria
) {
}
