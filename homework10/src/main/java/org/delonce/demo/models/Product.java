package org.delonce.demo.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "products", schema = "delonce")
public class Product {
    @Id
    private Long id;
    private String article;
    private Integer amount;
    private Long total;
}

