package gentjanahani.registro_elettronico_backend.repositories;

import gentjanahani.registro_elettronico_backend.entities.Professore;
import gentjanahani.registro_elettronico_backend.entities.Studente;
import gentjanahani.registro_elettronico_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfessoreRepository extends JpaRepository<Professore, UUID> {

    Optional<Professore> findById(UUID idProfessore);

    Optional<Professore> findByUser_IdUser(UUID idUser);

    Optional<Professore> findByUser(User user);
}
