package org.springframework.data.jpa.repository.query;

import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.provider.QueryExtractor;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.RepositoryMetadata;

import javax.persistence.EntityManager;
import java.lang.reflect.Method;

public class JpaQueryResourceMethodFactory implements JpaQueryMethodFactory {
    private final QueryExtractor extractor;

    public JpaQueryResourceMethodFactory(QueryExtractor extractor) {
        this.extractor = extractor;
    }

    public JpaQueryResourceMethodFactory(EntityManager entityManager) {
        this.extractor = PersistenceProvider.fromEntityManager(entityManager);
    }

    @Override
    public JpaQueryMethod build(Method method, RepositoryMetadata metadata, ProjectionFactory factory) {
        return new JpaQueryResourceMethod(method, metadata, factory, this.extractor);
    }
}
