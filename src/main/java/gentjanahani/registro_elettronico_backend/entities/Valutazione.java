package gentjanahani.registro_elettronico_backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

@Entity
@Table(name = "valutazione")
public class Valutazione {
    @Id
    @GeneratedValue
    private UUID idValutazione;

    @Column(nullable = false)
    @Min(4)
    @Max(10)
    @Positive
    private int valore;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoValutazione tipo;

    //    relazione ManyToOne con studente
    @ManyToOne
    @JoinColumn(name = "id_studente")
    private Studente studente;

    //    relazione ManyToOne con lezione
    @ManyToOne
    @JoinColumn(name = "id_lezione")
    private Lezione lezione;

public Valutazione (){}

    public Valutazione(int valore, TipoValutazione tipo, Studente studente, Lezione lezione) {
        this.valore = valore;
        this.tipo = tipo;
        this.studente = studente;
        this.lezione = lezione;
    }

    public UUID getIdValutazione() {
        return idValutazione;
    }



    public int getValore() {
        return valore;
    }

    public void setValore(int valore) {
        this.valore = valore;
    }

    public TipoValutazione getTipo() {
        return tipo;
    }

    public void setTipo(TipoValutazione tipo) {
        this.tipo = tipo;
    }

    public Studente getStudente() {
        return studente;
    }

    public void setStudente(Studente studente) {
        this.studente = studente;
    }

    public Lezione getLezione() {
        return lezione;
    }

    public void setLezione(Lezione lezione) {
        this.lezione = lezione;
    }


    @Override
    public String toString() {
        return "Valutazione{" +
                "idValutazione=" + idValutazione +
                ", valore=" + valore +
                ", tipo=" + tipo +

                '}';
    }
}
