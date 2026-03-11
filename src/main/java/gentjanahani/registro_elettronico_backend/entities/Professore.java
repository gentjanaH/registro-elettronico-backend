package gentjanahani.registro_elettronico_backend.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="professore")
public class Professore {

    @Id
    @GeneratedValue
    private UUID idProfessore;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private LocalDate dataDiNascita;

    //    relazione OneToOne con user
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    //   junction table con materia
    @ManyToMany
    @JoinTable(
            name="professore_materia",
            joinColumns = @JoinColumn(name = "id_professore"),
            inverseJoinColumns = @JoinColumn(name = "id_materia")
    )
    private List<Materia> materie= new ArrayList<>();


    //    costruttore vuoto
    public Professore (){}

    //    costruttore

    public Professore(String nome, String cognome, LocalDate dataDiNascita, User user, List<Materia> materie) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.user = user;
        this.materie = materie;
    }


    //    getter e setter

    public UUID getIdProfessore() {
        return idProfessore;
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

    public List<Materia> getMaterie() {
        return materie;
    }

    public void setMaterie(List<Materia> materie) {
        this.materie = materie;
    }


//    toString
    @Override
    public String toString() {
        return "Professore{" +
                "idProfessore=" + idProfessore +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataDiNascita=" + dataDiNascita +

                '}';
    }
}
