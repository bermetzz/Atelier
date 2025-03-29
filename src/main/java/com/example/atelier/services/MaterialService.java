package com.example.atelier.services;

import com.example.atelier.entities.Material;
import com.example.atelier.repositories.MaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MaterialService {
    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public Material findById(Long id) {
        return materialRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Material with id " + id + " not found"));
    }

    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    @Transactional
    public void save(Material material) {
        materialRepository.save(material);
    }

    @Transactional
    public void deleteById(Long id) {
        materialRepository.deleteById(id);
    }
}
