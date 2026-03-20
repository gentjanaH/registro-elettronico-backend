package gentjanahani.registro_elettronico_backend.services;

import gentjanahani.registro_elettronico_backend.entities.*;
import gentjanahani.registro_elettronico_backend.exceptions.BadRequestException;
import gentjanahani.registro_elettronico_backend.exceptions.NotFoundException;
import gentjanahani.registro_elettronico_backend.payloads.request.RegisterDTO;
import gentjanahani.registro_elettronico_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RuoloService ruoloService;
    private final GenitoreService genitoreService;
    private final StudenteService studenteService;
    private final ProfessoreService professoreService;
    private final ClasseService classeService;
    private final MateriaService materiaService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RuoloService ruoloService, GenitoreService genitoreService, StudenteService studenteService, ProfessoreService professoreService, ClasseService classeService, MateriaService materiaService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.ruoloService = ruoloService;
        this.genitoreService = genitoreService;
        this.studenteService = studenteService;
        this.professoreService = professoreService;
        this.classeService = classeService;
        this.materiaService = materiaService;
    }

    //metoto findAll
    public Page<User> findAll(int page, int size, String orderBy) {
        if (page < 0) page = 0;
        if (size > 100 || size < 0) size = 10;

        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return this.userRepository.findAll(pageable);
    }

    //metoto findById
    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Utente non trovato"));
    }

    public void validateBirthdate(LocalDate data, String ruolo) {
        if (data == null) return;

        int age = Period.between(data, LocalDate.now()).getYears();

        switch (ruolo.toUpperCase()) {
            case "GENITORE" -> {
                if (age < 18) throw new BadRequestException("Il genitore deve essere maggiorenne");
            }
            case "STUDENTE" -> {
                if (age < 5 || age > 18)
                    throw new BadRequestException("Età studente non valida");
            }
            case "PROFESSORE" -> {
                if (age < 23)
                    throw new BadRequestException("Il professore deve avere almeno 23 anni");
            }
        }
    }


    //metoto saveUser
    public User register(RegisterDTO payload) {

        validateBirthdate(payload.dataDiNascita(), payload.ruolo());

        User user = new User(
                payload.nome(),
                payload.cognome(),
                payload.email(),
                passwordEncoder.encode(payload.password()),
                ruoloService.findByRuolo(payload.ruolo())
        );

        userRepository.save(user);

        switch (payload.ruolo().toUpperCase()) {

            case "GENITORE" -> {

                if (payload.idFiglio() == null || payload.idFiglio().isEmpty()) {
                    throw new BadRequestException("Id Studente mancante o errato");
                }

                Genitore genitore = new Genitore(payload.nome(), payload.cognome(), payload.dataDiNascita(), user);

                List<Studente> figli = payload.idFiglio().stream()
                        .map(studenteService::findById)
                        .toList();

                genitore.setFigli(figli);

                figli.forEach(f -> f.setGenitore(genitore));

                genitoreService.saveGenitore(genitore);

                return user;
            }

            case "STUDENTE" -> {
                if (payload.idClasse() == null) {
                    throw new BadRequestException("Lo studente deve avere una classe assegnata");
                }
                Classe classe = classeService.findClasseByID(payload.idClasse());

                Studente s = new Studente(
                        payload.nome(),
                        payload.cognome(),
                        payload.dataDiNascita(),
                        user,
                        null,
                        classe
                );
                studenteService.save(s);

            }

            case "PROFESSORE" -> {

                if (payload.idMaterie() == null || payload.idMaterie().isEmpty()) {
                    throw new BadRequestException("Il professore deve avere almeno una materia");
                }


                Professore p = new Professore(
                        payload.nome(),
                        payload.cognome(),
                        payload.dataDiNascita(),
                        user
                );


                Professore prof = professoreService.save(p);

                payload.idMaterie().forEach(idMateria -> {
                    Materia m = materiaService.getById(idMateria);
                    prof.getMaterie().add(m);
                });

                professoreService.save(prof);
            }


        }

        return user;

    }

    //metoto findByEmail
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public boolean existByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }


    //metoto findAndUpdate

    //metoto addRuolo

    //metoto findAndDelete
}
