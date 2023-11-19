package com.tpe.controller;

import com.tpe.domain.OrderItem;
import com.tpe.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private OrderService orderService;

    //http://localhost:8080/orders/save/filter?cid=1&prod=1&quant=3
    @PostMapping("/save/filter")
    public ResponseEntity<String> createOrder(@Valid @RequestParam Long cid, Long prod,Integer quant){

        orderService.saveOrders(cid,prod,quant);

        return new ResponseEntity<>("Orders are saves succesfully",HttpStatus.CREATED);//201
    }

    //2-tüm siparişleri getirme ->http://localhost:8080/orders
    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrders(){
        List<OrderItem> orderItemList= orderService.getAll();

        return ResponseEntity.ok(orderItemList);//200
    }

    //3-Id ile sipariş getirme ->http://localhost:8080/orders/5

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrder(@PathVariable Long id){
        OrderItem orderItem=orderService.getOrderById(id);

        return ResponseEntity.ok(orderItem);
    }

    //4-Id ile sipariş miktarını update etme ->http://localhost:8080/orders/update/5/quantity/30
    ////quantity=0 ise siparişi sil

    //  @PutMapping("update/{id}")
    //  public ResponseEntity<String> updateOrder(@PathVariable Long id, Long prtId,Long cstID,@RequestBody OrderItem orderItem){
    //      orderService.updateOrdersByID(id,prtId,cstID,orderItem);

    //      return ResponseEntity.ok("Orders is updated successfully...");
    //  }

//5-Id ile sipariş delete etme ->http://localhost:8080/orders/delete?id=5

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteOrder(@RequestParam("id") Long id){

        orderService.deleteOrderById(id);

        return ResponseEntity.ok("Order is deleted successfully..");//200
    }

























}
