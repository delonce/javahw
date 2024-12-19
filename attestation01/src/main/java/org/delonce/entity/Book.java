package org.delonce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    private int bookId;
    private String title;
    private int authorId;
    private int categoryId;
    private String publishedDate;
}
