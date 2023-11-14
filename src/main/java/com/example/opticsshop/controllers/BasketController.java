package com.example.opticsshop.controllers;

import com.example.opticsshop.DAO.models.Basket;
import com.example.opticsshop.DAO.models.User;
import com.example.opticsshop.DAO.repositories.BasketRepository;
import com.example.opticsshop.services.BasketService;
import com.example.opticsshop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BasketController {
    @Autowired
    private final ProductService productService;

    @Autowired
    private final BasketService basketService;
    private final BasketRepository basketRepository;


    @PostMapping("/basket/add/{id}")
    public String addToBasket(@PathVariable Long id, Principal principal, Model model, HttpServletRequest request) {
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        basketService.addToBasket(id);
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/basket/remove/{id}")
    public String removeFromBasket(@PathVariable Long id) {
        basketService.removeFromBasket(id);
        return "redirect:/basket";
    }

    @GetMapping("/basket")
    public String getBasket(Principal principal, Model model) {
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        List<Basket> basketItems = basketService.getBasket();
        model.addAttribute("basket", basketItems);
        int allPrice = basketService.getCheckoutPrice();
        model.addAttribute("basketPrice", allPrice);
        return "basket";
    }

    @PostMapping("/basket/order")
    public String createOrder(Principal principal, Model model) {
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        List<Basket> basketItems = basketService.getBasket();
        model.addAttribute("basket", basketItems);
        basketRepository.deleteAll(basketItems);
        return "redirect:/order";
    }

    @GetMapping("/order")
    public String successOrder(Principal principal, Model model) {
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        return "order";
    }
}
