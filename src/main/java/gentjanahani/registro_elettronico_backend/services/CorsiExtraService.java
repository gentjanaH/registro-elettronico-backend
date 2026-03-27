package gentjanahani.registro_elettronico_backend.services;

import gentjanahani.registro_elettronico_backend.entities.Classe;
import gentjanahani.registro_elettronico_backend.entities.CorsiExtra;
import gentjanahani.registro_elettronico_backend.entities.Professore;
import gentjanahani.registro_elettronico_backend.entities.Studente;
import gentjanahani.registro_elettronico_backend.exceptions.BadRequestException;
import gentjanahani.registro_elettronico_backend.exceptions.NotFoundException;
import gentjanahani.registro_elettronico_backend.payloads.request.CorsiExtraDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.CorsiExtraResponseDTO;
import gentjanahani.registro_elettronico_backend.repositories.CorsiExtraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CorsiExtraService {

    private final CorsiExtraRepository corsiExtraRepository;
    private final ProfessoreService professoreService;
    private final ClasseService classeService;
    private final StudenteService studenteService;

    @Autowired
    public CorsiExtraService(CorsiExtraRepository corsiExtraRepository, ProfessoreService professoreService, ClasseService classeService, StudenteService studenteService) {
        this.corsiExtraRepository = corsiExtraRepository;
        this.professoreService = professoreService;
        this.classeService = classeService;
        this.studenteService = studenteService;
    }

    public CorsiExtraResponseDTO toDTO(CorsiExtra corso) {
        return new CorsiExtraResponseDTO(
                corso.getIdCorso(),
                corso.getNome(),
                corso.getInizio(),
                corso.getFine(),
                corso.getGiorno(),
                corso.getProfessore().getIdProfessore(),
                corso.getProfessore().getNome(),
                corso.getProfessore().getCognome(),
                corso.getClasse().getIdClasse(),
                corso.getClasse().getNome(),
                corso.getStudenti().stream().toList()
        );
    }

    public Page<CorsiExtraResponseDTO> toPageDTO(Page<CorsiExtra> page) {
        return page.map(this::toDTO);
    }

    //ADD CORSO
    public CorsiExtraResponseDTO addCorsoExtra(CorsiExtraDTO payload) {

        Professore prof = professoreService.findById(payload.idProfessore());
        Classe classe = classeService.findClasseByID(payload.idClasse());

        if (payload.fine().isBefore(payload.inizio()) ||
                payload.fine().equals(payload.inizio())) {
            throw new BadRequestException("L'orario di fine di fine corso deve essere dopo quello di inizio");

        }

        CorsiExtra corso = new CorsiExtra(
                payload.nome(),
                payload.inizio(),
                payload.fine(),
                payload.giorno(),
                prof,
                classe
        );

        corsiExtraRepository.save(corso);
        return this.toDTO(corso);
    }

    //    METODO PER ISCRIVERE LO STUDENTE
    public CorsiExtraResponseDTO iscriviStudente(UUID idCorso, UUID idStudente) {
        CorsiExtra corso = corsiExtraRepository.findById(idCorso)
                .orElseThrow(() -> new NotFoundException("Corso non trovato"));

        Studente studente = studenteService.findById(idStudente);

        if (corso.getStudenti().stream().anyMatch(s -> s.getIdStudente().equals(idStudente))) {
            throw new BadRequestException("Lo studente è già iscritto a questo corso");
        }

        corso.getStudenti().add(studente);
        corsiExtraRepository.save(corso);
        return this.toDTO(corso);
    }

    //GET BY ID
    public CorsiExtraResponseDTO getById(UUID idCorso) {
        CorsiExtra corso = corsiExtraRepository.findById(idCorso)
                .orElseThrow(() -> new NotFoundException("Corso non trovato"));

        return this.toDTO(corso);
    }

    //GET ALL
    public Page<CorsiExtraResponseDTO> getAll(Pageable pagebale) {
        return this.toPageDTO(corsiExtraRepository.findAll(pagebale));
    }

    //DELETE
    public void deleteCorso(UUID idCorso) {
        CorsiExtra corso = corsiExtraRepository.findById(idCorso)
                .orElseThrow(() -> new NotFoundException("Corso non trovato"));


        corsiExtraRepository.delete(corso);

    }

    public CorsiExtraResponseDTO rimuoviStudente(UUID idCorso, UUID idStudente) {
        CorsiExtra corso = corsiExtraRepository.findById(idCorso)
                .orElseThrow(() -> new NotFoundException("Corso non trovato"));

        corso.getStudenti().removeIf(s -> s.getIdStudente().equals(idStudente));
        corsiExtraRepository.save(corso);
        return this.toDTO(corso);
    }


}
