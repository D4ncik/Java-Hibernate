package md.ceiti.md.cazarefx_hibernate.model.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import md.ceiti.md.cazarefx_hibernate.model.*;
import java.sql.SQLException;
import java.util.List;

public abstract class GenericDAO<T> implements DAO<T> {
    private final Class<T> type;

    public GenericDAO(Class<T> type) {
        this.type = type;
    }

    @Override
    public T get(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(type, id);
        }
    }

    @Override
    public List<T> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from " + type.getSimpleName(), type).list();
        }
    }

    @Override
    public int save(T t) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(t);
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int insert(T t) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(t);
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(T t) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(t);
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(entity);  // Șterge obiectul direct
            transaction.commit();
            return 1; // Succesul ștergerii
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return 0; // Eșecul ștergerii
        }
    }


}
