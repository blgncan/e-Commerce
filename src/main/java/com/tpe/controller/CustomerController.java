package com.tpe.controller;

import com.tpe.domain.Customer;
import com.tpe.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;//constructor injection:autowired zorunlu değil


    //13-a-customer kaydetme:http://localhost:8080/customers/save + POST + body
    //email daha önce kullanılmışsa hata fırlatır.(ConflictException)

    @PostMapping("/save")
    public ResponseEntity<String> createCustomer(@Valid @RequestBody Customer customer){

        customerService.saveCustomer(customer);

        return new ResponseEntity<>("Customer is saved successfully", HttpStatus.CREATED);//201
    }

    //14-a-Tüm customerları getirme -> http://localhost:8080/customers + GET
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customerList=customerService.getAll();
        // return new ResponseEntity<>(customerList,HttpStatus.OK);//200
        return ResponseEntity.ok(customerList);//200
    }




}