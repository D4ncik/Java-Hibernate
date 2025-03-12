package md.ceiti.md.cazarefx_hibernate.model.impl;


import md.ceiti.md.cazarefx_hibernate.model.dao.CameraTipDAO;
import md.ceiti.md.cazarefx_hibernate.model.entity.CameraTip_Data;
import md.ceiti.md.cazarefx_hibernate.model.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class CameraTipDAO_Impl implements CameraTipDAO {

    public int insertTipCamera(CameraTip_Data cameraTip) throws SQLException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(cameraTip);

            transaction.commit();

            return 1;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Eroare la inserarea tipului de cameră", e);
        }
    }

    @Override
    public List<CameraTip_Data> getAll() throws SQLException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM CameraTip_Data", CameraTip_Data.class).getResultList();
        } catch (Exception e) {
            throw new SQLException("Error retrieving room types", e);
        }
    }

    @Override
    public int addCameraTip(CameraTip_Data cameraTip) throws SQLException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(cameraTip);
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Error adding room type", e);
        }
    }

    @Override
    public int updateCameraTip(CameraTip_Data cameraTip) throws SQLException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(cameraTip);
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Error updating room type", e);
        }
    }

    @Override
    public int deleteCameraTip(CameraTip_Data cameraTip) throws SQLException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(cameraTip);
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Error deleting room type", e);
        }
    }
}
