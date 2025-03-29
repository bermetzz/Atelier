package com.example.atelier.controllers;

import com.example.atelier.entities.Order;
import com.example.atelier.enums.Size;
import com.example.atelier.services.AuthorizationService;
import com.example.atelier.services.MaterialService;
import com.example.atelier.services.OrderService;
import com.example.atelier.services.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/client")
public class ClientController {
    private final ServicesService servicesService;

    private final OrderService orderService;

    private final MaterialService materialService;

    @Autowired
    public ClientController(ServicesService servicesService, OrderService orderService, MaterialService materialService) {
        this.servicesService = servicesService;
        this.orderService = orderService;
        this.materialService = materialService;
    }

    @GetMapping
    public String index() {
        return "client/index";
    }

    @GetMapping("/order/{type}")
    public String showFormToOrder(@ModelAttribute("order") Order order,
                                  @PathVariable("type") String type, Model model) {
        switch (type) {
            case "sewing" -> model
                    .addAttribute("sewing", servicesService.findAllSewingServices())
                    .addAttribute("sizes", Size.values());
            case "restoration" -> model.addAttribute("restoration", servicesService.findAllRestorationServices());
            default -> throw new RuntimeException("Does not exist this type");
        }
        model.addAttribute("materials", materialService.findAll());

        return "client/create_order";
    }

    @PostMapping("/order")
    public String makeOrder(@ModelAttribute("order") Order order) {
        orderService.saveSewing(order);
        return "redirect:/client";
    }

    @GetMapping("/orders/not-done")
    public String showNotDoneOrders(Model model) {
        model.addAttribute("orders", orderService.findAllNotDoneOrders());
        return "client/not_done_orders";
    }

    @GetMapping("/orders/done")
    public String showDoneOrders(Model model) {
        model.addAttribute("orders", orderService.findAllDoneOrders());
        return "client/done_orders";
    }
}
