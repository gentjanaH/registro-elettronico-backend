package gentjanahani.registro_elettronico_backend.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "presenza")
public class Presenza {

    @Id
    @GeneratedValue
    private UUID idPresenza;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoPresenza stato;

    //    relazione ManyToOne con lezione
    @ManyToOne
    @JoinColumn(name = "id_lezione")
    private Lezione lezione;

    //    relazione ManyToOne con studente
    @ManyToOne
    @JoinColumn(name = "id_studente")
    private Studente studente;

    //    relazione ManyToOne con giustificazione
    @OneToOne(mappedBy = "presenza")
    private Giustificazione giustificazione;


    public Presenza (){}

    public Presenza(StatoPresenza stato, Lezione lezione, Studente studente, Giustificazione giustificazione) {
        this.stato = stato;
        this.lezione = lezione;
        this.studente = studente;
        this.giustificazione = giustificazione;
    }

    public UUID getIdPresenza() {
        return idPresenza;
    }



    public StatoPresenza getStato() {
        return stato;
    }

    public void setStato(StatoPresenza stato) {
        this.stato = stato;
    }

    public Lezione getLezione() {
        return lezione;
    }

    public void setLezione(Lezione lezione) {
        this.lezione = lezione;
    }

    public Studente getStudente() {
        return studente;
    }

    public void setStudente(Studente studente) {
        this.studente = studente;
    }

    public Giustificazione getGiustificazione() {
        return giustificazione;
    }

    public void setGiustificazione(Giustificazione giustificazione) {
        this.giustificazione = giustificazione;
    }


    @Override
    public String toString() {
        return "Presenza{" +
                "idPresenza=" + idPresenza +
                ", stato=" + stato +

                '}';
    }
}
