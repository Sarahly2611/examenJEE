package dao;

import model.Product;
import util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class ProductDAO {

    public void save(Product product) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(product);
        tx.commit();
        em.close();
    }

    public List<Product> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Product> list = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        em.close();
        return list;
    }

    public Product findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Product product = em.find(Product.class, id);
        em.close();
        return product;
    }

    public void update(Product product) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(product);
        tx.commit();
        em.close();
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Product product = em.find(Product.class, id);
        if (product != null) em.remove(product);
        tx.commit();
        em.close();
    }
}