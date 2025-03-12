package md.ceiti.md.cazarefx_hibernate.model.dao;

import md.ceiti.md.cazarefx_hibernate.model.entity.Cazari_Data;
import java.util.List;

import java.sql.SQLException;
import java.util.List;

public interface CazariDAO {
    List<Cazari_Data> getAll() throws SQLException;
    int addCazari(Cazari_Data cazare) throws SQLException;
    int updateCazari(Cazari_Data cazare) throws SQLException;
    int deleteCazari(Cazari_Data cazare) throws SQLException;
}


