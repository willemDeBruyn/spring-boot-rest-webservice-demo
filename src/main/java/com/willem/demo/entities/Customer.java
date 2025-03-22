package com.willem.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String address;

    // CascadeType.ALL: Deleting Customer will delete associated Orders.
    // orphanRemoval = true: Removing Order from list in Customer will delete Order from DB
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
}
