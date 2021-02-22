package ru.levelp;

import org.junit.Assert;
import org.junit.Test;
import org.levelp.model.Part;
import org.levelp.model.Storage;
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

            Storage storage = new Storage("Some storage");
            Part p = new Part("test-part", "Test part");
            p.setStorage(storage);

            manager.persist(storage);
            manager.persist(p);

            User found = manager.find(User.class, user.getId());
            Assert.assertNotNull(found);

            manager.refresh(found);
            manager.remove(found);

            manager.getTransaction().commit();
        } finally {
            manager.close();
            factory.close();
        }
    }

}
