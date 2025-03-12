package md.ceiti.md.cazarefx_hibernate.model.dao;


import md.ceiti.md.cazarefx_hibernate.model.entity.Clienti_Data;

import java.sql.SQLException;
import java.util.List;

public interface ClientiDAO {
    void saveClient(Clienti_Data client);
    int updateClient(Clienti_Data client);
    int deleteClient(Clienti_Data client);
    Clienti_Data getClientById(int id);
    List<Clienti_Data> getAllClients();
    void insertClient(Clienti_Data client) throws SQLException;
}
