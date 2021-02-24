package org.levelp.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

import static org.junit.Assert.*;

public class PartsDAOTest {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private PartsDAO partsDAO;

    @Before
    public void configure() {
        factory = Persistence.createEntityManagerFactory(
                "TestPersistenceUnit"
        );
        manager = factory.createEntityManager();
        partsDAO = new PartsDAO(manager);

        manager.getTransaction().begin();
        Storage newStorage = new Storage("test-storage");
        Part newPart = new Part("111", "My part");
        newPart.setStorage(newStorage);

        manager.persist(newStorage);
        manager.persist(newPart);
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
    public void findByStorageTitle() {
        assertTrue(partsDAO.findByStorageTitle("test-storage-11").isEmpty());
        assertEquals("My part", partsDAO.findByStorageTitle("test-storage").get(0).getTitle());
    }

    @Test
    public void findAllSortedBy() {
        assertEquals("My part", partsDAO.findAllSortedBy("title").get(0).getTitle());

        boolean failed = false;
        try {
            partsDAO.findAllSortedBy("non-existing-column");
        } catch (Throwable expected) {
            failed = true;
        }
        if (!failed) {
            fail("Shouldn't pass here");
        }
    }
}