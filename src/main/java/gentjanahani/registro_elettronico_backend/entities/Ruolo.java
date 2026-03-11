package gentjanahani.registro_elettronico_backend.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "ruolo")
public class Ruolo {
    @Id
    @GeneratedValue
    private UUID idRuolo;

    @Column(nullable = false, unique = true)
    private String ruolo;


    public Ruolo (){}

    public Ruolo(String ruolo){

        this.ruolo=ruolo;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public UUID getIdRuolo() {
        return idRuolo;
    }

    @Override
    public String toString() {
        return "Ruolo{" +
                "idRuolo=" + idRuolo +
                ", ruolo='" + ruolo + '\'' +
                '}';
    }
}
