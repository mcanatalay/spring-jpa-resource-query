# spring-jpa-resource-query

[![Build Status](https://app.travis-ci.com/mcanatalay/spring-jpa-resource-query.svg?branch=main)](https://app.travis-ci.com/mcanatalay/spring-jpa-resource-query) [![](https://jitpack.io/v/mcanatalay/spring-jpa-resource-query.svg)](https://jitpack.io/#mcanatalay/spring-jpa-resource-query)

When your SQL queries become huge, spring-jpa-resource-query helps you to load them from resources folder. It makes your code clean and you can work with well-formed SQL queries in your IDE.

## Install
```xml
<repositories>
	<repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml
<dependency>
    <groupId>com.github.mcanatalay</groupId>
    <artifactId>spring-jpa-resource-query</artifactId>
</dependency>
```

## Configuration
```java
@Configuration
public class JpaQueryConfig {
    @PersistenceContext
    EntityManager entityManager;

    @Bean
    JpaQueryMethodFactory queryMethodFactory() {
        return new JpaQueryResourceMethodFactory(entityManager);
    }
}
```

## Usage
```java
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "classpath:select_top_users.sql")
    List<User> findAll();

    @Query(value = "classpath:select_user_by_id.sql")
    User findById(int userId);

    @Query(value = "classpath:select_user_by_name.sql")
    User findByUsername(String username);
}
```
