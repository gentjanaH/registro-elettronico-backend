package gentjanahani.registro_elettronico_backend.services;

import gentjanahani.registro_elettronico_backend.entities.*;
import gentjanahani.registro_elettronico_backend.exceptions.BadRequestException;
import gentjanahani.registro_elettronico_backend.exceptions.NotFoundException;
import gentjanahani.registro_elettronico_backend.payloads.response.ProfessoreResponseDTO;
import gentjanahani.registro_elettronico_backend.repositories.ProfessoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfessoreService {

    private final ProfessoreRepository professoreRepository;
    private final MateriaService materiaService;
    private final ClasseService classeService;


    @Autowired
    public ProfessoreService(ProfessoreRepository professoreRepository, MateriaService materiaService, ClasseService classeService) {
        this.professoreRepository = professoreRepository;
        this.materiaService = materiaService;

        this.classeService = classeService;
    }

    public ProfessoreResponseDTO toDTO(Professore professore) {
        return new ProfessoreResponseDTO(
                professore.getNome(),
                professore.getCognome(),
                professore.getDataDiNascita(),
                professore.getUser().getEmail(),
                professore.getUser().getRuolo().getRuolo(),
                professore.getMaterie(),
                professore.getClassi()
        );
    }

    public Professore save(Professore p) {
        return professoreRepository.save(p);
    }

    public Professore findById(UUID id) {

        Professore prof = professoreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professore non trovato"));

        return prof;
    }

    public Professore findByUserId(UUID idUser) {
        return professoreRepository.findByUser_IdUser(idUser)
                .orElseThrow(() -> new NotFoundException("Professore non trovato per questo utente"));
    }

    public Professore findByUser(User user) {

        return professoreRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("User professore non trovato"));

    }

    //metodo per cercare tutti i professori
    public Page<ProfessoreResponseDTO> getAll(Pageable pageable) {

        Page<Professore> page = professoreRepository.findAll(pageable);
        return page.map(this::toDTO);

    }

    //metoto addMateriaProfessore
    public Professore addMateriaToProf(UUID idProfessore, UUID idMateria) {
        Professore prof = findById(idProfessore);
        Materia materia = materiaService.getById(idMateria);

        if (prof.getMaterie().contains(materia)) {
            throw new BadRequestException("Questa materia è gia assegnata al professore selezionato.");
        }

        prof.getMaterie().add(materia);
        return professoreRepository.save(prof);
    }

    //metoto addClasseProfessore
    public Professore addClasseToProf(UUID idProfessore, UUID idClasse) {

        Professore prof = findById(idProfessore);
        Classe classe = classeService.findClasseByID(idClasse);

        // Evita duplicati
        if (!prof.getClassi().contains(classe)) {
            prof.getClassi().add(classe);
        }

        if (!classe.getListaProfessori().contains(prof)) {
            classe.getListaProfessori().add(prof);
        }

        return professoreRepository.save(prof);
    }
}
