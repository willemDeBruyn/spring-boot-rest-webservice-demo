package com.willem.demo.repositories;

import com.willem.demo.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author willem
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>
{
    List<Order> findByCustomerId(Long customerId);
}
