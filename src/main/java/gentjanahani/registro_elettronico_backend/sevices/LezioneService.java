package gentjanahani.registro_elettronico_backend.sevices;

import gentjanahani.registro_elettronico_backend.entities.Classe;
import gentjanahani.registro_elettronico_backend.entities.Lezione;
import gentjanahani.registro_elettronico_backend.entities.Materia;
import gentjanahani.registro_elettronico_backend.entities.Professore;
import gentjanahani.registro_elettronico_backend.exceptions.NotFoundException;
import gentjanahani.registro_elettronico_backend.payloads.request.LezioneDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.LezioneResponseDTO;
import gentjanahani.registro_elettronico_backend.repositories.LezioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public LezioneDTO toDTO(Lezione lezione) {
        return new LezioneDTO(
                lezione.getData(),
                lezione.getInizioLezione(),
                lezione.getFineLezione(),
                lezione.getClasse().getIdClasse(),
                lezione.getMateria().getIdMateria()
        );
    }


    public Optional<Lezione> findLezioneById(UUID idLezione) {
        Lezione found = this.lezioneRepository.findById(idLezione)
                .orElseThrow(() -> new NotFoundException("Lezione non trovata."));
        return Optional.ofNullable(found);
    }

    //    metodo che salva una lezione
    public LezioneResponseDTO addLezione(LezioneDTO payload, UUID idProf) {

        Materia materia = materiaService.getById(payload.idMateria());
        Classe classe = classeService.findClasseByID(payload.idClasse());
        Professore prof = professoreService.findById(idProf);

        Lezione lezione = new Lezione(
                payload.data(),
                payload.inizioLezione(),
                payload.fineLezione(),
                classe,
                materia,
                prof
        );

        Lezione saved = lezioneRepository.save(lezione);

        return new LezioneResponseDTO(
                saved.getData(),
                saved.getInizioLezione(),
                saved.getFineLezione(),
                saved.getClasse().getIdClasse(),
                saved.getMateria().getIdMateria(),
                saved.getProfessore().getIdProfessore()
        );
    }


}

