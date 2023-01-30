package org.springframework.data.jpa.repository.query;

import com.example.TestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.provider.QueryExtractor;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.util.TypeInformation;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaQueryResourceMethodFactoryTest {
    @Test
    void givenExtractorIsNullWhenFactoryInitializedThenThrowsIllegalArgumentException() {
        QueryExtractor extractor = null;
        assertThrows(IllegalArgumentException.class, () -> new JpaQueryResourceMethodFactory(extractor));
    }

    @Test
    void givenExtractorIsNotNullWhenFactoryInitializedThenInitializeFactory() {
        QueryExtractor extractor = mock(QueryExtractor.class);
        assertDoesNotThrow(() -> {
            JpaQueryResourceMethodFactory factory = new JpaQueryResourceMethodFactory(extractor);
            assertNotNull(factory);
        });
    }

    @Test
    void whenFactoryBuildMethodCalledThenItDoesntThrowException() throws NoSuchMethodException {
        QueryExtractor extractor = mock(QueryExtractor.class);
        JpaQueryResourceMethodFactory factory = new JpaQueryResourceMethodFactory(extractor);

        Method method = TestRepository.class.getMethod("getDataWithResource");
        RepositoryMetadata repositoryMetadata = mock(RepositoryMetadata.class);
        TypeInformation typeInformation = mock(TypeInformation.class);
        doReturn(method.getReturnType())
                .when(typeInformation).getType();
        doReturn(typeInformation)
                .when(repositoryMetadata).getReturnType(any());
        doReturn(method.getReturnType())
                .when(repositoryMetadata).getReturnedDomainClass(any());
        ProjectionFactory projectionFactory = mock(ProjectionFactory.class);

        assertDoesNotThrow(() -> {
            JpaQueryMethod queryMethod = factory.build(method, repositoryMetadata, projectionFactory);
            assertNotNull(queryMethod);
        });
    }

    @Test
    void whenFactoryBuildMethodCalledThenItCreatesANewJpaQueryMethodInstance() throws NoSuchMethodException {
        QueryExtractor extractor = mock(QueryExtractor.class);
        JpaQueryResourceMethodFactory factory = new JpaQueryResourceMethodFactory(extractor);

        Method method = TestRepository.class.getMethod("getDataWithResource");
        RepositoryMetadata repositoryMetadata = mock(RepositoryMetadata.class);
        TypeInformation typeInformation = mock(TypeInformation.class);
        doReturn(method.getReturnType())
                .when(typeInformation).getType();
        doReturn(typeInformation)
                .when(repositoryMetadata).getReturnType(any());
        doReturn(method.getReturnType())
                .when(repositoryMetadata).getReturnedDomainClass(any());
        ProjectionFactory projectionFactory = mock(ProjectionFactory.class);

        JpaQueryMethod queryMethod1 = factory.build(method, repositoryMetadata, projectionFactory);
        assertNotNull(queryMethod1);
        JpaQueryMethod queryMethod2 = factory.build(method, repositoryMetadata, projectionFactory);
        assertNotNull(queryMethod2);
        assertNotEquals(queryMethod1, queryMethod2);
    }
}
