package gentjanahani.registro_elettronico_backend.services;

import gentjanahani.registro_elettronico_backend.entities.Classe;
import gentjanahani.registro_elettronico_backend.exceptions.BadRequestException;
import gentjanahani.registro_elettronico_backend.exceptions.NotFoundException;
import gentjanahani.registro_elettronico_backend.payloads.request.ClasseDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.ClasseResponseDTO;
import gentjanahani.registro_elettronico_backend.repositories.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClasseService {

    private final ClasseRepository classeRepository;

    @Autowired
    public ClasseService(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    public ClasseResponseDTO toDTO(Classe classe) {
        return new ClasseResponseDTO(
                classe.getIdClasse(),
                classe.getNome(),
                classe.getCapienzaMax()
        );
    }

    public Classe findClasseByID(UUID idClasse) {

        Classe classe = classeRepository.findById(idClasse)
                .orElseThrow(() -> new NotFoundException("Classe non trovata"));

        return classe;
    }

    public Classe addClasse(ClasseDTO payload) {

        if (classeRepository.existsByNome(payload.nome())) {
            throw new BadRequestException("La classe " + payload.nome() + " esiste gia");
        }
        Classe classe = new Classe(
                payload.nome(),
                payload.capienzaMax()

        );

        return classeRepository.save(classe);
    }

    //METODO GETALL
    public Page<ClasseResponseDTO> getAll(Pageable pageable) {

        return classeRepository.findAll(pageable)
                .map(this::toDTO);
    }


    //METODO GETBYID
    public ClasseResponseDTO getById(UUID idClasse) {

        Classe classe = classeRepository.findById(idClasse).orElseThrow(() -> new NotFoundException("classe non trovata"));

        return toDTO(classe);
    }

    public void deleteClasse(UUID idClasse) {

        Classe classe = findClasseByID(idClasse);

        classeRepository.delete(classe);
    }
}
