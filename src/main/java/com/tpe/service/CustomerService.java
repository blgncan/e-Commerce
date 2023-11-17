package com.tpe.service;

import com.tpe.domain.Customer;
import com.tpe.dto.CustomerDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
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

        boolean isExists = customerRepository.existsByEmail(customer.getEmail());
        if (isExists) {
            //13-d-custom exception fırlat
            throw new ConflictException("Customer already exists by email:" + customer.getEmail());
        }

        customerRepository.save(customer);
    }

    //14-b
    public List<Customer> getAll() {
        return customerRepository.findAll();//"FROM Customer"
    }


    //15-d-Id ile customer getirme
    public Customer getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).//SELECT * FROM Customer WHERE..
                orElseThrow(() -> new ResourceNotFoundException("Customer is not found by id: " + id));
        return customer;
    }


    //15-c-id ile Customer DTO getirme
    public CustomerDTO getCustomerDTO(Long identity) {
        Customer foundCustomer = getCustomerById(identity);

        CustomerDTO customerDTO = new CustomerDTO(foundCustomer);  //customer-->customerDTO

        return customerDTO;
    }

    //16-b
    public void deleteCustomerById(Long id) {
        getCustomerById(id);//customer yoksa custom exception fırlatılır.
        customerRepository.deleteById(id);//DELETE FROM ...WHERE
    }
}