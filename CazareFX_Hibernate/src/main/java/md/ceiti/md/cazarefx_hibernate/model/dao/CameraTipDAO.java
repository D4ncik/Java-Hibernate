package md.ceiti.md.cazarefx_hibernate.model.dao;

import md.ceiti.md.cazarefx_hibernate.model.entity.CameraTip_Data;
import java.sql.SQLException;
import java.util.List;

public interface CameraTipDAO {
    List<CameraTip_Data> getAll() throws SQLException;
    int addCameraTip(CameraTip_Data cameraTip) throws SQLException;
    int updateCameraTip(CameraTip_Data cameraTip) throws SQLException;
    int deleteCameraTip(CameraTip_Data cameraTip) throws SQLException;

    int insertTipCamera(CameraTip_Data newTipCamera) throws SQLException;
}
