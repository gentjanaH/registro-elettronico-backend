package gentjanahani.registro_elettronico_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID idUser;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;


    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false, name = "password_hash")
    private String password;

    @Column(name = "avatar_url")
    private String avatarUrl;

    //   Relazione ManyToOne con Ruolo
    @ManyToOne
    @JoinColumn(name = "id_ruolo")
    private Ruolo ruolo;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Professore professore;

    @OneToOne
    @JoinColumn(name = "studente_id_studente")
    @JsonIgnore
    private Studente studente;

    @OneToOne
    @JoinColumn(name = "genitore_id_genitore")
    @JsonIgnore
    private Genitore genitore;

    //    costruttore vuoto
    public User() {
    }

//    costruttore


    public User(String nome, String cognome, String email, String password, Ruolo ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
    }

    //    override dei metodi
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + ruolo.getRuolo()));
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    //    getter e setter
    public UUID getIdUser() {
        return idUser;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Professore getProfessore() {
        return professore;
    }

    public void setProfessore(Professore professore) {
        this.professore = professore;
    }

    public Studente getStudente() {
        return studente;
    }

    public void setStudente(Studente studente) {
        this.studente = studente;
    }

    public Genitore getGenitore() {
        return genitore;
    }

    public void setGenitore(Genitore genitore) {
        this.genitore = genitore;
    }

    //    to string


    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
