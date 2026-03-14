package gentjanahani.registro_elettronico_backend.repositories;

import gentjanahani.registro_elettronico_backend.entities.Studente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudenteRepository extends JpaRepository<Studente, UUID> {

    Page<Studente> findAllByClasseIdClasse(UUID idClasse, Pageable pageable);

//    Studente findByEmail(String email);


}
