package gentjanahani.registro_elettronico_backend.services;

import gentjanahani.registro_elettronico_backend.entities.*;
import gentjanahani.registro_elettronico_backend.exceptions.BadRequestException;
import gentjanahani.registro_elettronico_backend.exceptions.NotFoundException;
import gentjanahani.registro_elettronico_backend.payloads.request.PresenzaDTO;
import gentjanahani.registro_elettronico_backend.payloads.request.UpDatePresenzaDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.PresenzaResponseDTO;
import gentjanahani.registro_elettronico_backend.repositories.PresenzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PresenzaService {

    private final PresenzaRepository presenzaRepository;
    private final StudenteService studenteService;
    private final LezioneService lezioneService;

    @Autowired
    public PresenzaService(PresenzaRepository presenzaRepository, StudenteService studenteService, LezioneService lezioneService) {
        this.presenzaRepository = presenzaRepository;
        this.studenteService = studenteService;
        this.lezioneService = lezioneService;
    }

    public PresenzaResponseDTO toDTO(Presenza presenza) {

        String motivo = presenza.getGiustificazione() != null
                ? presenza.getGiustificazione().getMotivo()
                : null;

        return new PresenzaResponseDTO(
                presenza.getIdPresenza(),
                presenza.getStato(),
                presenza.getLezione().getIdLezione(),
                presenza.getLezione().getData(),
                presenza.getLezione().getInizioLezione(),
                presenza.getLezione().getFineLezione(),
                presenza.getLezione().getMateria().getIdMateria(),
                presenza.getLezione().getMateria().getNome(),
                presenza.getStudente().getIdStudente(),
                presenza.getStudente().getNome(),
                presenza.getStudente().getCognome(),
                motivo


        );
    }

    public Page<PresenzaResponseDTO> toPageDTO(Page<Presenza> page) {
        return page.map(this::toDTO);
    }

    public Presenza findPresenzaByID(UUID idPresenza) {
        Presenza p = presenzaRepository.findById(idPresenza)
                .orElseThrow(() -> new NotFoundException("Presenza non trovata"));

        return p;
    }

    public Page<PresenzaResponseDTO> getAssenzeByStudente(UUID idStudente, Pageable pageable) {

        return toPageDTO(presenzaRepository.findAssenzeByIdStudente(idStudente, pageable));
    }


    //    metodo per registare presenza, o settare stato assente
    public PresenzaResponseDTO addPresenzaAssenza(UUID idStudente, PresenzaDTO payload) {
        Studente studente = studenteService.findById(idStudente);

        Lezione lezione = lezioneService.findLezioneById(payload.idLezione())
                .orElseThrow(() -> new NotFoundException("Lezione non trovata"));

        Presenza presenza = new Presenza(
                payload.stato(),
                lezione,
                studente,
                null
        );

        return toDTO(presenzaRepository.save(presenza));
    }

    //    metodo per modificare lo stato di una presenza
    public Presenza updateStatoPresenza(UUID idPresenza, UpDatePresenzaDTO payload) {

        Presenza presenza = presenzaRepository.findById(idPresenza)
                .orElseThrow(() -> new NotFoundException("Presenza non trovata"));

        presenza.setStato(payload.stato());

        return presenzaRepository.save(presenza);
    }

    //metodo per eliminare un'assenza
    public void deletePresenza(UUID idPresenza) {
        Presenza presenza = presenzaRepository.findById(idPresenza)
                .orElseThrow(() -> new NotFoundException("Presenza non trovata"));
        Giustificazione g = presenza.getGiustificazione();
        if (g != null) throw new BadRequestException("Impossibile eliminare un'assenza se gia giustificata.");

        presenzaRepository.delete(presenza);
    }


}
