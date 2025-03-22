package com.willem.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.willem.demo.model.CustomerDto;
import com.willem.demo.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author willem
 */

@WebMvcTest(CustomerController.class)
class CustomerControllerTest
{
    private static final Long CUSTOMER_ID = 1L;
    private static final Long NON_EXISTENT_CUSTOMER_ID = 999L;

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CustomerService customerService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testSaveCustomer() throws Exception
    {
        CustomerDto customerDto = createCustomerDto(CUSTOMER_ID, "John", "Doe", "john.doe@example.com");

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
        // Mock the service method to return true when deleting
        when(customerService.deleteCustomer(CUSTOMER_ID)).thenReturn(true);

        // Perform the DELETE request and check the response
        mockMvc.perform(delete("/api/customers/{id}", CUSTOMER_ID))
                .andExpect(status().isNoContent());

        verify(customerService, times(1)).deleteCustomer(CUSTOMER_ID);
    }

    @Test
    void testDeleteCustomerNotFound() throws Exception
    {
        // Mock the service method to return false (customer not found)
        when(customerService.deleteCustomer(NON_EXISTENT_CUSTOMER_ID)).thenReturn(false);

        // Perform the DELETE request and check the response
        mockMvc.perform(delete("/api/customers/{id}", NON_EXISTENT_CUSTOMER_ID))
                .andExpect(status().isNotFound());

        verify(customerService, times(1)).deleteCustomer(NON_EXISTENT_CUSTOMER_ID);
    }

    @Test
    void testGetAllCustomers() throws Exception
    {
        List<CustomerDto> customers = List.of(
                createCustomerDto(1L, "John", "Doe", "john.doe@example.com"),
                createCustomerDto(2L, "Jane", "Doe", "jane.doe@example.com")
        );

        // Mock the service method to return a list of customers
        when(customerService.findAllCustomers()).thenReturn(customers);

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
        CustomerDto customerDto = createCustomerDto(CUSTOMER_ID, "John", "Doe", "john.doe@example.com");

        when(customerService.findCustomerById(CUSTOMER_ID)).thenReturn(Optional.of(customerDto));

        // Perform the GET request and check the response
        mockMvc.perform(get("/api/customers/{id}", CUSTOMER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(customerService, times(1)).findCustomerById(CUSTOMER_ID);
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception
    {
        // Mock the service method to return an empty Optional (customer not found)
        when(customerService.findCustomerById(CUSTOMER_ID)).thenReturn(Optional.empty());

        // Perform the GET request and check the response
        mockMvc.perform(get("/api/customers/{id}", CUSTOMER_ID))
                .andExpect(status().isNotFound());

        verify(customerService, times(1)).findCustomerById(CUSTOMER_ID);
    }

    @Test
    void updateCustomerTest() throws Exception
    {
        // Given
        CustomerDto inputDto = createCustomerDto(CUSTOMER_ID, "John", "Doe", "john.doe@example.com");
        CustomerDto updatedDto = createCustomerDto(CUSTOMER_ID, "Jane", "Doe", "john.doe@example.com");

        // When the service is called, return the updated DTO
        when(customerService.updateCustomer(eq(CUSTOMER_ID), any(CustomerDto.class)))
                .thenReturn(updatedDto);

        // Convert DTOs to JSON strings
        String inputJson = objectMapper.writeValueAsString(inputDto);
        String expectedJson = objectMapper.writeValueAsString(updatedDto);

        // Execute the PUT request and verify results
        mockMvc.perform(put("/api/customers/{id}", CUSTOMER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        // Verify that the service method was called exactly once
        verify(customerService, times(1)).updateCustomer(eq(CUSTOMER_ID), any(CustomerDto.class));
    }

    private CustomerDto createCustomerDto(Long id, String name, String surname, String email)
    {
        return CustomerDto.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .email(email)
                .phoneNumber("123456789")
                .address("address")
                .build();
    }
}