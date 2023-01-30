package org.springframework.boot.autoconfigure.data.jpa;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.provider.QueryExtractor;
import org.springframework.data.jpa.repository.query.JpaQueryMethodFactory;
import org.springframework.data.jpa.repository.query.JpaQueryResourceMethodFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean({JpaQueryMethodFactory.class})
@ConditionalOnClass({EntityManager.class, JpaRepositoryFactoryBean.class})
@ConditionalOnBean({EntityManagerFactory.class})
@ConditionalOnProperty(
        prefix = "spring.data.jpa.repositories",
        name = {"enabled"},
        havingValue = "true",
        matchIfMissing = true
)
@AutoConfigureAfter({JpaRepositoriesAutoConfiguration.class})
public class JpaQueryResourceMethodFactoryAutoConfiguration {
    @PersistenceContext
    EntityManager entityManager;

    @Bean
    JpaQueryMethodFactory jpaQueryResourceMethodFactory() {
        QueryExtractor extractor = PersistenceProvider.fromEntityManager(entityManager);
        return new JpaQueryResourceMethodFactory(extractor);
    }
}