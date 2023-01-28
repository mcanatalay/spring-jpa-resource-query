# spring-jpa-resource-query

When your SQL queries become huge, spring-jpa-resource-query helps you to load them from resources folder. It makes your code clean and you can work with well-formed SQL queries in your IDE.

## Install

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
