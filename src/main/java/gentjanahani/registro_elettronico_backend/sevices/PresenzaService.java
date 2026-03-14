package gentjanahani.registro_elettronico_backend.sevices;

import gentjanahani.registro_elettronico_backend.entities.Giustificazione;
import gentjanahani.registro_elettronico_backend.entities.Lezione;
import gentjanahani.registro_elettronico_backend.entities.Presenza;
import gentjanahani.registro_elettronico_backend.entities.Studente;
import gentjanahani.registro_elettronico_backend.exceptions.BadRequestException;
import gentjanahani.registro_elettronico_backend.exceptions.NotFoundException;
import gentjanahani.registro_elettronico_backend.payloads.request.PresenzaDTO;
import gentjanahani.registro_elettronico_backend.payloads.request.UpDatePresenzaDTO;
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

    public Page<Presenza> getAssenzeByStudente(UUID idStudente, Pageable pageable) {

        return presenzaRepository.findAssenzeByIdStudente(idStudente, pageable);
    }


    //    metodo per registare presenza, o settare stato assente
    public Presenza addPresenzaAssenza(UUID idStudente, PresenzaDTO payload) {
        Studente studente = studenteService.findById(idStudente);

        Lezione lezione = lezioneService.findLezioneById(payload.idLezione())
                .orElseThrow(() -> new NotFoundException("Lezione non trovata"));

        Presenza presenza = new Presenza(
                payload.stato(),
                lezione,
                studente,
                null
        );

        return presenzaRepository.save(presenza);
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
