package ru.levelp;

import org.junit.Test;
import org.levelp.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerTest {

    private EntityManagerFactory factory;
    private EntityManager manager;

    @Test
    public void smokeTest() {
        factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        manager = factory.createEntityManager();

        try {
            manager.getTransaction().begin();
            User user = new User("test", "aaa", true);
            manager.persist(user);
            manager.getTransaction().commit();
        } finally {
            manager.close();
            factory.close();
        }
    }

}
