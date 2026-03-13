package gentjanahani.registro_elettronico_backend.sevices;

import gentjanahani.registro_elettronico_backend.entities.Professore;
import gentjanahani.registro_elettronico_backend.exceptions.NotFoundException;
import gentjanahani.registro_elettronico_backend.repositories.ProfessoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    public Professore findById(UUID id) {

        Professore prof = professoreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professore non trovato"));

        return prof;
    }
}
