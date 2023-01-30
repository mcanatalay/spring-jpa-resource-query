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
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JpaQueryResourceMethod extends JpaQueryMethod {
    private static final ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();
    private static final Map<String, String> RESOURCE_QUERY_MAP = new HashMap<>();

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
        String query = this.getAnnotatedQuery();
        if (query != null) {
            return query;
        } else {
            throw new IllegalStateException(String.format("No annotated query found for query method %s!", this.getName()));
        }
    }

    private boolean isResourcePath(@Nullable String value) {
        return value != null && value.toLowerCase().startsWith("classpath:");
    }

    private String getAnnotatedQueryFromResource(@NonNull String path) {
        if (!RESOURCE_QUERY_MAP.containsKey(path)) {
            Optional<String> queryFromResource = getQueryFromResource(path);
            queryFromResource.ifPresent(query -> RESOURCE_QUERY_MAP.put(path, query));
        }

        return RESOURCE_QUERY_MAP.get(path);
    }

    private Optional<String> getQueryFromResource(@NonNull String path) {
        String query;
        Resource resource = RESOURCE_LOADER.getResource(path);
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            query = FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            query = null;
        }

        return StringUtils.hasText(query) ? Optional.of(query) : Optional.empty();
    }
}
