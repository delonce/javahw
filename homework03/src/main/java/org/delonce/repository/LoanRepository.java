package org.delonce.repository;

import org.delonce.entity.Book;
import org.delonce.entity.Loan;
import org.delonce.entity.Member;

import java.util.List;

public interface LoanRepository {
    List<Loan> findAll();
    List<Loan> findLoanByMember(Member member);

    void takeBook(Member member, Book book, String loan_date, String return_date);
    void returnBook(Member member, Book book);
}
