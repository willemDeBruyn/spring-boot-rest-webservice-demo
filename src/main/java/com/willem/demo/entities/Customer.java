package com.willem.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

/**
 * @author willem
 */
@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String address;

    // CascadeType.ALL: Deleting Customer will delete associated Orders.
    // orphanRemoval = true: Removing Order from list in Customer will delete Order from DB
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
}
