package md.ceiti.md.cazarefx_hibernate.model.entity;

import jakarta.persistence.*;

@Entity  // Face clasa o entitate Hibernate
@Table(name = "clienti") // Trebuie să corespundă numelui tabelului din PostgreSQL
public class Clienti_Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nume")
    private String nume;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "email")
    private String email;

    @Column(name = "genul")
    private String gen;

    @Column(name = "categorie")
    private String categorie;

    public Clienti_Data() {}

    public Clienti_Data(int id, String nume, String telefon, String email, String gen, String categorie) {
        this.id = id;
        this.nume = nume;
        this.telefon = telefon;
        this.email = email;
        this.gen = gen;
        this.categorie = categorie;
    }

    public Clienti_Data(String nume, String telefon, String email, String gen, String categorie) {
        this.nume = nume;
        this.telefon = telefon;
        this.email = email;
        this.gen = gen;
        this.categorie = categorie;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getGen() { return gen; }
    public void setGen(String gen) { this.gen = gen; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    @Override
    public String toString() {
        return "Clienti_Data{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", telefon='" + telefon + '\'' +
                ", email='" + email + '\'' +
                ", gen='" + gen + '\'' +
                ", categorie='" + categorie + '\'' +
                '}';
    }
}
