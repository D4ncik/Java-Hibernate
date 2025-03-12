package md.ceiti.md.cazarefx_hibernate.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "camera_tip")
public class CameraTip_Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nume", nullable = false)
    private String nume;

    @Column(name = "descriere", nullable = false)
    private String Descriere;

    @Column(name = "nr_camere", nullable = false)
    private String Nr_camera;

    @Column(name = "ocupata", nullable = false)
    private String ocupate;

    @Column(name = "disponibila", nullable = false)
    private String disponibile;

    // Constructor pentru Hibernate
    public CameraTip_Data() {}

    // Constructor cu parametri
    public CameraTip_Data(String nume, String descriere, String nr_camera, String ocupate, String disponibile) {
        this.nume = nume;
        this.Descriere = descriere;
        this.Nr_camera = nr_camera;
        this.ocupate = ocupate;
        this.disponibile = disponibile;
    }

    public CameraTip_Data(int id, String nume, String descriere, String nr_camera, String ocupate, String disponibile) {
        this.id = id;
        this.nume = nume;
        this.Descriere = descriere;
        this.Nr_camera = nr_camera;
        this.ocupate = ocupate;
        this.disponibile = disponibile;
    }

    // Getters și Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return Descriere;
    }

    public void setDescriere(String descriere) {
        this.Descriere = descriere;
    }

    public String getNr_camera() {
        return Nr_camera;
    }

    public void setNr_camera(String nr_camera) {
        this.Nr_camera = nr_camera;
    }

    public String getOcupate() {
        return ocupate;
    }

    public void setOcupate(String ocupate) {
        this.ocupate = ocupate;
    }

    public String getDisponibile() {
        return disponibile;
    }

    public void setDisponibile(String disponibile) {
        this.disponibile = disponibile;
    }

    @Override
    public String toString() {
        return "CameraTip_Data{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", Descriere='" + Descriere + '\'' +
                ", Nr_camera='" + Nr_camera + '\'' +
                ", ocupate='" + ocupate + '\'' +
                ", disponibile='" + disponibile + '\'' +
                '}';
    }
}
