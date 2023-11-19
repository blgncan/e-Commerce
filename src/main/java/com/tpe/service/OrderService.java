package com.tpe.service;

import com.tpe.domain.Customer;
import com.tpe.domain.OrderItem;
import com.tpe.domain.Product;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderRepository orderRepository;

    public void saveOrders(Long cID, Long pID, Integer quantity) {

        Customer customer1 = customerService.getCustomerById(cID);
        Product product1 = productService.getProductById(pID);

        OrderItem newOrder = new OrderItem();
        newOrder.setCustomer(customer1);
        newOrder.setProduct(product1);
        newOrder.setQuantity(quantity);

        orderRepository.save(newOrder);
    }

    public List<OrderItem> getAll() {
        return orderRepository.findAll();
    }

    public OrderItem getOrderById(Long id) {
        OrderItem orderItem = orderRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Order is not found by id: " + id));

        return orderItem;

    }

//  public void updateOrdersByID(Long id, Long prtId, Long cstID, OrderItem orderItem) {//test
//      OrderItem foundOrder = getOrderById(id);
//      saveOrders(orderItem, prtId, cstID);

//  }

    public void deleteOrderById(Long id) {
        getOrderById(id);
        orderRepository.deleteById(id);
    }
}

