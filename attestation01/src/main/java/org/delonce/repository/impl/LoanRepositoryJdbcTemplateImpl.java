package org.delonce.repository.impl;

import org.delonce.config.JdbcTemplateConfig;
import org.delonce.entity.Book;
import org.delonce.entity.Loan;
import org.delonce.entity.Member;
import org.delonce.repository.LoanRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

public class LoanRepositoryJdbcTemplateImpl implements LoanRepository {

    //language=SQL
    private static final String SQL_SELECT_ALL = "SELECT * FROM loans ORDER BY id";

    //language=SQL
    private static final String SQL_SELECT_LOAN_BY_MEMBER = "SELECT " +
            "l.id, " +
            "l.fk_book_id, " +
            "l.fk_member_id, " +
            "l.loan_date, " +
            "l.return_date " +
            "FROM loans l " +
            "JOIN members m ON l.fk_member_id = m.id " +
            "WHERE l.fk_member_id = ?;";

    //language=SQL
    private static final String SQL_INSERT_LOAN = "INSERT INTO loans(fk_book_id, fk_member_id, loan_date, return_date) values(?, ?, ?, ?)";

    //language=SQL
    private static final String SQL_DELETE_LOAN = "DELETE FROM loans WHERE fk_member_id = ? AND fk_book_id = ?;";

    private final JdbcTemplate jdbcTemplate = JdbcTemplateConfig.JdbcTemplate();

    private static final RowMapper<Loan> loanRowMapper = (row, rowNumber) -> {
        int loanId = row.getInt("id");
        int bookId = row.getInt("fk_book_id");
        int memberId = row.getInt("fk_member_id");
        String loanDate = row.getString("loan_date");
        String returnDate = row.getString("return_date");

        return new Loan(loanId, bookId, memberId, loanDate, returnDate);
    };

    @Override
    public List<Loan> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, loanRowMapper);
    }

    @Override
    public List<Loan> findLoanByMember(Member member) {
        return jdbcTemplate.query(SQL_SELECT_LOAN_BY_MEMBER, (PreparedStatement ps) -> {
            ps.setInt(1, member.getMemberId());
        }, loanRowMapper);
    }

    @Override
    public void takeBook(Member member, Book book, String loan_date, String return_date) {
        jdbcTemplate.update(SQL_INSERT_LOAN, book.getBookId(), member.getMemberId(), Date.valueOf(loan_date), Date.valueOf(return_date));
    }

    @Override
    public void returnBook(Member member, Book book) {
        jdbcTemplate.update(SQL_DELETE_LOAN, member.getMemberId(), book.getBookId());
    }
}
