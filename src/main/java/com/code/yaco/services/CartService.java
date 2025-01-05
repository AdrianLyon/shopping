package com.code.yaco.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.code.yaco.dto.CartItemDTO;
import com.code.yaco.dto.ProductDTO;
import com.code.yaco.models.CartItem;
import com.code.yaco.models.Product;
import com.code.yaco.models.User;
import com.code.yaco.repositories.CartItemRepository;

@Service
public class CartService {
    private final CartItemRepository cartItemRepository;

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItemDTO> getCartItems(User user) {
        return cartItemRepository.findByUser(user)
                .stream()
                .map(cartItem -> {
                    CartItemDTO dto = new CartItemDTO();
                    dto.setId(cartItem.getId());

                    ProductDTO productDTO = new ProductDTO();
                    Product product = cartItem.getProduct();
                    productDTO.setId(product.getId());
                    productDTO.setName(product.getName());
                    productDTO.setDescription(product.getDescription());
                    productDTO.setPrice(product.getPrice());

                    dto.setProduct(productDTO);
                    dto.setQuantity(cartItem.getQuantity());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void addToCart(User user, Product product, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }    
}
