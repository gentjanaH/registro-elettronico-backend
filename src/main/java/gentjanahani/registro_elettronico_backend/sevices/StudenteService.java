package gentjanahani.registro_elettronico_backend.sevices;

import gentjanahani.registro_elettronico_backend.entities.Studente;
import gentjanahani.registro_elettronico_backend.repositories.StudenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StudenteService {

    private final StudenteRepository studenteRepository;

    @Autowired
    public StudenteService(StudenteRepository studenteRepository) {
        this.studenteRepository = studenteRepository;
    }

    public Studente findById(UUID id) {
        return studenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Studente non trovato"));
    }

    public Studente save(Studente s) {
        return studenteRepository.save(s);
    }
}
