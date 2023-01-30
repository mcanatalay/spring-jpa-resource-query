package org.springframework.boot.autoconfigure.data.jpa;

import com.example.ExampleConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.query.JpaQueryMethodFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;

import javax.persistence.EntityManager;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@EnableJpaRepositories
class JpaQueryResourceMethodFactoryAutoConfigurationTest {
    @Test
    void givenAllConditionsMeetWhenAutoConfigurationCalledThenCreateFactoryBean() {
        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(
                        org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration.class,
                        org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
                        org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
                        JpaQueryResourceMethodFactoryAutoConfiguration.class
                ))
                .withUserConfiguration(ExampleConfiguration.class)
                .run(context -> {
                    assertDoesNotThrow(() -> context.getBean(JpaQueryMethodFactory.class));
                    assertNotNull(context.getBean(JpaQueryMethodFactory.class));
                });
    }

    @Test
    void givenJpaQueryMethodFactoryAlreadyExistsWhenAutoConfigurationCalledThenCreateNoNewFactoryBean() {
        JpaQueryMethodFactory factory = mock(JpaQueryMethodFactory.class);
        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(
                        org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
                        org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
                        JpaQueryResourceMethodFactoryAutoConfiguration.class
                ))
                .withUserConfiguration(ExampleConfiguration.class)
                .withBean("testFactoryBean", JpaQueryMethodFactory.class, () -> factory)
                .run(context -> {
                    assertDoesNotThrow(() -> context.getBeansOfType(JpaQueryMethodFactory.class));
                    Map<String, JpaQueryMethodFactory> beanMap = context.getBeansOfType(JpaQueryMethodFactory.class);
                    assertEquals(1, beanMap.size());
                    assertEquals(factory, beanMap.get("testFactoryBean"));
                });
    }

    @Test
    void givenJpaRepositoryFactoryClassNotLoadedWhenAutoConfigurationCalledThenNoFactoryBean() {
        new ApplicationContextRunner()
                .withClassLoader(new FilteredClassLoader(JpaRepositoryFactoryBean.class))
                .withConfiguration(AutoConfigurations.of(
                        org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
                        org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
                        JpaQueryResourceMethodFactoryAutoConfiguration.class
                ))
                .run(context -> {
                    assertThrows(BeansException.class, () -> context.getBean(JpaQueryMethodFactory.class));
                });
    }

    @Test
    void givenEntityManagerFactoryBeanIsMissingWhenAutoConfigurationCalledThenNoFactoryBean() {
        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(
                        org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
                        JpaQueryResourceMethodFactoryAutoConfiguration.class
                ))
                .run(context -> {
                    assertThrows(BeansException.class, () -> context.getBean(JpaQueryMethodFactory.class));
                });
    }
}
