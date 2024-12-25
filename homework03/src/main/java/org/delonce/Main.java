package org.delonce;

import org.delonce.entity.Book;
import org.delonce.entity.Category;
import org.delonce.entity.Member;
import org.delonce.repository.BookRepository;
import org.delonce.repository.CategoryRepository;
import org.delonce.repository.LoanRepository;
import org.delonce.repository.MemberRepository;
import org.delonce.repository.impl.BookRepositoryJdbcTemplateImpl;
import org.delonce.repository.impl.CategoryRepositoryJdbcTemplateImpl;
import org.delonce.repository.impl.LoanRepositoryJdbcTemplateImpl;
import org.delonce.repository.impl.MemberRepositoryJdbcTemplateImpl;

public class Main {
    public static void main(String[] args) {
        BookRepository bookRepository = new BookRepositoryJdbcTemplateImpl();
        System.out.println(bookRepository.findAll());
        System.out.println(bookRepository.findAllByCategoriesAndAuthors());

        LoanRepository loanRepository = new LoanRepositoryJdbcTemplateImpl();
        System.out.println(loanRepository.findAll());

        MemberRepository memberRepository = new MemberRepositoryJdbcTemplateImpl();
        Member firstMember = memberRepository.findAll().getFirst();

        System.out.println(loanRepository.findLoanByMember(firstMember));

        Book lastByIdBook = bookRepository.findAll().getLast();
        loanRepository.takeBook(firstMember, lastByIdBook, "2023-11-15", "2023-12-15");
        System.out.println(loanRepository.findLoanByMember(firstMember));

        loanRepository.returnBook(firstMember, lastByIdBook);
        System.out.println(loanRepository.findLoanByMember(firstMember));

        CategoryRepository categoryRepository = new CategoryRepositoryJdbcTemplateImpl();
        System.out.println(categoryRepository.findAll());

        categoryRepository.createCategory("TestCategory");
        System.out.println(categoryRepository.findAll());

        Category lastCategory = categoryRepository.findAll().getLast();

        categoryRepository.deleteCategory(lastCategory.getCategoryId());

        System.out.println(categoryRepository.findAll());
    }
}
