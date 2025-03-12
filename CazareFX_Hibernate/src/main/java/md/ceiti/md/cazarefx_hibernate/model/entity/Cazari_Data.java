package md.ceiti.md.cazarefx_hibernate.model.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "cazari")  // Numele tabelului în baza de date
public class Cazari_Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generare ID (în cazul în care este autoincrement)
    private int id;

    @Column(name = "client", nullable = false)
    private String client;

    @Column(name = "camera", nullable = false)
    private String camera;

    @Column(name = "nr_camera", nullable = false)
    private int Nr_camera;

    @Column(name = "statut", nullable = false)
    private String statut;

    @Column(name = "data_in", nullable = false)
    private String Data_in;

    @Column(name = "data_out", nullable = false)
    private String Data_out;

    @Column(name = "pret", nullable = false)
    private String pret;

    @Column(name = "zile", nullable = false)
    private int zile;

    // Constructor pentru Hibernate
    public Cazari_Data() {}

    // Constructor cu parametri
    public Cazari_Data(String client, String camera, int nr_camera, String statut, String data_in, String data_out, String pret, int zile) {
        this.client = client;
        this.camera = camera;
        Nr_camera = nr_camera;
        this.statut = statut;
        Data_in = data_in;
        Data_out = data_out;
        this.pret = pret;
        this.zile = zile;
    }

    // Getters și Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public int getNr_camera() {
        return Nr_camera;
    }

    public void setNr_camera(int nr_camera) {
        Nr_camera = nr_camera;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getData_in() {
        return Data_in;
    }

    public void setData_in(String data_in) {
        Data_in = data_in;
    }

    public String getData_out() {
        return Data_out;
    }

    public void setData_out(String data_out) {
        Data_out = data_out;
    }

    public String getPret() {
        return pret;
    }

    public void setPret(String pret) {
        this.pret = pret;
    }

    public int getZile() {
        return zile;
    }

    public void setZile(int zile) {
        this.zile = zile;
    }

    @Override
    public String toString() {
        return "Cazari_Data{" +
                "id=" + id +
                ", client='" + client + '\'' +
                ", camera='" + camera + '\'' +
                ", Nr_camera=" + Nr_camera +
                ", statut='" + statut + '\'' +
                ", Data_in='" + Data_in + '\'' +
                ", Data_out='" + Data_out + '\'' +
                ", pret='" + pret + '\'' +
                ", zile=" + zile +
                '}';
    }
}
