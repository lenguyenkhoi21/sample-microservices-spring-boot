package com.example.servicesb.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class ProductMG {
    @Id
    private String id;
    private long productId;
    private String name;
    private String image;
    private String description;
    private int price;
    private boolean status;
}
