package md.ceiti.md.cazarefx_hibernate.model.impl;


import md.ceiti.md.cazarefx_hibernate.model.dao.ClientiDAO;
import md.ceiti.md.cazarefx_hibernate.model.entity.Clienti_Data;
import md.ceiti.md.cazarefx_hibernate.model.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class ClientiDAO_Impl implements ClientiDAO {

    public void insertClient(Clienti_Data client) throws SQLException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(client);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new SQLException("Eroare la inserarea clientului.", e);
        }
    }

    @Override
    public void saveClient(Clienti_Data client) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(client);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public int updateClient(Clienti_Data client) {
        int rowsAffected = 0;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(client);
            rowsAffected = 1;
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return rowsAffected;
    }


    public int deleteClient(Clienti_Data client){
        int rowsAffected = 0;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(client);
            rowsAffected = 1;

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            rowsAffected = 0;
        }
        return rowsAffected;
    }


    @Override
    public Clienti_Data getClientById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Clienti_Data.class, id);
        }
    }

    @Override
    public List<Clienti_Data> getAllClients() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Clienti_Data", Clienti_Data.class).list();
        }
    }
}
