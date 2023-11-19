package com.tpe.controller;

import com.tpe.domain.Customer;
import com.tpe.dto.CustomerDTO;
import com.tpe.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")//http://localhost:8080/customers
@RequiredArgsConstructor//tüm metodları alan const.-fieldlar için
public class CustomerController {

    private final CustomerService customerService;//constructor injection:autowired zorunlu değil*@RequiredArgsConstructor

    //13-a-customer kaydetme:http://localhost:8080/customers/save + POST + body
    //email daha önce kullanılmışsa hata fırlatır.(ConflictException)

    @PostMapping("/save")//obje+json
    public ResponseEntity<String> createCustomer(@Valid @RequestBody Customer customer) {

        customerService.saveCustomer(customer);

        return new ResponseEntity<>("Customer is saved successfully", HttpStatus.CREATED);//201
    }

    //14-a-Tüm customerları getirme -> http://localhost:8080/customers + GET
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customerList = customerService.getAll();
        // return new ResponseEntity<>(customerList,HttpStatus.OK);//200
        return ResponseEntity.ok(customerList);//200
    }

    //15-a-Id ile tek bir customer getirme -> http://localhost:8080/customers/1 + GET
    //id tabloda yoksa hata fırlatır.(ResourceNotFoundException)
    //15-b-CustomerDTO tanımlama
    @GetMapping("/{id}")//tek bir tane gelirse
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id) {

        CustomerDTO customerDTO = customerService.getCustomerDTO(id);

        return ResponseEntity.ok(customerDTO);//200
    }

    //16-a-id ile customer silme http://localhost:8080/customers/custom?id=1 + DELETE
    @DeleteMapping("/custom")//birden fazla gelirse/?=queryparam
    public ResponseEntity<String> deleteCustomer(@RequestParam("id") Long id) {

        customerService.deleteCustomerById(id);

        return ResponseEntity.ok("Customer is deleted successfully..");//200
    }

    //20-a)id ile customer ı update etme -> http://localhost:8080/customers/update/1 + PUT + JSON Body
    ////Customer is updated successfully mesajı dönsün.
    ////emaili update ederken yeni değer tabloda var ve kendi maili değilse hata fırlatır. (ConflictException)
    @PutMapping("/update/{id}") //tüm entity update edilecekse
    public ResponseEntity<String> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {

        customerService.updateCustomerById(id, customerDTO);

        return ResponseEntity.ok("Customer is updated successfully...");
    }

    //21-a)tüm customerları page page gösterme ->
    // http://localhost:8080/customers/page?sayfa=1
    //                                      &size=2
    //                                      &sort=id
    //                                      &direction=ASC  + GET

    @GetMapping("/page")
    public ResponseEntity<Page<Customer>> getAllByPage(@RequestParam("page") int page,//kaçıncı sayfa
                                                       @RequestParam("size") int size,//her sayfada kaç bilgi ar
                                                       @RequestParam("sort") String prop,//hangi değişken
                                                       @RequestParam("direction") Sort.Direction direction) {//değişken şekli
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<Customer> allCustomer = customerService.getAllCustomerByPage(pageable);
        return ResponseEntity.ok(allCustomer);
    }


  //22-Name ile customer getirme -> http://localhost:8080/customers/query?name=Jack + GET
// @GetMapping("/query")
// public ResponseEntity<List<Customer>> getCostumerByName(@RequestParam("name") String name) {

//     List<Customer> customer = customerService.getCustomerByName(name);

//     return ResponseEntity.ok(customer);
// }
//   //22/1-Name ile customer getirme -> http://localhost:8080/customers/query?name=Jack + GET
  @GetMapping("/query/dto")//!!!!!
  public ResponseEntity<List<CustomerDTO>> getCostumerByNameDTO (@RequestParam("name")  String name){

      List<CustomerDTO> customerDTO=customerService.getCustomerDTOByName(name);

      return ResponseEntity.ok(customerDTO);
  }

    //23-fullname ile customer getirme-> http://localhost:8080/customers/fullquery?
    //name=Jack&lastName=Sparrow

}