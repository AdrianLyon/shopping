package com.code.yaco.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.code.yaco.dto.CartItemDTO;
import com.code.yaco.dto.ProductDTO;
import com.code.yaco.dto.UserDTO;
import com.code.yaco.models.Product;
import com.code.yaco.models.User;
import com.code.yaco.services.CartService;
import com.code.yaco.services.ProductService;
import com.code.yaco.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ProductController {
    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;

    public ProductController(ProductService productService, CartService cartService, UserService userService) {
        this.productService = productService;
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        List<ProductDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product_list";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, Principal principal) {
        // Obtenemos el UserDTO
        var userDTO = userService.findByUsername(principal.getName());
        if (userDTO == null) {
            throw new IllegalStateException("El usuario no existe");
        }

        // Convertimos UserDTO a User
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());

        // Obtenemos el ProductDTO
        var productDTO = productService.getAllProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElse(null);
        if (productDTO == null) {
            throw new IllegalStateException("Producto no encontrado");
        }

        // Convertimos ProductDTO a Product
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        // Llamamos al servicio para agregar al carrito
        cartService.addToCart(user, product, quantity);

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model, Principal principal) {
        String username = principal.getName();

        UserDTO userDTO = userService.findByUsername(username);
        if (userDTO == null) {
            throw new IllegalStateException("El usuario no existe");
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());

        List<CartItemDTO> cartItems = cartService.getCartItems(user);
        model.addAttribute("cartItems", cartItems);

        return "cart_view";
    }

}
