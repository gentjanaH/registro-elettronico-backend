package gentjanahani.registro_elettronico_backend.services;

import gentjanahani.registro_elettronico_backend.entities.*;
import gentjanahani.registro_elettronico_backend.exceptions.NotFoundException;
import gentjanahani.registro_elettronico_backend.exceptions.UnauthorizedException;
import gentjanahani.registro_elettronico_backend.payloads.request.CompitoDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.CompitoResponseDTO;
import gentjanahani.registro_elettronico_backend.repositories.CompitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompitoService {

    private final CompitoRepository compitoRepository;
    private final ProfessoreService professoreService;
    private final ClasseService classeService;
    private final MateriaService materiaService;


    @Autowired
    public CompitoService(CompitoRepository compitoRepository, ProfessoreService professoreService, ClasseService classeService, MateriaService materiaService) {
        this.compitoRepository = compitoRepository;
        this.professoreService = professoreService;
        this.classeService = classeService;
        this.materiaService = materiaService;
    }


    public CompitoResponseDTO toDTO(Compito compito) {
        return new CompitoResponseDTO(
                compito.getIdCompito(),
                compito.getDescrizione(),
                compito.getDataConsegna(),
                compito.getClasse().getIdClasse(),
                compito.getClasse().getNome(),
                compito.getMateria().getIdMateria(),
                compito.getMateria().getNome(),
                compito.getProfessore().getIdProfessore(),
                compito.getProfessore().getNome(),
                compito.getProfessore().getCognome()
        );
    }

    public Page<CompitoResponseDTO> toPageDTO(Page<Compito> page) {
        return page.map(this::toDTO);
    }

    // CREATE
    public CompitoResponseDTO addCompito(CompitoDTO payload, User profUser, UUID idClasse) {

        Professore prof = professoreService.findByUserId(profUser.getIdUser());

        Classe classe = classeService.findClasseByID(idClasse);

        Materia materia = materiaService.getById(payload.idMateria());

        Compito compito = new Compito(
                payload.descrizione(),
                payload.dataDiConsegna(),
                classe,
                materia,
                prof
        );

        compitoRepository.save(compito);

        return this.toDTO(compito);
    }

    //   GET ALL
    public Page<CompitoResponseDTO> getAllCompiti(Pageable pageable) {

        Page<Compito> page = compitoRepository.findAll(pageable);
        return this.toPageDTO(page);

    }

    //  GET COMPITO BY ID
    public CompitoResponseDTO compitoById(UUID idCompito) {

        Compito compito = compitoRepository.findById(idCompito)
                .orElseThrow(() -> new NotFoundException("Compito non trovato"));

        return this.toDTO(compito);
    }

    //  GET COMPITO BY CLASS
    public Page<CompitoResponseDTO> getCompitiByClasse(UUID idClasse, Pageable pageable) {

        Page<Compito> page = compitoRepository.findByClasse_IdClasse(idClasse, pageable);
        return this.toPageDTO(page);
    }


    // DELETE
    public void deleteCompito(UUID idCompito, User profUser) {

        Compito compito = compitoRepository.findById(idCompito)
                .orElseThrow(() -> new NotFoundException("Compito non trovato"));

        // Controllo che il professore sia l'autore
        if (!compito.getProfessore().getUser().getIdUser().equals(profUser.getIdUser())) {
            throw new UnauthorizedException("Non puoi eliminare un compito che non hai creato");
        }

        compitoRepository.delete(compito);
    }
}
