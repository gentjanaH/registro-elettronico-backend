package gentjanahani.registro_elettronico_backend.sevices;

import gentjanahani.registro_elettronico_backend.entities.Professore;
import gentjanahani.registro_elettronico_backend.repositories.ProfessoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessoreService {

    private final ProfessoreRepository professoreRepository;

    @Autowired
    public ProfessoreService(ProfessoreRepository professoreRepository) {
        this.professoreRepository = professoreRepository;
    }

    public Professore save(Professore p) {
        return professoreRepository.save(p);
    }
}
