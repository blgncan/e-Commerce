package com.tpe.repository;

import com.tpe.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository//optional
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    //13-c
    boolean existsByEmail(String email);//Spring tarafından derleme aşamasında implemente edilir.
}
