package com.example.my_marketplace.controllers;

import com.example.my_marketplace.enumm.Status;
import com.example.my_marketplace.models.*;
import com.example.my_marketplace.repositories.CategoryRepository;
import com.example.my_marketplace.repositories.OrderRepository;
import com.example.my_marketplace.services.OrderService;
import com.example.my_marketplace.services.PersonDetailsService;
import com.example.my_marketplace.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class AdminController {

    private final ProductService productService;

    @Value("${upload.path}")
    private String uploadPath;

    private final CategoryRepository categoryRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final PersonDetailsService personDetailsService;

    public AdminController(ProductService productService, CategoryRepository categoryRepository, OrderService orderService, OrderRepository orderRepository, PersonDetailsService personDetailsService) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.personDetailsService = personDetailsService;
    }

    @GetMapping("admin/product/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "product/addProduct";
    }

    @PostMapping("/admin/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one")MultipartFile file_one, @RequestParam("file_two")MultipartFile file_two, @RequestParam("file_three")MultipartFile file_three, @RequestParam("file_four")MultipartFile file_four, @RequestParam("file_five")MultipartFile file_five, @RequestParam("category") int category, Model model) throws IOException {
        Category category_db = (Category) categoryRepository.findById(category).orElseThrow();
        System.out.println(category_db.getName());
        if(bindingResult.hasErrors()){
            model.addAttribute("category", categoryRepository.findAll());
            return "product/addProduct";
        }

        if(file_one != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
            file_one.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);

        }

        if(file_two != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_two.getOriginalFilename();
            file_two.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if(file_three != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_three.getOriginalFilename();
            file_three.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if(file_four != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_four.getOriginalFilename();
            file_four.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if(file_five != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_five .getOriginalFilename();
            file_five .transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        productService.saveProduct(product, category_db);
        return "redirect:/admin";
    }


    @GetMapping("/admin")
    public String admin(Model model)
    {
        model.addAttribute("products", productService.getAllProduct());
        return "admin";
    }

    @GetMapping("admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") int id){
        model.addAttribute("product", productService.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/editProduct";
    }

    @PostMapping("admin/product/edit/{id}")
    public String editProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @PathVariable("id") int id, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("category", categoryRepository.findAll());
            return "product/editProduct";
        }
        productService.updateProduct(id, product);
        return "redirect:/admin";
    }

    // ВЫВОД всех заказов пользователей
    @GetMapping("allOrders")
    public String getAllOrders (Model model){
        List<Order> ordersA = orderService.getAllOrders();
        model.addAttribute("ordersAll", ordersA);
        return "allOrders";
    }

    // ПОИСК заказов по номеру
    @PostMapping("admin/allOrders/search")
    public String orderSearch (@RequestParam("search") String search, Model model){
        model.addAttribute("orderAll", orderService.getAllOrders());
        model.addAttribute("search_order", orderService.find4Last(search));
        model.addAttribute("value_search", search);
        return "allOrders";
    }

    // ИЗМЕНЕНИЕ статуса заказа
    @PostMapping("admin/allOrders/{id}")
    public String changeOrderStatus (@ModelAttribute("status") Status status, @PathVariable("id") int id) {
        Order order = orderService.getOrderById(id); //получаем объект заказа из БД
        order.setStatus(status); //меняем статус на выбранный в селекте
        orderService.updateOrder(id, order); //обновляем данные заказа в БД
        return "redirect:/allOrders";
    }

    // Вывод всех пользователей
    @GetMapping("allUsers")
    public String showAllUsers (Model model){
        model.addAttribute("usersAll", personDetailsService.getAllUsers());
        return "allUsers";
    }

    // Изменение роли пользователя
    @PostMapping("admin/allUsers/{id}")
    public String changeRole (@ModelAttribute("roleStatus") String person, @PathVariable("id") int id) {
        Person per = personDetailsService.findByID(id); //получаем пользователя из БД
        personDetailsService.updateUserRole(person, per); //обновляем данные пользователя в БД
        return "redirect:/allUsers";
    }



}