package gentjanahani.registro_elettronico_backend.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="genitore")
public class Genitore {
    @Id
    @GeneratedValue
    private UUID idGenitore;

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

    public Genitore(){}

    public Genitore(String nome, String cognome, LocalDate dataDiNascita, User user) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.user = user;
    }

    public UUID getIdGenitore() {
        return idGenitore;
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


    @Override
    public String toString() {
        return "Genitore{" +
                "idGenitore=" + idGenitore +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataDiNascita=" + dataDiNascita +

                '}';
    }
}
