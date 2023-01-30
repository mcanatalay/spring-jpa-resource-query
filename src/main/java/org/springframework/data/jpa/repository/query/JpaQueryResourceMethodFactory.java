package org.springframework.data.jpa.repository.query;

import org.springframework.data.jpa.provider.QueryExtractor;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

public class JpaQueryResourceMethodFactory implements JpaQueryMethodFactory {
    private final QueryExtractor extractor;

    public JpaQueryResourceMethodFactory(QueryExtractor extractor) {
        Assert.notNull(extractor, "QueryExtractor must not be null");
        this.extractor = extractor;
    }

    @Override
    public JpaQueryMethod build(Method method, RepositoryMetadata metadata, ProjectionFactory factory) {
        return new JpaQueryResourceMethod(method, metadata, factory, this.extractor);
    }
}
