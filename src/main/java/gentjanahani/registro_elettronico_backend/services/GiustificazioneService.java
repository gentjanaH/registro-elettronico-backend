package gentjanahani.registro_elettronico_backend.services;

import gentjanahani.registro_elettronico_backend.entities.*;
import gentjanahani.registro_elettronico_backend.exceptions.BadRequestException;
import gentjanahani.registro_elettronico_backend.payloads.request.GiustificazioneDTO;
import gentjanahani.registro_elettronico_backend.repositories.GiustificazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GiustificazioneService {

    private final GiustificazioneRepository giustificazioneRepository;
    private final PresenzaService presenzaService;
    private final GenitoreService genitoreService;

    @Autowired
    public GiustificazioneService(GiustificazioneRepository giustificazioneRepository, PresenzaService presenzaService, GenitoreService genitoreService) {
        this.giustificazioneRepository = giustificazioneRepository;
        this.presenzaService = presenzaService;
        this.genitoreService = genitoreService;
    }


    public Giustificazione giustifica(UUID idPresenza, GiustificazioneDTO payload, User user) {

        Genitore genitore = genitoreService.findUser(user);

        Presenza presenza = presenzaService.findPresenzaByID(idPresenza);

        if (presenza.getStato() != StatoPresenza.ASSENTE) {
            throw new BadRequestException("Puoi giustificare solo le assenze");
        }

        if (!presenza.getStudente().getGenitore().getIdGenitore().equals(genitore.getIdGenitore())) {
            throw new BadRequestException("Per giustificare questa assenza devi essere genitore o tutore dello studente.");
        }

        if (presenza.getGiustificazione() != null) {
            throw new BadRequestException("assenza gia giustificata");
        }

        Giustificazione g = new Giustificazione(
                payload.motivo(),
                genitore
        );
        presenza.setStato(StatoPresenza.GIUSTIFICATO);
        g.setPresenza(presenza);

        return giustificazioneRepository.save(g);
    }
}
