package org.levelp.model;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class PartsDAO {
    private final EntityManager manager;

    public PartsDAO(EntityManager manager) {
        this.manager = manager;
    }

    public Part findByPartId(String partId) {
        try {
            return manager.createQuery(
                    "from Part where partId = :id",
                    Part.class
            ).setParameter("id", partId)
                    .getSingleResult();
        } catch (NoResultException notFound) {
            return null;
        }
    }

    public List<Part> findAll() {
        return manager.createQuery("from Part", Part.class).getResultList();
    }

    public List<Part> findByStorageTitle(String title) {
        return manager.createQuery(
                "from Part part where part.storage.title = :title",
                Part.class
        ).setParameter("title", title)
                .getResultList();
    }
}