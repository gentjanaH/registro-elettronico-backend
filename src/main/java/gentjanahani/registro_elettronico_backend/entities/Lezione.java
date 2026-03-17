package gentjanahani.registro_elettronico_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "lezione")
public class Lezione {
    @Id
    @GeneratedValue
    private UUID idLezione;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime inizioLezione;

    @Column(nullable = false)
    private LocalTime fineLezione;

    @Column(nullable = false)
    private String descrizione;

    //    relazione ManyToOne con classe
    @ManyToOne
    @JoinColumn(name = "id_classe")
    @JsonIgnore
    private Classe classe;

    //    relazione ManyToOne con materia
    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia materia;

    //    relazione ManyToOne con professore
    @ManyToOne
    @JoinColumn(name = "id_professore")
    private Professore professore;

    public Lezione() {
    }

    public Lezione(LocalDate data, LocalTime inizioLezione, LocalTime fineLezione, String descrizione, Classe classe, Materia materia, Professore professore) {
        this.data = data;
        this.inizioLezione = inizioLezione;
        this.fineLezione = fineLezione;
        this.descrizione = descrizione;
        this.classe = classe;
        this.materia = materia;
        this.professore = professore;

    }


    public UUID getIdLezione() {
        return idLezione;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getFineLezione() {
        return fineLezione;
    }

    public void setFineLezione(LocalTime fineLezione) {
        this.fineLezione = fineLezione;
    }

    public LocalTime getInizioLezione() {
        return inizioLezione;
    }

    public void setInizioLezione(LocalTime inizioLezione) {
        this.inizioLezione = inizioLezione;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Professore getProfessore() {
        return professore;
    }

    public void setProfessore(Professore professore) {
        this.professore = professore;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return "Lezione{" +
                "idLezione=" + idLezione +
                ", data=" + data +
                ", inizioLezione=" + inizioLezione +
                ", fineLezione=" + fineLezione +
                ", descrizione=" + descrizione +

                '}';
    }
}
