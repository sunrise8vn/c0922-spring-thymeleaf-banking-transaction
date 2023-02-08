package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.service.IGeneralService;

import java.math.BigDecimal;
import java.util.List;

public interface ICustomerService extends IGeneralService<Customer> {

    List<Customer> findAllByDeletedIsFalse();

    void incrementBalance(Long customerId, BigDecimal transactionAmount);

    Deposit deposit(Deposit deposit);
}
