package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.repository.CustomerRepository;
import com.cg.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Override
    public List<Customer> findALl() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findAllByDeletedIsFalse() {
        return customerRepository.findAllByDeletedIsFalse();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void incrementBalance(Long customerId, BigDecimal transactionAmount) {
        customerRepository.incrementBalance(customerId, transactionAmount);
    }

    @Override
    public Deposit deposit(Deposit deposit) {
        Customer customer = deposit.getCustomer();
//        BigDecimal currentBalance = customer.getBalance();
        BigDecimal transactionAmount = deposit.getTransactionAmount();
//        BigDecimal newBalance = currentBalance.add(transactionAmount);
//        customer.setBalance(newBalance);
        customerRepository.incrementBalance(customer.getId(), transactionAmount);

        deposit.setId(null);
        deposit.setCreatedAt(new Date());
        depositRepository.save(deposit);

        customer = customerRepository.findById(customer.getId()).get();
        deposit.setCustomer(customer);

        return deposit;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void delete(Customer customer) {

    }
}
