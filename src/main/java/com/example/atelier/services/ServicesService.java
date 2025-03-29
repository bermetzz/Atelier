package com.example.atelier.services;

import com.example.atelier.entities.Order;
import com.example.atelier.entities.Services;
import com.example.atelier.enums.Category;
import com.example.atelier.repositories.ServicesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ServicesService {
    private final ServicesRepository servicesRepository;

    @Autowired
    public ServicesService(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    public Services findById(Long id) {
        return servicesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Services with id " + id + " not found"));
    }

    public List<Services> findAll() {
        return servicesRepository.findAll();
    }

    @Transactional
    public void save(Services services) {
        servicesRepository.save(services);
    }

    @Transactional
    public void deleteById(Long id) {
            servicesRepository.deleteById(id);
    }

    public List<Services> findAllSewingServices() {
        return servicesRepository.findAllByCategory(Category.SEWING);
    }

    public List<Services> findAllRestorationServices() {
        return servicesRepository.findAllByCategory(Category.RESTORATION);
    }
}
