package gentjanahani.registro_elettronico_backend.payloads.response;

import gentjanahani.registro_elettronico_backend.entities.Classe;
import gentjanahani.registro_elettronico_backend.entities.Materia;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record ProfessoreResponseDTO(
        String nome,
        String cognome,
        LocalDate dataDiNascita,
        String email,
        String ruolo,
        Set<Materia> materie,
        Set<Classe> classi
) {
}
