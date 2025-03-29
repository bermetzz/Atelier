package com.example.atelier.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "Material")
public class Material {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "quantity")
    double quantity;

    @Column(name = "cost")
    double cost;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Order> orders;
}
