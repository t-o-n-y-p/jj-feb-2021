package org.levelp.model;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

    public List<Part> findAllSortedBy(String columnName) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Part> query = builder.createQuery(Part.class);
        Root<Part> fromPart = query.from(Part.class);

        query.orderBy(builder.asc(fromPart.get(columnName)));

        return manager.createQuery(query).getResultList();
    }

}