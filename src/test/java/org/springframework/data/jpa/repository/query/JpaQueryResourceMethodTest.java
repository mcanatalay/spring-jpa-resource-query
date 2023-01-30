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
class JpaQueryResourceMethodTest {
    @Test
    void givenRepositoryMethodHasQueryWhenAnnotatedValueFetchedThenReturnAnnotationQueryValue() throws NoSuchMethodException {
        Method method = TestRepository.class.getMethod("getData");
        RepositoryMetadata repositoryMetadata = mock(RepositoryMetadata.class);
        TypeInformation typeInformation = mock(TypeInformation.class);
        doReturn(method.getReturnType())
                .when(typeInformation).getType();
        doReturn(typeInformation)
                .when(repositoryMetadata).getReturnType(any());
        doReturn(method.getReturnType())
                .when(repositoryMetadata).getReturnedDomainClass(any());
        ProjectionFactory projectionFactory = mock(ProjectionFactory.class);
        QueryExtractor extractor = mock(QueryExtractor.class);

        JpaQueryResourceMethod queryResourceMethod = new JpaQueryResourceMethod(
                method,
                repositoryMetadata,
                projectionFactory,
                extractor
        );
        assertEquals("SELECT data FROM test LIMIT 1", queryResourceMethod.getAnnotatedQuery());
    }

    @Test
    void givenRepositoryMethodHasResourceAddressWhenAnnotatedValueFetchedThenReturnResourceFileContent() throws NoSuchMethodException {
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
        QueryExtractor extractor = mock(QueryExtractor.class);

        JpaQueryResourceMethod queryResourceMethod = new JpaQueryResourceMethod(
                method,
                repositoryMetadata,
                projectionFactory,
                extractor
        );
        assertEquals("SELECT data FROM test LIMIT 1", queryResourceMethod.getAnnotatedQuery());
    }

    @Test
    void givenRepositoryMethodHasResourceAddressWithoutFileWhenAnnotatedValueFetchedThenReturnNull() throws NoSuchMethodException {
        Method method = TestRepository.class.getMethod("getDataWithWrongResource");
        RepositoryMetadata repositoryMetadata = mock(RepositoryMetadata.class);
        TypeInformation typeInformation = mock(TypeInformation.class);
        doReturn(method.getReturnType())
                .when(typeInformation).getType();
        doReturn(typeInformation)
                .when(repositoryMetadata).getReturnType(any());
        doReturn(method.getReturnType())
                .when(repositoryMetadata).getReturnedDomainClass(any());
        ProjectionFactory projectionFactory = mock(ProjectionFactory.class);
        QueryExtractor extractor = mock(QueryExtractor.class);

        JpaQueryResourceMethod queryResourceMethod = new JpaQueryResourceMethod(
                method,
                repositoryMetadata,
                projectionFactory,
                extractor
        );
        assertNull(queryResourceMethod.getAnnotatedQuery());
    }

    @Test
    void givenRepositoryMethodHasQueryWhenRequiredAnnotatedValueFetchedThenReturnAnnotationQueryValue() throws NoSuchMethodException {
        Method method = TestRepository.class.getMethod("getData");
        RepositoryMetadata repositoryMetadata = mock(RepositoryMetadata.class);
        TypeInformation typeInformation = mock(TypeInformation.class);
        doReturn(method.getReturnType())
                .when(typeInformation).getType();
        doReturn(typeInformation)
                .when(repositoryMetadata).getReturnType(any());
        doReturn(method.getReturnType())
                .when(repositoryMetadata).getReturnedDomainClass(any());
        ProjectionFactory projectionFactory = mock(ProjectionFactory.class);
        QueryExtractor extractor = mock(QueryExtractor.class);

        JpaQueryResourceMethod queryResourceMethod = new JpaQueryResourceMethod(
                method,
                repositoryMetadata,
                projectionFactory,
                extractor
        );
        assertEquals("SELECT data FROM test LIMIT 1", queryResourceMethod.getRequiredAnnotatedQuery());
    }

    @Test
    void givenRepositoryMethodHasResourceAddressWhenRequiredAnnotatedValueFetchedThenReturnResourceFileContent() throws NoSuchMethodException {
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
        QueryExtractor extractor = mock(QueryExtractor.class);

        JpaQueryResourceMethod queryResourceMethod = new JpaQueryResourceMethod(
                method,
                repositoryMetadata,
                projectionFactory,
                extractor
        );
        assertEquals("SELECT data FROM test LIMIT 1", queryResourceMethod.getRequiredAnnotatedQuery());
    }

    @Test
    void givenRepositoryMethodHasResourceAddressWithoutFileWhenRequiredAnnotatedValueFetchedThenThrowIllegalStateException() throws NoSuchMethodException {
        Method method = TestRepository.class.getMethod("getDataWithWrongResource");
        RepositoryMetadata repositoryMetadata = mock(RepositoryMetadata.class);
        TypeInformation typeInformation = mock(TypeInformation.class);
        doReturn(method.getReturnType())
                .when(typeInformation).getType();
        doReturn(typeInformation)
                .when(repositoryMetadata).getReturnType(any());
        doReturn(method.getReturnType())
                .when(repositoryMetadata).getReturnedDomainClass(any());
        ProjectionFactory projectionFactory = mock(ProjectionFactory.class);
        QueryExtractor extractor = mock(QueryExtractor.class);

        JpaQueryResourceMethod queryResourceMethod = new JpaQueryResourceMethod(
                method,
                repositoryMetadata,
                projectionFactory,
                extractor
        );
        assertThrows(IllegalStateException.class, queryResourceMethod::getRequiredAnnotatedQuery);
    }
}
