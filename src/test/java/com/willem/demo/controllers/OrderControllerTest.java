package com.willem.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.willem.demo.entities.Order;
import com.willem.demo.model.CustomerDto;
import com.willem.demo.model.OrderDto;
import com.willem.demo.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author willem
 */
@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest
{
    private static final Long CUSTOMER_ID = 1L;
    private static final Long ORDER_ID = 1L;
    private static final Long NON_EXISTENT_ORDER_ID = 999L;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    OrderService orderService;

    @Test
    void testSaveOrder() throws Exception
    {
        OrderDto orderDto = createOrderDto(ORDER_ID);

        when(orderService.saveOrder(any(OrderDto.class))).thenReturn(orderDto);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ASUS LAPTOP"))
                .andExpect(jsonPath("$.description").value("Gaming laptop"));

        verify(orderService, times(1)).saveOrder(any(OrderDto.class));
    }

    @Test
    void testDeleteOrderNotFound() throws Exception
    {
        when(orderService.deleteOrder(ORDER_ID)).thenReturn(false);

        mockMvc.perform(delete("/api/orders/{id}", ORDER_ID))
                .andExpect(status().isNotFound());

        verify(orderService, times(1)).deleteOrder(ORDER_ID);
    }

    @Test
    void testGetAllOrders() throws Exception
    {
        OrderDto orderDto1 = createOrderDto(ORDER_ID);
        OrderDto orderDto2 = createOrderDto(2L);

        when(orderService.findAllOrders()).thenReturn(List.of(orderDto1, orderDto2));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ASUS LAPTOP"))
                .andExpect(jsonPath("$[1].name").value("ASUS LAPTOP"));

        verify(orderService, times(1)).findAllOrders();
    }

    @Test
    void testGetOrderById() throws Exception
    {
        OrderDto orderDto = createOrderDto(ORDER_ID);

        when(orderService.findOrderById(ORDER_ID)).thenReturn(Optional.of(orderDto));

        // Perform the GET request and check the response
        mockMvc.perform(get("/api/orders/{id}", ORDER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ASUS LAPTOP"))
                .andExpect(jsonPath("$.description").value("Gaming laptop"));

        verify(orderService, times(1)).findOrderById(ORDER_ID);
    }

    @Test
    void testGetOrderByIdNotFound() throws Exception
    {
        // Mock the service method to return an empty Optional (customer not found)
        when(orderService.findOrderById(NON_EXISTENT_ORDER_ID)).thenReturn(Optional.empty());

        // Perform the GET request and check the response
        mockMvc.perform(get("/api/orders/{id}", NON_EXISTENT_ORDER_ID))
                .andExpect(status().isNotFound());

        verify(orderService, times(1)).findOrderById(NON_EXISTENT_ORDER_ID);
    }

    @Test
    void updateOrderTest() throws Exception
    {
        // Given
        OrderDto inputDto = createOrderDto(ORDER_ID);
        OrderDto updatedDto = createOrderDto(ORDER_ID);

        // When the service is called, return the updated DTO
        when(orderService.updateOrder(eq(ORDER_ID), any(OrderDto.class)))
                .thenReturn(updatedDto);

        // Convert DTOs to JSON strings
        String inputJson = objectMapper.writeValueAsString(inputDto);
        String expectedJson = objectMapper.writeValueAsString(updatedDto);

        // Execute the PUT request and verify results
        mockMvc.perform(put("/api/orders/{id}", ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        // Verify that the service method was called exactly once
        verify(orderService, times(1)).updateOrder(eq(ORDER_ID), any(OrderDto.class));
    }

    private CustomerDto createCustomerDto()
    {
        return CustomerDto.builder()
                .id(CUSTOMER_ID)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123456789")
                .address("address")
                .build();
    }

    private OrderDto createOrderDto(Long orderId)
    {
        return OrderDto.builder()
                .id(orderId)
                .name("ASUS LAPTOP")
                .description("Gaming laptop")
                .price(new BigDecimal("19.99"))
                .customer(createCustomerDto())
                .status(Order.OrderStatus.PENDING)
                .orderDate(LocalDateTime.now())
                .build();
    }
}

