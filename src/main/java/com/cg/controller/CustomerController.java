package com.cg.controller;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.service.customer.ICustomerService;
import com.cg.service.deposit.IDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
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

    @GetMapping("/deposit/{id}")
    public String showDepositPage(@PathVariable Long id, Model model) {

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            model.addAttribute("error", true);
            model.addAttribute("messages", "Customer not found");
        }
        else {
            Customer customer = customerOptional.get();
//            model.addAttribute("customer", customer);

            Deposit deposit = new Deposit();
            deposit.setCustomer(customer);

            model.addAttribute("deposit", deposit);
        }

        return "customer/deposit";
    }

    @PostMapping("/create")
    public String doCreate(@ModelAttribute Customer customer, BindingResult bindingResult, Model model) {

        new Customer().validate(customer, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("customer", customer);
            model.addAttribute("error", true);
            return "customer/create";
        }

        customer.setBalance(BigDecimal.ZERO);
        customerService.save(customer);
        model.addAttribute("success", true);
        model.addAttribute("messages", "Create customer successful");

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

    @PostMapping("/deposit/{customerId}")
    public String doDeposit(@PathVariable Long customerId, @ModelAttribute Deposit deposit, Model model) {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            model.addAttribute("error", true);
        }
        else {
            Customer customer = customerOptional.get();

            deposit.setCustomer(customer);
            deposit = customerService.deposit(deposit);

            deposit.setTransactionAmount(BigDecimal.ZERO);

            model.addAttribute("deposit", deposit);
        }

        model.addAttribute("success", true);
        model.addAttribute("messages", "Deposit successful");

        return "customer/deposit";
    }
}
