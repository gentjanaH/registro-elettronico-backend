package gentjanahani.registro_elettronico_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "classe")
public class Classe {

    @Id
    @GeneratedValue
    private UUID idClasse;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private int capienzaMax;

    //   relazione ManyToMany con professore
    @ManyToMany
    @JoinTable(
            name = "classe_professore",
            joinColumns = @JoinColumn(name = "id_classe"),
            inverseJoinColumns = @JoinColumn(name = "id_professore")
    )
    private List<Professore> listaProfessori;


    //   relazione OneToMany con studente
    @OneToMany(mappedBy = "classe")
    @JsonIgnore
    private List<Studente> listaStudenti;


    //    costruttore vuoto
    public Classe() {
    }

    //    costruttore

    public Classe(String nome, int capienzaMax) {
        this.nome = nome;
        this.capienzaMax = capienzaMax;

    }

    //    getter e setter

    public UUID getIdClasse() {
        return idClasse;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapienzaMax() {
        return capienzaMax;
    }

    public void setCapienzaMax(int capienzaMax) {
        this.capienzaMax = capienzaMax;
    }

    public List<Professore> getListaProfessori() {
        return listaProfessori;
    }

    public void setListaProfessori(List<Professore> listaProfessori) {
        this.listaProfessori = listaProfessori;
    }

    public List<Studente> getListaStudenti() {
        return listaStudenti;
    }

    public void setListaStudenti(List<Studente> listaStudenti) {
        this.listaStudenti = listaStudenti;
    }

    //    to String


    @Override
    public String toString() {
        return "Classe{" +
                "idClasse=" + idClasse +
                ", nome='" + nome + '\'' +
                ", capienzaMax=" + capienzaMax +

                '}';
    }
}
