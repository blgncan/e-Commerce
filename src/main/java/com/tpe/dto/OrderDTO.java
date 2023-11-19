package com.tpe.dto;

import com.tpe.domain.Customer;
import com.tpe.domain.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {//gerek yok


    private Long id;

    @NotNull(message = "Quantity can not be null")
    private Integer quantity;

    @NotNull(message = "Total price can not be null")
    @Setter(AccessLevel.NONE)
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull(message = "Product can not be null")
    private Product product;

    @ManyToOne//fk-setleme burda olcak.
    @JoinColumn(nullable = false)
    @NotNull(message = "Customer can not be null")
    //TODO: json
    private Customer customer;

    @PrePersist
    @PreUpdate
    public void countTotalPrice(){

        this.totalPrice=this.product.getPrice()*this.quantity;
    }
}
