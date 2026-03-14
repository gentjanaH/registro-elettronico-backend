package gentjanahani.registro_elettronico_backend.payloads.response;

import gentjanahani.registro_elettronico_backend.entities.GiornoSettimana;
import gentjanahani.registro_elettronico_backend.entities.Studente;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record CorsiExtraResponseDTO(
        UUID idCorso,
        String nome,
        LocalTime inizio,
        LocalTime fine,
        GiornoSettimana giorno,
        UUID idProfessore,
        String nomeProfessore,
        String cognomeProfessore,
        UUID idClasse,
        String nomeClasse,
        List<Studente> studentiIscritti
) {
}
