package com.example.atelier.services;

import com.example.atelier.entities.Budget;
import com.example.atelier.entities.Order;
import com.example.atelier.enums.Category;
import com.example.atelier.repositories.BudgetRepository;
import com.example.atelier.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    private final AuthorizationService authorizationService;

    private final BudgetRepository budgetRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, AuthorizationService authorizationService, BudgetRepository budgetRepository) {
        this.orderRepository = orderRepository;
        this.authorizationService = authorizationService;
        this.budgetRepository = budgetRepository;
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order with id " + id + " not found"));
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findAllCompletedOrders() {
        return orderRepository.findAllByStatusTrue();
    }

    public List<Order> findAllNotDoneOrders() {
        return orderRepository.findAll().stream().filter(order -> Objects.equals(order.getUser().getId(), authorizationService.getCurrentUser().getId())).toList();
    }

    public List<Order> findAllDoneOrders() {
        return orderRepository.findAllDoneOrders(authorizationService.getCurrentUser().getId());
    }

    public List<Order> findAllNotDoneOrdersByCategory(Category category) {
        return orderRepository.findAll().stream().filter(order -> order.getServices().getCategory().equals(category)).toList();
    }

    @Transactional
    public void saveSewing(Order order) {
        order.setOrderDate(LocalDate.now());
        order.setUser(authorizationService.getCurrentUser());
        if(order.getServices().getCategory()==Category.RESTORATION){
            order.setCost(order.getServices().getCost());
        }
        else {order.setCost(order.getMaterial().getCost() + order.getServices().getCost());
        }
        orderRepository.save(order);
    }

    public double sewingPercent() {
        double doneOrders = findAllCompletedOrders().stream().filter(order -> order.getServices().getCategory().equals(Category.SEWING)).count();
        System.out.println(doneOrders);
        return doneOrders / (findAll().size() + doneOrders) * 100 ;
    }

    public double restorationPercent() {
        double doneOrders = findAllCompletedOrders().stream().filter(order -> order.getServices().getCategory().equals(Category.RESTORATION)).count();
        return doneOrders / (findAll().size() + doneOrders) * 100;
    }

    @Transactional
    public void completeOrder(Long id) {
        Order order = findById(id);
        if (order.getServices().getCategory()==Category.SEWING){
            order.getMaterial().setQuantity(order.getMaterial().getQuantity() - order.getSize().getLength());
            Optional<Budget> budget = budgetRepository.findAll().stream().findFirst();
            budget.ifPresent(value -> value.setBudget(value.getBudget() + order.getCost()));
            order.setEndDate(LocalDate.now());
            order.setStatus(true);
        } else if (order.getServices().getCategory()==Category.RESTORATION) {
            Optional<Budget> budget = budgetRepository.findAll().stream().findFirst();
            budget.ifPresent(value -> value.setBudget(value.getBudget() + order.getCost()));
            order.setEndDate(LocalDate.now());
            order.setStatus(true);
        }
        order.setWorker(authorizationService.getCurrentUser());
        orderRepository.save(order);
    }

    public List<Order> findAllOrdersDoneByWorker() {
        return orderRepository.findAllOrdersDoneByWorker(authorizationService.getCurrentUser().getId());
    }
}
