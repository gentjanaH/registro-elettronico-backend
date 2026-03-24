package gentjanahani.registro_elettronico_backend.payloads.request;

import gentjanahani.registro_elettronico_backend.payloads.response.AdminResponseDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.GenitoreResponseDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.ProfessoreResponseDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.StudenteResponseDTO;

import java.util.List;

public record UtentiPerRuoloDTO(
        List<ProfessoreResponseDTO> professori,
        List<GenitoreResponseDTO> genitori,
        List<StudenteResponseDTO> studenti,
        List<AdminResponseDTO> amministratori
) {
}
