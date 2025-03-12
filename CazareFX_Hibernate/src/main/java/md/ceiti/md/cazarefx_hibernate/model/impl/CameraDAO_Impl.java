package md.ceiti.md.cazarefx_hibernate.model.impl;

import md.ceiti.md.cazarefx_hibernate.model.dao.CameraDAO;
import md.ceiti.md.cazarefx_hibernate.model.entity.Camera_Data;
import md.ceiti.md.cazarefx_hibernate.model.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class CameraDAO_Impl implements CameraDAO {

    @Override
    public List<Camera_Data> getAll() throws SQLException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Camera_Data> cameraList = session.createQuery("FROM Camera_Data", Camera_Data.class).getResultList();
            if (cameraList.isEmpty()) {
                System.out.println("No camera data found.");
            }
            return cameraList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving camera data", e);
        }
    }

    @Override
    public int add(Camera_Data camera) throws SQLException {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(camera);
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new SQLException("Error adding camera", e);
        }
    }

    @Override
    public int update(Camera_Data camera) throws SQLException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(camera);
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new SQLException("Error updating camera", e);
        }
    }

    @Override
    public int delete(Camera_Data camera) throws SQLException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(camera);
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new SQLException("Error deleting camera", e);
        }
    }
}
