package gentjanahani.registro_elettronico_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "professore")
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
    @OneToOne(mappedBy = "professore", fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;
    ;

    //   junction table con materia
    @ManyToMany
    @JoinTable(
            name = "professore_materia",
            joinColumns = @JoinColumn(name = "id_professore"),
            inverseJoinColumns = @JoinColumn(name = "id_materia")
    )
    private Set<Materia> materie = new HashSet<>();

    @ManyToMany(mappedBy = "listaProfessori")
    @JsonIgnore
    private Set<Classe> classi = new HashSet<>();


    //    costruttore vuoto
    public Professore() {
    }

    //    costruttore

    public Professore(String nome, String cognome, LocalDate dataDiNascita, User user) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.user = user;
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

    public Set<Materia> getMaterie() {
        return materie;
    }

    public void setMaterie(Set<Materia> materie) {
        this.materie = materie;
    }

    public Set<Classe> getClassi() {
        return classi;
    }

    public void setClassi(Set<Classe> classi) {
        this.classi = classi;
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
