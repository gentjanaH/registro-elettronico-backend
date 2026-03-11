package gentjanahani.registro_elettronico_backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "compito")
public class Compito {

    @Id
    @GeneratedValue
    private UUID idCompito;

    @Column(nullable = false)
    private String descrizione;

    @Column(nullable = false)
    @Future
    private LocalDate dataConsegna;

    //    relazione ManyToOne con classe
    @ManyToOne
    @JoinColumn(name = "id_classe")
    private Classe classe;

    //    relazione ManyToOne con materia
    @ManyToOne
    @JoinColumn(name="id_materia")
    private Materia materia;

    //    relazione ManyToOne con professore
    @ManyToOne
    @JoinColumn(name = "id_professore")
    private Professore professore;

    public Compito (){}

    public Compito(String descrizione, LocalDate dataConsegna, Classe classe, Materia materia, Professore professore) {
        this.descrizione = descrizione;
        this.dataConsegna = dataConsegna;
        this.classe = classe;
        this.materia = materia;
        this.professore = professore;
    }

    public UUID getIdCompito() {
        return idCompito;
    }


    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDate getDataConsegna() {
        return dataConsegna;
    }

    public void setDataConsegna(LocalDate dataConsegna) {
        this.dataConsegna = dataConsegna;
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

    @Override
    public String toString() {
        return "Compito{" +
                "idCompito=" + idCompito +
                ", descrizione='" + descrizione + '\'' +
                ", dataConsegna=" + dataConsegna +

                '}';
    }
}
