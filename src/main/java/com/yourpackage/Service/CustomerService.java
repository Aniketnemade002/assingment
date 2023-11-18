package com.yourpackage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;

import com.yourpackage.model.Customer;

@Service
public class CustomerService {
    private final AuthService authService;
    private final RestTemplate restTemplate;

    @Autowired
    public CustomerService(AuthService authService, RestTemplate restTemplate) {
        this.authService = authService;
        this.restTemplate = restTemplate;
    }

    public void createCustomer(Customer customer) {
       
        String createCustomerUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=create";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authService.getBearerToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Customer> entity = new HttpEntity<>(customer, headers);

        try {
            restTemplate.postForObject(createCustomerUrl, entity, String.class);
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Failed to create a customer. " + ex.getResponseBodyAsString(), ex);
        }
    }

    public Customer getCustomerByUUID(String uuid) {
      
        String getCustomerUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer&uuid=" + uuid;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authService.getBearerToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            return restTemplate.getForObject(getCustomerUrl, Customer.class);
        } catch (HttpClientErrorException ex) {
            
            throw new RuntimeException("Failed to retrieve the customer. " + ex.getResponseBodyAsString(), ex);
        }
    }

    public void updateCustomer(Customer customer) {
      
        String updateCustomerUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=update";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authService.getBearerToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Customer> entity = new HttpEntity<>(customer, headers);

        try {
            restTemplate.postForObject(updateCustomerUrl, entity, String.class);
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Failed to update the customer. " + ex.getResponseBodyAsString(), ex);
        }
    }

    public void deleteCustomer(String uuid) {
        // Implement logic to delete a specific customer by UUID
        String deleteCustomerUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=delete&uuid=" + uuid;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authService.getBearerToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            restTemplate.delete(deleteCustomerUrl, entity);
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Failed to delete the customer. " + ex.getResponseBodyAsString(), ex);
        }
    }
}
