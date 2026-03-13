package gentjanahani.registro_elettronico_backend.payloads.response;

import gentjanahani.registro_elettronico_backend.entities.Materia;

import java.time.LocalDate;
import java.util.List;

public record ProfessoreResponseDTO(
        String nome,
        String cognome,
        LocalDate dataDiNascita,
        String email,
        String ruolo,
        List<Materia> materie
) {
}
