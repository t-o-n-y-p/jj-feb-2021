package org.levelp.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class UsersDAOTest {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private UsersDAO usersDAO;
    private Date now = new Date();

    @Before
    public void configure() {
        factory = Persistence.createEntityManagerFactory(
                "TestPersistenceUnit"
        );
        manager = factory.createEntityManager();
        usersDAO = new UsersDAO(manager);

        User newUser = new User("login777", "pass", false);
        newUser.setBirthDate(now);
        manager.getTransaction().begin();
        manager.persist(newUser);
        manager.getTransaction().commit();
    }

    @After
    public void cleanup() {
        if (manager != null) {
            manager.close();
        }
        if (factory != null) {
            factory.close();
        }
    }

    @Test
    public void findByLogin() {
        assertNull(usersDAO.findByLogin("non existing user"));

        User found = usersDAO.findByLogin("login777");
        assertNotNull(found);
        assertEquals("login777", found.getLogin());
    }

    @Test
    public void findByLoginAndPassword() {
        assertNull(usersDAO.findByLoginAndPassword("some user", "ppp"));
        assertNull(usersDAO.findByLoginAndPassword("login777", "pass1"));

        User found = usersDAO.findByLoginAndPassword("login777", "pass");
        assertNotNull(found);
        assertEquals("login777", found.getLogin());
        assertEquals("pass", found.getPassword());
    }

    @Test
    public void findByBirthDate() {
        List<User> foundByNow = usersDAO.findByBirthDate(now);
        assertEquals(1, foundByNow.size());
        assertEquals("login777", foundByNow.get(0).getLogin());

        Date dateBefore = new Date(now.getTime() - 100000000);
        List<User> foundBefore = usersDAO.findByBirthDate(dateBefore);
        assertEquals(0, foundBefore.size());
    }
}