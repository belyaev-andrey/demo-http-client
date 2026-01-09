package com.jetbrains.test.boot4.server.security;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("""
            SELECT r.name
            FROM role r
            INNER JOIN user_role ur ON r.id = ur.role_id
            INNER JOIN app_user u ON u.id = ur.user_id
            WHERE u.username = :username
            """)
    List<String> findRolesByUsername(@Param("username") String username);
}
