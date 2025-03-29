package com.example.atelier.controllers;

import com.example.atelier.enums.Category;
import com.example.atelier.services.AuthorizationService;
import com.example.atelier.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/worker")
public class WorkerController {
    private final OrderService orderService;

    private final AuthorizationService authorizationService;

    @Autowired
    public WorkerController(OrderService orderService, AuthorizationService authorizationService) {
        this.orderService = orderService;
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public String index() {
        return "worker/index";
    }

    @GetMapping("/orders/{type}")
    public String seeOrders(@PathVariable("type") String type, Model model) {
        switch (type) {
            case "restoration" -> model.addAttribute("orders", orderService.findAllNotDoneOrdersByCategory(Category.RESTORATION));
            case "sewing" -> model.addAttribute("orders", orderService.findAllNotDoneOrdersByCategory(Category.SEWING));
        }
        return "worker/orders";
    }

    @PutMapping("/orders/{id}")
    public String completeOrder(@PathVariable("id") Long id) {
        orderService.completeOrder(id);
        return "redirect:/worker";
    }

    @GetMapping("/orders/done-by-worker")
    public String showWorkerOrders(Model model) {
        model.addAttribute("orders", orderService.findAllOrdersDoneByWorker());
        return "worker/done_orders";
    }
}
