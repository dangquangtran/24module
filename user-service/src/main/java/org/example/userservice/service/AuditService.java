package org.example.userservice.service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.userservice.entity.User;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService implements IAuditService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getRevisions(Long id) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        return auditReader.createQuery()
                .forRevisionsOfEntity(User.class, false, true)
                .add(AuditEntity.id().eq(id))
                .getResultList();
    }
}
