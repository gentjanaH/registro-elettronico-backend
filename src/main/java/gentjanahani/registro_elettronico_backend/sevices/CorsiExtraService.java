package gentjanahani.registro_elettronico_backend.sevices;

import gentjanahani.registro_elettronico_backend.entities.Classe;
import gentjanahani.registro_elettronico_backend.entities.CorsiExtra;
import gentjanahani.registro_elettronico_backend.entities.Professore;
import gentjanahani.registro_elettronico_backend.entities.User;
import gentjanahani.registro_elettronico_backend.exceptions.BadRequestException;
import gentjanahani.registro_elettronico_backend.payloads.request.CorsiExtraDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.CorsiExtraResponseDTO;
import gentjanahani.registro_elettronico_backend.repositories.CorsiExtraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CorsiExtraService {

    private final CorsiExtraRepository corsiExtraRepository;
    private final ProfessoreService professoreService;
    private final ClasseService classeService;

    @Autowired
    public CorsiExtraService(CorsiExtraRepository corsiExtraRepository, ProfessoreService professoreService, ClasseService classeService) {
        this.corsiExtraRepository = corsiExtraRepository;
        this.professoreService = professoreService;
        this.classeService = classeService;
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

        Professore prof = professoreService.findByUserId(payload.idProfessore());
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

    //GET BY ID

    //GET ALL

    //DELETE


}
