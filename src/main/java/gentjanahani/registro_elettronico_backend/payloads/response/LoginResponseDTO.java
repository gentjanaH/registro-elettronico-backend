package gentjanahani.registro_elettronico_backend.payloads.response;

import gentjanahani.registro_elettronico_backend.entities.Genitore;
import gentjanahani.registro_elettronico_backend.entities.Professore;
import gentjanahani.registro_elettronico_backend.entities.Studente;
import gentjanahani.registro_elettronico_backend.entities.User;
import gentjanahani.registro_elettronico_backend.payloads.request.GenitoreLoginDTO;

public record LoginResponseDTO(
        String accessToken,
        User user,
        Studente studente,
        Professore professore,
        GenitoreLoginDTO genitore
) {
}
