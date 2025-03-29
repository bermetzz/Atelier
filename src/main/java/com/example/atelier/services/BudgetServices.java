package com.example.atelier.services;

import com.example.atelier.entities.Budget;
import com.example.atelier.repositories.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BudgetServices {
    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetServices(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public Optional<Budget> findBudget() {
        return budgetRepository.findAll().stream().findAny();
    }
}
