package gentjanahani.registro_elettronico_backend.sevices;

import gentjanahani.registro_elettronico_backend.entities.*;
import gentjanahani.registro_elettronico_backend.exceptions.BadRequestException;
import gentjanahani.registro_elettronico_backend.exceptions.NotFoundException;
import gentjanahani.registro_elettronico_backend.exceptions.UnauthorizedException;
import gentjanahani.registro_elettronico_backend.payloads.request.LezioneDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.LezioneResponseDTO;
import gentjanahani.registro_elettronico_backend.repositories.LezioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LezioneService {
    private final LezioneRepository lezioneRepository;
    private final MateriaService materiaService;
    private final ClasseService classeService;
    private final ProfessoreService professoreService;


    @Autowired
    public LezioneService(LezioneRepository lezioneRepository, MateriaService materiaService, ClasseService classeService, ProfessoreService professoreService) {
        this.lezioneRepository = lezioneRepository;
        this.materiaService = materiaService;
        this.classeService = classeService;
        this.professoreService = professoreService;
    }

    public LezioneResponseDTO toDTO(Lezione lezione) {
        return new LezioneResponseDTO(
                lezione.getIdLezione(),
                lezione.getData(),
                lezione.getInizioLezione(),
                lezione.getFineLezione(),
                lezione.getClasse().getIdClasse(),
                lezione.getClasse().getNome(),
                lezione.getMateria().getIdMateria(),
                lezione.getMateria().getNome(),
                lezione.getProfessore().getIdProfessore(),
                lezione.getProfessore().getNome(),
                lezione.getProfessore().getCognome()
        );
    }

    //  FIND
    public Optional<Lezione> findLezioneById(UUID idLezione) {
        Lezione found = this.lezioneRepository.findById(idLezione)
                .orElseThrow(() -> new NotFoundException("Lezione non trovata."));
        return Optional.ofNullable(found);
    }

    public LezioneResponseDTO getLezioneById(UUID idLezione) {

        Lezione lezione = findLezioneById(idLezione)
                .orElseThrow(() -> new NotFoundException("Lezione non trovata"));

        return this.toDTO(lezione);
    }

    //  FIND ALL
    public List<LezioneResponseDTO> getAllLezioni() {

        return lezioneRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    //    metodo che salva una lezione
    public LezioneResponseDTO addLezione(LezioneDTO payload, User user, UUID idClasse) {

        Materia materia = materiaService.getById(payload.idMateria());
        Classe classe = classeService.findClasseByID(idClasse);
        Professore prof = professoreService.findByUserId(user.getIdUser());

        Lezione lezione = new Lezione(
                payload.data(),
                payload.inizioLezione(),
                payload.fineLezione(),
                classe,
                materia,
                prof
        );

        Lezione saved = lezioneRepository.save(lezione);

        return this.toDTO(saved);
    }

    //  UPDATE
    public LezioneResponseDTO updateLezione(UUID idLezione, LezioneDTO payload, User profUser) {

        Lezione lezione = findLezioneById(idLezione)
                .orElseThrow(() -> new NotFoundException("Lezione non trovata"));

        if (!lezione.getProfessore().getUser().getIdUser().equals(profUser.getIdUser())) {
            throw new UnauthorizedException("Non puoi modificare una lezione che non hai creato");
        }

        Materia materia = materiaService.getById(payload.idMateria());

        lezione.setInizioLezione(payload.inizioLezione());
        lezione.setFineLezione(payload.fineLezione());
        lezione.setMateria(materia);

        lezioneRepository.save(lezione);

        return this.toDTO(lezione);
    }

    //  DELETE
    public void deleteLezione(UUID idLezione, User profUser) {

        Lezione lezione = findLezioneById(idLezione)
                .orElseThrow(() -> new NotFoundException("Lezione non trovata"));

        if (!lezione.getProfessore().getUser().getIdUser().equals(profUser.getIdUser())) {
            throw new UnauthorizedException("Non sei autorizzato a eliminare questa lezione.");

        }

        lezioneRepository.delete(lezione);
    }
}

