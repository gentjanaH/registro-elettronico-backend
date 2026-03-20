package gentjanahani.registro_elettronico_backend.services;

import gentjanahani.registro_elettronico_backend.entities.*;
import gentjanahani.registro_elettronico_backend.exceptions.BadRequestException;
import gentjanahani.registro_elettronico_backend.exceptions.NotFoundException;
import gentjanahani.registro_elettronico_backend.payloads.request.UpdateValutazioneDTO;
import gentjanahani.registro_elettronico_backend.payloads.request.ValutazioneDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.ValutazioneResponseDTO;
import gentjanahani.registro_elettronico_backend.repositories.ValutazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ValutazioneService {

    private final ValutazioneRepository valutazioneRepository;
    private final StudenteService studenteService;
    private final LezioneService lezioneService;
    private final ProfessoreService professoreService;


    @Autowired
    public ValutazioneService(ValutazioneRepository valutazioneRepository, StudenteService studenteService, LezioneService lezioneService, ProfessoreService professoreService) {
        this.valutazioneRepository = valutazioneRepository;
        this.studenteService = studenteService;
        this.lezioneService = lezioneService;
        this.professoreService = professoreService;
    }

    public ValutazioneResponseDTO toDTO(Valutazione valutazione) {
        return new ValutazioneResponseDTO(
                valutazione.getIdValutazione(),
                valutazione.getValore(),
                valutazione.getTipo(),
                valutazione.getLezione().getIdLezione(),
                valutazione.getLezione().getData(),
                valutazione.getLezione().getMateria().getIdMateria(),
                valutazione.getLezione().getMateria().getNome()

        );

    }

    public Page<Valutazione> getValutazioniStudente(UUID idValutazione, Pageable pageable) {
        return valutazioneRepository.getValutazioniByIdStudente(idValutazione, pageable);
    }

    //    METODO PER AGGIUNGERE VOTI
    public ValutazioneResponseDTO addVoto(ValutazioneDTO payload, User user, UUID idStudente) {

        Studente studente = studenteService.findById(idStudente);

        Lezione lezione = lezioneService.findLezioneById(payload.idLezione())
                .orElseThrow(() -> new NotFoundException("Lezione non trovata!"));

        Professore profLoggato = user.getProfessore();

        if (!lezione.getProfessore().getIdProfessore().equals(profLoggato.getIdProfessore())) {
            throw new BadRequestException("Non sei autorizzato ad assegnare voti in questa classe o questo studente.");
        }
        Valutazione valutazione = new Valutazione(
                payload.valore(),
                payload.tipo(),
                studente,
                lezione
        );
        valutazioneRepository.save(valutazione);
        return toDTO(valutazione);
    }

    //    METODO PER MODIFCARE VOTI
    public ValutazioneResponseDTO updateVoto(UUID idValutazione, UpdateValutazioneDTO payload, User user) {

        Valutazione voto = valutazioneRepository.findById(idValutazione)
                .orElseThrow(() -> new NotFoundException("Voto non trovato"));


        if (!voto.getLezione().getProfessore().getIdProfessore().equals(user.getIdUser())) {
            throw new BadRequestException("Non sei autorizzato a modificare voti in questa classe o questo studente.");
        }
        voto.setValore(payload.valore());
        voto.setTipo(payload.tipo());

        valutazioneRepository.save(voto);
        return toDTO(voto);
    }

    //    METODO PER ELIMINARE VOTI
    public void deleteVoto(UUID idValutazione, User user) {

        Valutazione voto = valutazioneRepository.findById(idValutazione)
                .orElseThrow(() -> new NotFoundException("Voto non trovato"));


        if (!voto.getLezione().getProfessore().getIdProfessore().equals(user.getIdUser())) {
            throw new BadRequestException("Non sei autorizzato a modificare voti in questa classe o questo studente.");
        }

        valutazioneRepository.delete(voto);
    }
}
