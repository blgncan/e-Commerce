package com.tpe.repository;

import com.tpe.domain.Customer;
import com.tpe.dto.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository//optional
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    //13-c
    boolean existsByEmail(String email);//Spring tarafından derleme aşamasında implemente edilir.

    Optional<List<Customer> >findByName(String name);
}
