package com.willem.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.willem.demo.model.CustomerDto;
import com.willem.demo.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author willem
 */

@WebMvcTest(CustomerController.class)
class CustomerControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CustomerService customerService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testSaveCustomer() throws Exception
    {
        CustomerDto customerDto = CustomerDto.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .build();

        // Mock the service method to return the customerDto
        when(customerService.saveCustomer(any(CustomerDto.class))).thenReturn(customerDto);

        // Perform the POST request and check the response
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(customerService, times(1)).saveCustomer(any(CustomerDto.class));
    }

    @Test
    void testDeleteCustomer() throws Exception
    {
        Long customerId = 1L;

        // Mock the service method to return true when deleting
        when(customerService.deleteCustomer(customerId)).thenReturn(true);

        // Perform the DELETE request and check the response
        mockMvc.perform(delete("/api/customers/{id}", customerId))
                .andExpect(status().isNoContent());

        verify(customerService, times(1)).deleteCustomer(customerId);
    }

    @Test
    void testDeleteCustomerNotFound() throws Exception
    {
        Long customerId = 999L;

        // Mock the service method to return false (customer not found)
        when(customerService.deleteCustomer(customerId)).thenReturn(false);

        // Perform the DELETE request and check the response
        mockMvc.perform(delete("/api/customers/{id}", customerId))
                .andExpect(status().isNotFound());

        verify(customerService, times(1)).deleteCustomer(customerId);
    }

    @Test
    void testGetAllCustomers() throws Exception
    {
        CustomerDto customerDto1 = CustomerDto.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .build();

        CustomerDto customerDto2 = CustomerDto.builder()
                .id(2L)
                .name("Jane")
                .surname("Doe")
                .email("jane.doe@example.com")
                .build();

        // Mock the service method to return a list of customers
        when(customerService.findAllCustomers()).thenReturn(List.of(customerDto1, customerDto2));

        // Perform the GET request and check the response
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].name").value("Jane"));

        verify(customerService, times(1)).findAllCustomers();
    }

    @Test
    void testGetCustomerById() throws Exception
    {
        Long customerId = 1L;
        CustomerDto customerDto = CustomerDto.builder()
                .id(customerId)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .build();

        when(customerService.findCustomerById(customerId)).thenReturn(Optional.of(customerDto));

        // Perform the GET request and check the response
        mockMvc.perform(get("/api/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(customerService, times(1)).findCustomerById(customerId);
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception
    {
        Long customerId = 999L;

        // Mock the service method to return an empty Optional (customer not found)
        when(customerService.findCustomerById(customerId)).thenReturn(Optional.empty());

        // Perform the GET request and check the response
        mockMvc.perform(get("/api/customers/{id}", customerId))
                .andExpect(status().isNotFound());

        verify(customerService, times(1)).findCustomerById(customerId);
    }
}