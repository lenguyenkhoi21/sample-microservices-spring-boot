package com.example.servicesa.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    private String id;
    private long productId;
    private String name;
    private String image;
    private String description;
    private int price;
    private boolean status;
}
