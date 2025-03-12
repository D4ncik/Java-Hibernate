package md.ceiti.md.cazarefx_hibernate.model.dao;

import jakarta.transaction.SystemException;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    T get(int id) throws SQLException;
    List<T> getAll() throws SQLException;
    int save(T t) throws SQLException, SystemException;
    int insert(T t)throws SQLException;
    int update(T t) throws SQLException, SystemException;
    int delete(T t) throws SQLException, SystemException;

}
