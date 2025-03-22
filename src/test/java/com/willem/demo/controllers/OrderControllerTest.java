package com.willem.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.willem.demo.model.OrderDto;
import com.willem.demo.services.OrderService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

/**
 * @author willem
 */
@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    OrderService orderService;

    @Test
    void testSaveOrder() throws Exception
    {
        OrderDto orderDto = OrderDto.builder()
                .id(1L)
                .name("ASUS LAPTOP")
                .description("Gaming laptop")
                .price(new BigDecimal("19.99"))
                .build();

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
        Long orderId = 999L;

        when(orderService.deleteOrder(orderId)).thenReturn(false);

        mockMvc.perform(delete("/api/orders/{id}", orderId))
                .andExpect(status().isNotFound());

        verify(orderService, times(1)).deleteOrder((orderId));
    }

    @Test
    void testGetAllOrders() throws Exception
    {
        OrderDto orderDto1 = OrderDto.builder()
                .id(1L)
                .name("ASUS LAPTOP")
                .description("Gaming laptop")
                .price(new BigDecimal("19.99"))
                .build();

        OrderDto orderDto2 = OrderDto.builder()
                .id(2L)
                .name("PlayStation 5")
                .description("Console")
                .price(new BigDecimal("19.99"))
                .build();

        when(orderService.findAllOrders()).thenReturn(List.of(orderDto1, orderDto2));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ASUS LAPTOP"))
                .andExpect(jsonPath("$[1].name").value("PlayStation 5"));

        verify(orderService, times(1)).findAllOrders();
    }

    @Test
    void testGetOrderById() throws Exception
    {
        Long orderId = 1L;
        OrderDto orderDto = OrderDto.builder()
                .id(orderId)
                .name("ASUS LAPTOP")
                .description("Gaming laptop")
                .price(new BigDecimal("19.99"))
                .build();

        when(orderService.findOrderById(orderId)).thenReturn(Optional.of(orderDto));

        // Perform the GET request and check the response
        mockMvc.perform(get("/api/orders/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ASUS LAPTOP"))
                .andExpect(jsonPath("$.description").value("Gaming laptop"));

        verify(orderService, times(1)).findOrderById(orderId);
    }

    @Test
    void testGetOrderByIdNotFound() throws Exception
    {
        Long orderId = 999L;

        // Mock the service method to return an empty Optional (customer not found)
        when(orderService.findOrderById(orderId)).thenReturn(Optional.empty());

        // Perform the GET request and check the response
        mockMvc.perform(get("/api/orders/{id}", orderId))
                .andExpect(status().isNotFound());

        verify(orderService, times(1)).findOrderById(orderId);
    }

}