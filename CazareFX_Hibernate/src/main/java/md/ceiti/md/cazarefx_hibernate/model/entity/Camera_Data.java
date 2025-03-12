package md.ceiti.md.cazarefx_hibernate.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "camera",schema = "public")
public class Camera_Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nume", nullable = false)
    private String nume;

    @Column(name = "tip_camera", nullable = false)
    private String Tip_camera;

    @Column(name = "statut", nullable = false)
    private String statut;

    @Column(name = "starea", nullable = false)
    private String starea;

    @Column(name = "pret", nullable = false)
    private String pret;


    public Camera_Data() {}


    public Camera_Data(String nume, String tip_camera, String statut, String starea, String pret) {
        this.nume = nume;
        this.Tip_camera = tip_camera;
        this.statut = statut;
        this.starea = starea;
        this.pret = pret;
    }

    public Camera_Data(int id, String nume, String tip_camera, String statut, String starea, String pret) {
        this.id = id;
        this.nume = nume;
        this.Tip_camera = tip_camera;
        this.statut = statut;
        this.starea = starea;
        this.pret = pret;
    }


    public long getId() {
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

    public String getTip_camera() {
        return Tip_camera;
    }

    public void setTip_camera(String tip_camera) {
        Tip_camera = tip_camera;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getStarea() {
        return starea;
    }

    public void setStarea(String starea) {
        this.starea = starea;
    }

    public String getPret() {
        return pret;
    }

    public void setPret(String pret) {
        this.pret = pret;
    }

    @Override
    public String toString() {
        return "Camera_Data{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", Tip_camera='" + Tip_camera + '\'' +
                ", statut='" + statut + '\'' +
                ", starea='" + starea + '\'' +
                ", pret='" + pret + '\'' +
                '}';
    }
}
