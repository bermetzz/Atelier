package com.example.atelier.entities;

import com.example.atelier.enums.Size;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "Order_material")
@Where(clause = "status=false")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "order_date")
    LocalDate orderDate;

    @Column(name = "end_date")
    LocalDate endDate;

    @Column(name = "status")
    boolean status;

    @Column(name = "cost")
    double cost;

    @Enumerated(EnumType.STRING)
    Size size;

    @ManyToOne
    User user;

    @ManyToOne
    User worker;

    @OneToOne
    Services services;

    @ManyToOne
    Material material;
}
