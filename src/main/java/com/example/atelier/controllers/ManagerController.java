package com.example.atelier.controllers;

import com.example.atelier.entities.Material;
import com.example.atelier.entities.Services;
import com.example.atelier.enums.Category;
import com.example.atelier.services.MaterialService;
import com.example.atelier.services.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager")
public class ManagerController {
    private final MaterialService materialService;

    private final ServicesService servicesService;

    @Autowired
    public ManagerController(MaterialService materialService, ServicesService servicesService) {
        this.materialService = materialService;
        this.servicesService = servicesService;
    }

    @GetMapping
    public String index() {
        return "manager/index";
    }

    @GetMapping("/services")
    public String showAllServices(Model model) {
        model.addAttribute("services", servicesService.findAll());
        return "manager/services/services";
    }

    @GetMapping("/service/{id}")
    public String showServiceById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("service", servicesService.findById(id));
        return "manager/services/detail_service";
    }

    @GetMapping("/add-service")
    public String formToAddService(@ModelAttribute("service") Services service, Model model) {
        model.addAttribute("categories", Category.values());
        return "manager/services/create_service";
    }

    @PostMapping("/add-service")
    public String addMaterial(@ModelAttribute("service") Services service) {
        servicesService.save(service);
        return "redirect:/manager/services";
    }

    @DeleteMapping("/service/{id}")
    public String deleteService(@PathVariable("id") Long id) {
        servicesService.deleteById(id);
        return "redirect:/manager/services";
    }

    @GetMapping("/materials")
    public String showAllMaterials(Model model) {
        model.addAttribute("materials", materialService.findAll());
        return "manager/materials/materials";
    }

    @GetMapping("/material/{id}")
    public String showMaterialById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("material", materialService.findById(id));
        return "manager/materials/detail_material";
    }

    @GetMapping("/add-material")
    public String formToAddMaterial(@ModelAttribute("material") Material material) {
        return "manager/materials/create_material";
    }

    @PostMapping("/add-material")
    public String addMaterial(@ModelAttribute("material") Material material) {
        materialService.save(material);
        return "redirect:/manager/materials";
    }

    @DeleteMapping("/material/{id}")
    public String deleteMaterial(@PathVariable("id") Long id) {
        materialService.deleteById(id);
        return "redirect:/manager/materials";
    }
}
