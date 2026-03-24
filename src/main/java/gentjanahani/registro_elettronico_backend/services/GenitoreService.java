package gentjanahani.registro_elettronico_backend.services;

import gentjanahani.registro_elettronico_backend.entities.Genitore;
import gentjanahani.registro_elettronico_backend.entities.User;
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

    @Autowired
    public GenitoreService(GenitoreRepository genitoreRepository) {
        this.genitoreRepository = genitoreRepository;
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

}
