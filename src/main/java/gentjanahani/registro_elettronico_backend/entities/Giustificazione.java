package gentjanahani.registro_elettronico_backend.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="giustificazione")
public class Giustificazione {

    @Id
    @GeneratedValue
    private UUID idGiustificazione;

    @Column(nullable = false)
    private String motivo;

    // relazione ManyToOne con genitore
    @ManyToOne
    @JoinColumn(name = "id_genitore")
    private Genitore genitore;

    // relazione OneToOne con presenza
    @OneToOne
    @JoinColumn(name = "id_presenza", unique = true)
    private Presenza presenza;


    public Giustificazione (){}

    public Giustificazione(String motivo, Genitore genitore) {
        this.motivo = motivo;
        this.genitore = genitore;
    }

    public UUID getIdGiustificazione() {
        return idGiustificazione;
    }


    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Genitore getGenitore() {
        return genitore;
    }

    public void setGenitore(Genitore genitore) {
        this.genitore = genitore;
    }

    @Override
    public String toString() {
        return "Giustificazione{" +
                "idGiustificazione=" + idGiustificazione +
                ", motivo='" + motivo + '\'' +

                '}';
    }
}
