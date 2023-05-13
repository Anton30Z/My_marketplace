package com.example.my_marketplace.repositories;

import com.example.my_marketplace.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    com.example.my_marketplace.models.Category findByName(String name);
}
