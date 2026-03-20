package gentjanahani.registro_elettronico_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "studente")
public class Studente {
    @Id
    @GeneratedValue
    private UUID idStudente;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private LocalDate dataDiNascita;

    //    relazione OneToOne con user
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    //    relazione ManyToOne con genitore
    @ManyToOne
    @JoinColumn(name = "id_genitore")
    @JsonIgnore
    private Genitore genitore;

    //    relazione ManyToOne con classe
    @ManyToOne
    @JoinColumn(name = "id_classe")
    private Classe classe;


    //  costruttore vuoto
    public Studente() {
    }

    //  costruttore

    public Studente(String nome, String cognome, LocalDate dataDiNascita, User user, Genitore genitore, Classe classe) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.user = user;
        this.genitore = genitore;
        this.classe = classe;
    }


    //  getter e setter

    public UUID getIdStudente() {
        return idStudente;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Genitore getGenitore() {
        return genitore;
    }

    public void setGenitore(Genitore genitore) {
        this.genitore = genitore;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    //  to String

    @Override
    public String toString() {
        return "Studente{" +
                "idStudente=" + idStudente +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataDiNascita=" + dataDiNascita +

                '}';
    }
}
