package gentjanahani.registro_elettronico_backend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "materia")
public class Materia {

    @Id
    @GeneratedValue
    private UUID idMateria;

    @Column(nullable = false, unique = true)
    private String nome;

    @ManyToMany(mappedBy = "materie")
    private List<Professore> professori = new ArrayList<>();


    public Materia() {
    }

    public Materia(String nome) {

        this.nome = nome;

    }

    public UUID getIdMateria() {
        return idMateria;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Professore> getProfessori() {
        return professori;
    }

    public void setProfessori(List<Professore> professori) {
        this.professori = professori;
    }

    @Override
    public String toString() {
        return "Materia{" +
                "idMateria=" + idMateria +
                ", nome='" + nome + '\'' +
                '}';
    }
}
