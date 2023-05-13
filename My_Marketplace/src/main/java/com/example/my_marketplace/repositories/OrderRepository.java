package com.example.my_marketplace.repositories;

import com.example.my_marketplace.models.Order;
import com.example.my_marketplace.models.Person;
import com.example.my_marketplace.models.Product;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByPerson(Person person);

    // Поиск по 4 последним символам в номере заказа
    @Query(value = "select * from orders where (number LIKE %?1)", nativeQuery = true)
    List<Order> findByNumberEndingWithIgnoreCase (String name);

    // Поиск заказа по id
    //List<Order> findById(int id);

}

