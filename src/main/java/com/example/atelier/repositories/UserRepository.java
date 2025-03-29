package com.example.atelier.repositories;

import com.example.atelier.entities.User;
import com.example.atelier.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findAllByRole(Role role);

    List<User> findAllByRoleIn(List<Role> roles);
}
