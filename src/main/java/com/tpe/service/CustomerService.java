package com.tpe.service;

import com.tpe.domain.Customer;
import com.tpe.exception.ConflictException;
import com.tpe.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    //13-b-customer kaydetme
    public void saveCustomer(Customer customer) {

        boolean isExists=customerRepository.existsByEmail(customer.getEmail());
        if (isExists){
            //13-d-custom exception fırlat
            throw new ConflictException("Customer already exists by email:"+customer.getEmail());
        }

        customerRepository.save(customer);
    }

    //14-b
    public List<Customer> getAll() {
        return customerRepository.findAll();//"FROM Customer"
    }
}