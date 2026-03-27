package gentjanahani.registro_elettronico_backend.services;

import gentjanahani.registro_elettronico_backend.entities.Genitore;
import gentjanahani.registro_elettronico_backend.entities.Studente;
import gentjanahani.registro_elettronico_backend.entities.User;
import gentjanahani.registro_elettronico_backend.exceptions.BadRequestException;
import gentjanahani.registro_elettronico_backend.exceptions.NotFoundException;
import gentjanahani.registro_elettronico_backend.payloads.request.FiglioDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.GenitoreResponseDTO;
import gentjanahani.registro_elettronico_backend.repositories.GenitoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GenitoreService {

    private final GenitoreRepository genitoreRepository;
    private final StudenteService studenteService;

    @Autowired
    public GenitoreService(GenitoreRepository genitoreRepository, StudenteService studenteService) {
        this.genitoreRepository = genitoreRepository;
        this.studenteService = studenteService;
    }

    public Genitore saveGenitore(Genitore g) {
        return genitoreRepository.save(g);
    }

    public Genitore findUser(User user) {
        Genitore g = genitoreRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("User non trovato"));
        return g;
    }

    public Genitore findById(UUID id) {
        return genitoreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genitore non trovato"));
    }

    public List<Genitore> findAll() {

        return genitoreRepository.findAll();
    }


    public GenitoreResponseDTO toDTOGenitore(Genitore g) {
        List<FiglioDTO> figli = g.getFigli() == null ? List.of() :
                g.getFigli().stream()
                        .map(f -> new FiglioDTO(f.getIdStudente(), f.getNome(), f.getCognome()))
                        .toList();

        return new GenitoreResponseDTO(
                g.getIdGenitore(),
                g.getNome(),
                g.getCognome(),
                g.getDataDiNascita(),
                g.getUser() != null ? g.getUser().getEmail() : null,
                figli
        );
    }

    public GenitoreResponseDTO addFiglio(UUID idGenitore, UUID idStudente) {
        Genitore genitore = findById(idGenitore);
        Studente studente = studenteService.findById(idStudente);

        if (genitore.getFigli().stream().anyMatch(s -> s.getIdStudente().equals(idStudente))) {
            throw new BadRequestException("Lo studente è già figlio di questo genitore");
        }

        genitore.getFigli().add(studente);
        studente.setGenitore(genitore);
        studenteService.save(studente);
        genitoreRepository.save(genitore);

        return toDTOGenitore(genitore);
    }

    public GenitoreResponseDTO removeFiglio(UUID idGenitore, UUID idStudente) {
        Genitore genitore = findById(idGenitore);

        genitore.getFigli().removeIf(s -> s.getIdStudente().equals(idStudente));
        genitoreRepository.save(genitore);

        return toDTOGenitore(genitore);
    }

}
