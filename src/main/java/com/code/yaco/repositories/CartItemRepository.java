package com.code.yaco.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.code.yaco.models.CartItem;
import com.code.yaco.models.User;

public interface CartItemRepository extends JpaRepository<CartItem,Long>{
    List<CartItem> findByUser(User user);
}
