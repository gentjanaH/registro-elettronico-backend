package gentjanahani.registro_elettronico_backend.services;

import gentjanahani.registro_elettronico_backend.entities.Genitore;
import gentjanahani.registro_elettronico_backend.entities.Studente;
import gentjanahani.registro_elettronico_backend.entities.User;
import gentjanahani.registro_elettronico_backend.exceptions.NotFoundException;
import gentjanahani.registro_elettronico_backend.payloads.response.StudenteResponseDTO;
import gentjanahani.registro_elettronico_backend.repositories.StudenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Studente findByUser(User user) {

        return studenteRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("User studente non trovato"));

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

    public List<Studente> findAll() {
        return studenteRepository.findAll();
    }


    public Page<Studente> getStudentiByClasse(UUID idClasse, Pageable pageable) {

        return studenteRepository.findAllByClasseIdClasse(idClasse, pageable);
    }

    public StudenteResponseDTO toDTOStudente(Studente s) {
        Genitore genitore = s.getGenitore();
        return new StudenteResponseDTO(
                s.getIdStudente(),
                s.getNome(),
                s.getCognome(),
                s.getDataDiNascita(),
                s.getUser() != null ? s.getUser().getEmail() : null,
                genitore != null ? genitore.getNome() : null,
                genitore != null ? genitore.getCognome() : null
        );
    }
}
