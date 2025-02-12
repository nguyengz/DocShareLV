package com.example.demo.repository;

import com.example.demo.model.Users;
import com.google.api.services.drive.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String name); // Tim kiem User co ton tai trong DB khong?

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM Users u WHERE u.email = ?1")
    public Users findByEmail(String email);

    @Query("SELECT u FROM Users u WHERE u.verificationCode = ?1")
    public Users findByVerificationCode(String code);

// @Query("SELECT u.id FROM Users u INNER JOIN user_friends uf ON u.id = uf.user_id WHERE uf.friend_id = :user_id")
// public List<Object[]> following(@Param("user_id") Long user_id);
    long count();
}
