package com.example.atelier.entities;

import com.example.atelier.enums.Category;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "Services")
public class Services {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "cost")
    double cost;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    Category category;

    @OneToOne(mappedBy = "services", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Order order;
}
