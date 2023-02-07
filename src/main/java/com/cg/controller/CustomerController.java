package com.cg.controller;

import com.cg.model.Customer;
import com.cg.service.customer.ICustomerService;
import com.cg.service.deposit.IDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IDepositService depositService;


    @GetMapping
    public String showListPage(Model model) {

        List<Customer> customers = customerService.findAllByDeletedIsFalse();

        model.addAttribute("customers", customers);

        return "customer/list";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("customer", new Customer());

        return "customer/create";
    }

    @GetMapping("/update/{id}")
    public String showUpdatePage(@PathVariable Long id, Model model) {

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            model.addAttribute("error", true);
        }
        else {
            Customer customer = customerOptional.get();
            model.addAttribute("customer", customer);
        }

        return "customer/update";
    }

    @PostMapping("/create")
    public String doCreate(@ModelAttribute Customer customer) {

        customer.setBalance(BigDecimal.ZERO);
        customerService.save(customer);

        return "customer/create";
    }

    @PostMapping("/update/{id}")
    public String doUpdate(@PathVariable Long id, @ModelAttribute Customer customer, Model model) {

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            model.addAttribute("error", true);
        }
        else {
            customer.setId(id);
            customerService.save(customer);
            model.addAttribute("customer", customer);
        }

        return "customer/update";
    }
}
