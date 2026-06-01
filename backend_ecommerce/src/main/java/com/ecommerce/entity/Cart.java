package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(
            strategy =
                    GenerationType.IDENTITY
    )
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "customer_id",
            nullable = false,
            unique = true
    )
    private Customer customer;

    @OneToMany(
            mappedBy = "cart",
            cascade =
                    CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CartItem> items =
            new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {

        createdAt =
                LocalDateTime.now();

        updatedAt =
                LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {

        updatedAt =
                LocalDateTime.now();
    }
}