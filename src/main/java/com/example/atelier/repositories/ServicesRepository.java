package com.example.atelier.repositories;

import com.example.atelier.entities.Services;
import com.example.atelier.enums.Category;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long>{
    List<Services> findAllByCategory(Category category);
}
