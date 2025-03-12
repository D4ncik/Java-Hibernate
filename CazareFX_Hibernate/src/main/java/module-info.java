module md.ceiti.md.cazarefx_hibernate {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires jakarta.transaction;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;

    opens md.ceiti.md.cazarefx_hibernate to javafx.fxml;
    exports md.ceiti.md.cazarefx_hibernate;
}