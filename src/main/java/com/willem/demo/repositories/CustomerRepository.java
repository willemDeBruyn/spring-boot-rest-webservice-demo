package com.willem.demo.repositories;

import com.willem.demo.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @author willem
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>
{
}
