package org.delonce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Loan {
    private int loanId;
    private int bookId;
    private int memberId;
    private String loanDate;
    private String returnDate;
}

