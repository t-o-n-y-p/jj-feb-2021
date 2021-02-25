package org.levelp.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.levelp.TestConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PartsDAOTest {

    @Autowired
    private EntityManager manager;

    @Autowired
    private PartsDAO partsDAO;

    @Before
    public void configure() {
        manager.getTransaction().begin();
        Storage newStorage = new Storage("test-storage");
        Part newPart = new Part("111", "My part");
        newPart.setStorage(newStorage);

        manager.persist(newStorage);
        manager.persist(newPart);
        manager.getTransaction().commit();
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