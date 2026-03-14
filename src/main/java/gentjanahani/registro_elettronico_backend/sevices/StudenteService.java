package gentjanahani.registro_elettronico_backend.sevices;

import gentjanahani.registro_elettronico_backend.entities.Classe;
import gentjanahani.registro_elettronico_backend.entities.Studente;
import gentjanahani.registro_elettronico_backend.repositories.StudenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StudenteService {

    private final StudenteRepository studenteRepository;
    private final ClasseService classeService;

    @Autowired
    public StudenteService(StudenteRepository studenteRepository, ClasseService classeService) {
        this.studenteRepository = studenteRepository;
        this.classeService = classeService;
    }

    public Studente findById(UUID id) {
        return studenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Studente non trovato"));
    }

    public Studente save(Studente s) {
        return studenteRepository.save(s);
    }

    public Page<Studente> findAll(int page, int size, String order) {
        if (page < 0) page = 0;
        if (size > 100 || size < 0) size = 10;

        Pageable pageable = PageRequest.of(page, size, Sort.by(order));

        return this.studenteRepository.findAll(pageable);
    }


    public Page<Studente> getStudentiByClasse(UUID idClasse, Pageable pageable) {

        return studenteRepository.findAllByClasseIdClasse(idClasse, pageable);
    }

//    public Studente findByEmail(String email) {
//        return this.studenteRepository.findByEmail(email);
//    }

    ;


}
