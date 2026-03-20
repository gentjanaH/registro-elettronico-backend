package gentjanahani.registro_elettronico_backend.payloads.request;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record GenitoreLoginDTO(
        UUID idGenitore,
        String nome,
        String cognome,
        LocalDate dataDiNascita,
        List<StudenteDTO> figli
) {
}
