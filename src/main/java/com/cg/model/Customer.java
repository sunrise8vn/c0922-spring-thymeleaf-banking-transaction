package com.cg.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "customers")
public class Customer implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotEmpty(message = "Full name is required")
    @Column(name = "full_name", nullable = false)
    private String fullName;

//    @NotEmpty(message = "Email is required")
    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(precision = 10, scale = 0, nullable = false, updatable = false)
    private BigDecimal balance;

    private String address;


    @Column(columnDefinition = "boolean default false")
    private Boolean deleted = false;

    public Customer() {
    }

    public Customer(Long id, String fullName, String email, String phone, BigDecimal balance, String address, Boolean deleted) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.balance = balance;
        this.address = address;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customer customer = (Customer) target;

        String fullName = customer.getFullName();
        String email = customer.getEmail();

        if (fullName.length() == 0) {
            errors.rejectValue("fullName", "fullName.null");
        }
        else {
            if (fullName.length() < 4 || fullName.length() > 25) {
                errors.rejectValue("fullName", "fullName.length");
            }
        }

        if (email.length() == 0) {
            errors.rejectValue("email", "email.null");
        }
        else {
            if (!email.matches("^[\\w]+@([\\w-]+\\.)+[\\w-]{2,6}$")) {
                errors.rejectValue("email", "email.matches");
            }
        }
    }
}
