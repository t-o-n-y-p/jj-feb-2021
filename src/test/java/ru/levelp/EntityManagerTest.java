package ru.levelp;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.levelp.model.Part;
import org.levelp.model.Storage;
import org.levelp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class EntityManagerTest {

    @Autowired
    private EntityManager manager;

    @Test
    public void smokeTest() {


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

        manager.getTransaction().commit();
        manager.refresh(found);
        manager.remove(found);

    }

}
