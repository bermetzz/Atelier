package com.example.atelier.controllers;

import com.example.atelier.entities.Budget;
import com.example.atelier.entities.User;
import com.example.atelier.enums.Role;
import com.example.atelier.services.BudgetServices;
import com.example.atelier.services.OrderService;
import com.example.atelier.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/director")
public class DirectorController {
    private final UserService userService;

    private final OrderService orderService;

    private final BudgetServices budgetServices;

    @Autowired
    public DirectorController(UserService userService, OrderService orderService, BudgetServices budgetServices) {
        this.userService = userService;
        this.orderService = orderService;
        this.budgetServices = budgetServices;
    }

    @GetMapping
    public String index() {
        return "director/index";
    }

    @GetMapping("/all-clients")
    public String showAllClients(Model model) {
        model.addAttribute("clients", userService.findAllClients());
        return "director/clients";
    }

    @GetMapping("/all-workers")
    public String showAllWorkers(Model model) {
        model.addAttribute("workers", userService.findAllWorkers());
        return "director/workers";
    }

    @GetMapping("/user/{id}")
    public String showUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "director/detail_user";
    }

    @GetMapping("/order-percent/{type}")
    public String showPercentOrder(@PathVariable("type") String type, Model model) {
        switch (type) {
            case "sewing" -> model.addAttribute("percent", orderService.sewingPercent());
            case "restoration" -> model.addAttribute("percent", orderService.restorationPercent());
            default -> throw new RuntimeException("Does not exist this " + type + " type");
        }
        return "director/order_percent";
    }

    @GetMapping("/user/register")
    public String formToRegisterUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", List.of(Role.ROLE_MANAGER, Role.ROLE_WORKER));
        return "director/user_register";
    }

    @GetMapping("/budget")
    public String showBudget(Model model) {
        Optional<Budget> budget = budgetServices.findBudget();
        if (budget.isPresent()) {
            model.addAttribute("budget", budget.get());
        } else {
            throw new RuntimeException("Budget is empty");
        }
        return "director/budget";
    }

    @DeleteMapping("/delete/user/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/director";
    }
}
