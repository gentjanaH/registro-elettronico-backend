package gentjanahani.registro_elettronico_backend.repositories;

import gentjanahani.registro_elettronico_backend.entities.Presenza;
import gentjanahani.registro_elettronico_backend.entities.Valutazione;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ValutazioneRepository extends JpaRepository<Valutazione, UUID> {

    @Query("SELECT v FROM Valutazione v WHERE v.studente.id = :idStudente")
    Page<Valutazione> getValutazioniByIdStudente(@Param("idStudente") UUID idStudente, Pageable pageable);
}
