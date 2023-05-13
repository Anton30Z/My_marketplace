package com.example.my_marketplace.services;

import com.example.my_marketplace.enumm.Status;
import com.example.my_marketplace.models.Order;
import com.example.my_marketplace.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


// Работа с заказами пользователей в ЛК Алминистратора
@Service
@Transactional (readOnly = true)
public class OrderService {
    public final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //Метод позволяет получить список всех заказов в базе данных
    public List<Order> getAllOrders(){

        return orderRepository.findAll();
    }

    // Метод позволяет найти заказ по 4 последним символам
    public List<Order> find4Last (String word){
        return orderRepository.findByNumberEndingWithIgnoreCase(word);
    }

    //Метод позволяет получить заказ по id
    public Order getOrderById(int id){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    // Метод позволяет обновить данные о статусе в заказе
    @Transactional
    public void updateOrder (int id, Order order) {
        order.setId(id);
        orderRepository.save(order);
    }


}
