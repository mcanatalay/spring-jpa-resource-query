package org.springframework.data.jpa.repository.query;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.provider.QueryExtractor;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class JpaQueryResourceMethod extends JpaQueryMethod {
    private static final ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();

    protected JpaQueryResourceMethod(Method method, RepositoryMetadata metadata, ProjectionFactory factory, QueryExtractor extractor) {
        super(method, metadata, factory, extractor);
    }

    @Override
    String getAnnotatedQuery() {
        String value = super.getAnnotatedQuery();
        if (isResourcePath(value)) return getAnnotatedQueryFromResource(value);
        return value;
    }

    @Override
    String getRequiredAnnotatedQuery() throws IllegalStateException {
        String value = super.getRequiredAnnotatedQuery();
        if (isResourcePath(value)) return getAnnotatedQueryFromResource(value);
        return value;
    }

    private boolean isResourcePath(@Nullable String value) {
        return value != null && value.toLowerCase().startsWith("classpath:");
    }

    private String getAnnotatedQueryFromResource(@NonNull String path) {
        Resource resource = RESOURCE_LOADER.getResource(path);
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            return null;
        }
    }
}
