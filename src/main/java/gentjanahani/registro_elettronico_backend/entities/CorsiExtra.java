package gentjanahani.registro_elettronico_backend.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "corsi_extra")
public class CorsiExtra {

    @Id
    @GeneratedValue
    private UUID idCorso;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private LocalTime inizio;


    @Column(nullable = false)
    private LocalTime fine;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GiornoSettimana giorno;


    //    relazione ManyToOne con professore
    @ManyToOne
    @JoinColumn(name = "id_professore")
    private Professore professore;

    //    relazione ManyToOne con classe
    @ManyToOne
    @JoinColumn(name = "id_classe")
    private Classe classe;


    //    junction table corsiExtra_studente
    @ManyToMany
    @JoinTable(
            name = "corsi_extra_studente",
            joinColumns = @JoinColumn(name = "id_corso"),
            inverseJoinColumns = @JoinColumn(name = "id_studente")
    )
    private List<Studente> studenti = new ArrayList<>();


    public CorsiExtra() {
    }

    public CorsiExtra(String nome, LocalTime inizio, LocalTime fine, GiornoSettimana giorno, Professore professore, Classe classe) {
        this.nome = nome;
        this.inizio = inizio;
        this.fine = fine;
        this.giorno = giorno;
        this.professore = professore;
        this.classe = classe;

    }

    public UUID getIdCorso() {
        return idCorso;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalTime getInizio() {
        return inizio;
    }

    public void setInizio(LocalTime inizio) {
        this.inizio = inizio;
    }

    public LocalTime getFine() {
        return fine;
    }

    public void setFine(LocalTime fine) {
        this.fine = fine;
    }

    public GiornoSettimana getGiorno() {
        return giorno;
    }

    public void setGiorno(GiornoSettimana giorno) {
        this.giorno = giorno;
    }

    public Professore getProfessore() {
        return professore;
    }

    public void setProfessore(Professore professore) {
        this.professore = professore;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public List<Studente> getStudenti() {
        return studenti;
    }

    public void setStudenti(List<Studente> studenti) {
        this.studenti = studenti;
    }

    @Override
    public String toString() {
        return "CorsiExtra{" +
                "idCorso=" + idCorso +
                ", nome='" + nome + '\'' +
                ", inizio=" + inizio +
                ", fine=" + fine +
                ", giorno=" + giorno +
                '}';
    }
}
