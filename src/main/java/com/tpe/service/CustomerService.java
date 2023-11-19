package com.tpe.service;

import com.tpe.domain.Customer;
import com.tpe.dto.CustomerDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    //20-b
    public void updateCustomerById(Long id, CustomerDTO customerDTO) {//aaa@mail.com

        Customer foundCustomer = getCustomerById(id);

        boolean isExist = customerRepository.existsByEmail(customerDTO.getEmail());

        if (isExist && !customerDTO.getEmail().equals(foundCustomer.getEmail())) {
            throw new ConflictException("Customer already exists by email:" + customerDTO.getEmail());
        }

        foundCustomer.setName(customerDTO.getName());
        foundCustomer.setLastName(customerDTO.getLastName());
        foundCustomer.setPhone(customerDTO.getPhone());
        foundCustomer.setEmail(customerDTO.getEmail());//tabloda bir customer email:aaa@mail.com

        customerRepository.save(foundCustomer);//persist veya merge yapar-save veya update aynı yerde-parennteb geliyor


    }

    //21-b
    public Page<Customer> getAllCustomerByPage(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }
    //22-b

    public List<Customer> getCustomerByName(String name) {
        List<Customer> findCustomers = customerRepository.findByName(name).
                orElseThrow(() -> new ResourceNotFoundException("Customer is not found by id: " + name));
        return findCustomers;

    }

    public List<CustomerDTO> getCustomerDTOByName(String name) {

        List<Customer> foundCustomer = getCustomerByName(name);

        List<CustomerDTO> customerDTOList = new ArrayList<>();

        for (Customer customer : foundCustomer) {
            CustomerDTO customerDTO = new CustomerDTO(customer);
            customerDTOList.add(customerDTO);
        }

        return customerDTOList;

    }
}