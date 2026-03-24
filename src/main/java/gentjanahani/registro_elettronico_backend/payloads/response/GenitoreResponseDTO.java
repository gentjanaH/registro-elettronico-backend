package gentjanahani.registro_elettronico_backend.payloads.response;

import gentjanahani.registro_elettronico_backend.payloads.request.FiglioDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record GenitoreResponseDTO(
        UUID idGenitore,
        String nome,
        String cognome,
        LocalDate dataDiNascita,
        String email,
        List<FiglioDTO> figli

) {
}
