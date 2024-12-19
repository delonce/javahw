package org.delonce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookWithDesc {
    private String title;
    private String author;
    private String category;
    private String publishedDate;
}
