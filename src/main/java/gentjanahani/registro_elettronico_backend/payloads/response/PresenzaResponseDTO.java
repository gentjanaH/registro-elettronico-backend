package gentjanahani.registro_elettronico_backend.payloads.response;

import gentjanahani.registro_elettronico_backend.entities.StatoPresenza;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record PresenzaResponseDTO(
        UUID idPresenza,
        StatoPresenza stato,
        UUID idLezione,
        LocalDate data,
        LocalTime inizioLezione,
        LocalTime fineLezione,
        UUID idMateria,
        String nomeMateria,
        UUID isStudente,
        String nome,
        String cognome

) {
}
