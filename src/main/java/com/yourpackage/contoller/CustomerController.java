package com.yourpackage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;

import com.yourpackage.model.Customer;
import com.yourpackage.service.AuthService;

@Controller
public class CustomerController {
    private final AuthService authService;
    private final RestTemplate restTemplate;

    @Autowired
    public CustomerController(AuthService authService, RestTemplate restTemplate) {
        this.authService = authService;
        this.restTemplate = restTemplate;
    }

    // ...

    @PostMapping("/create-customer")
    public String createCustomer(Model model, @ModelAttribute Customer customer) {
        String createCustomerUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=create";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authService.getBearerToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Customer> entity = new HttpEntity<>(customer, headers);

        try {
            restTemplate.postForObject(createCustomerUrl, entity, String.class);
            return "redirect:/customer-list"; 
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create a customer. Please check the input data.");
            return "customer-list";
        }
    }

    @GetMapping("/update-customer/{uuid}")
    public String showUpdateForm(Model model, @PathVariable String uuid) {
      
        String updateCustomerUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer&uuid=" + uuid;
       
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authService.getBearerToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            Customer customer = restTemplate.getForObject(updateCustomerUrl, Customer.class);
            model.addAttribute("customer", customer);
            return "update-customer"; 
        } catch (Exception e) {
            model.addAttribute("error", "Customer not found.");
            return "customer-list";
        }
    }

    @PostMapping("/update-customer")
    public String updateCustomer(Model model, @ModelAttribute Customer customer) {
        
        String updateCustomerUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=update";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authService.getBearerToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Customer> entity = new HttpEntity<>(customer, headers);

        try {
            restTemplate.postForObject(updateCustomerUrl, entity, String.class);
            return "redirect:/customer-list"; 
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update the customer. Please check the input data.");
            return "customer-list";
        }
    }

    @GetMapping("/delete-customer/{uuid}")
    public String deleteCustomer(Model model, @PathVariable String uuid) {
        
        String deleteCustomerUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=delete&uuid=" + uuid;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authService.getBearerToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            restTemplate.exchange(deleteCustomerUrl, HttpMethod.DELETE, entity, String.class);
            return "redirect:/customer-list"; 
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete the customer. Please try again.");
            return "customer-list";
        }
    }
}
