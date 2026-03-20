package gentjanahani.registro_elettronico_backend.repositories;

import gentjanahani.registro_elettronico_backend.entities.Genitore;
import gentjanahani.registro_elettronico_backend.entities.Studente;
import gentjanahani.registro_elettronico_backend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudenteRepository extends JpaRepository<Studente, UUID> {

    Page<Studente> findAllByClasseIdClasse(UUID idClasse, Pageable pageable);

    Optional<Studente> findByUser(User user);


}
