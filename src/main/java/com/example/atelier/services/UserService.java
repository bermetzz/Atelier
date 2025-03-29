package com.example.atelier.services;

import com.example.atelier.entities.User;
import com.example.atelier.enums.Role;
import com.example.atelier.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAllClients() {
        return userRepository.findAllByRole(Role.ROLE_CLIENT);
    }

    public List<User> findAllWorkers() {
        return userRepository.findAllByRoleIn(Arrays.asList(Role.ROLE_WORKER, Role.ROLE_MANAGER));
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with this " + id + " not found"));
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
