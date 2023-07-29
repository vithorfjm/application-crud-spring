package com.example.crud.domain.product;

import com.example.crud.domain.category.Category;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="product")
@Entity(name="product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private Integer price_in_cents;

    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true) // Usa somente o ID da categoria
    @JsonProperty("category_id") // Renomeia o atributo para "categoria" no JSON
    private Category category;


    public Product(RequestProduct requestProduct, Category category) {
        this.name = requestProduct.name();;
        this.price_in_cents = requestProduct.price_in_cents();
        this.active = true;
        this.category = category;
    }

}
