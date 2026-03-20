package gentjanahani.registro_elettronico_backend.services;

import gentjanahani.registro_elettronico_backend.entities.Materia;
import gentjanahani.registro_elettronico_backend.exceptions.BadRequestException;
import gentjanahani.registro_elettronico_backend.exceptions.NotFoundException;
import gentjanahani.registro_elettronico_backend.payloads.request.MateriaDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.ProfessoreMateriaResponseDTO;
import gentjanahani.registro_elettronico_backend.repositories.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MateriaService {

    private final MateriaRepository materiaRepositoy;

    @Autowired
    public MateriaService(MateriaRepository materiaRepositoy) {
        this.materiaRepositoy = materiaRepositoy;
    }

    public MateriaDTO toMateriaDTO(Materia materia) {
        return new MateriaDTO(materia.getNome());
    }

    //    METODO PER SALVARE UNA MATERIA
    public Materia addMateria(MateriaDTO payload) {
        Materia materia = new Materia(
                payload.nome()

        );
        if (materiaRepositoy.existsByNome(payload.nome())) throw new BadRequestException("Materia gia esistente.");

        return materiaRepositoy.save(materia);
    }

    //    METODO PER VISUALIZZARE UNA MATERIA
    public Materia getById(UUID idMateria) {

        Materia found = materiaRepositoy.findById(idMateria)
                .orElseThrow(() -> new NotFoundException("Materia non trovata"));

        return found;

    }

    //    METODO PER VISUALIZZARE TUTTE LE MATERIE
    public Page<Materia> getAllMaterie(Pageable pageable) {

        return materiaRepositoy.findAll(pageable);
    }

    //    METODO PER VISUALIZZARE I PROFESSORI CHE INSEGNANO UNA  METERIA
    public List<ProfessoreMateriaResponseDTO> getProfessoriByMateria(UUID idMateria) {

        Materia materia = getById(idMateria);
        return materia.getProfessori().stream()
                .map(p -> new ProfessoreMateriaResponseDTO(
                        p.getIdProfessore(),
                        p.getNome(),
                        p.getCognome()
                ))
                .toList();
    }


    //    METODO PER ELIMINARE UNA MATERIA
    public void deleteMateria(UUID idMateria) {

        Materia materia = getById(idMateria);

        materiaRepositoy.delete(materia);
    }
}
