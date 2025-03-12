package md.ceiti.md.cazarefx_hibernate.model.impl;


import md.ceiti.md.cazarefx_hibernate.model.dao.CazariDAO;
import md.ceiti.md.cazarefx_hibernate.model.entity.Cazari_Data;
import md.ceiti.md.cazarefx_hibernate.model.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class CazariDAO_Impl implements CazariDAO {

    @Override
    public List<Cazari_Data> getAll() throws SQLException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Cazari_Data ", Cazari_Data.class).getResultList();
        } catch (Exception e) {
            throw new SQLException("Error retrieving booking data", e);
        }
    }

    @Override
    public int addCazari(Cazari_Data cazare) throws SQLException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(cazare);
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Error adding booking", e);
        }
    }

    @Override
    public int updateCazari(Cazari_Data cazare) throws SQLException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(cazare);
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Error updating booking", e);
        }
    }

    @Override
    public int deleteCazari(Cazari_Data cazare) throws SQLException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(cazare);
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Error deleting booking", e);
        }
    }
}
