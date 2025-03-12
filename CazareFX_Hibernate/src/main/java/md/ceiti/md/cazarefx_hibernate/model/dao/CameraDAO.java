package md.ceiti.md.cazarefx_hibernate.model.dao;

import md.ceiti.md.cazarefx_hibernate.model.entity.Camera_Data;

import java.sql.SQLException;
import java.util.List;

public interface CameraDAO {
    List<Camera_Data> getAll() throws SQLException;
    int add(Camera_Data camera) throws SQLException;
    int update(Camera_Data camera) throws SQLException;
    int delete(Camera_Data camera) throws SQLException;
}
