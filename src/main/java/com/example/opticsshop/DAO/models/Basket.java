package com.example.opticsshop.DAO.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="baskets")
@Data
@Getter
@Setter
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "amount")
    private int amount = 0;

    @Column(name = "productId")
    private Long productId;

    @ManyToOne
    @JoinColumn()
    private Product curr_product;
}