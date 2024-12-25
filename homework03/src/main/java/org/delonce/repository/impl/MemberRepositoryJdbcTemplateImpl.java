package org.delonce.repository.impl;

import org.delonce.config.JdbcTemplateConfig;
import org.delonce.entity.Member;
import org.delonce.repository.MemberRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class MemberRepositoryJdbcTemplateImpl implements MemberRepository {

    private static final String SQL_SELECT_ALL = "SELECT * FROM members ORDER BY id";

    private final JdbcTemplate jdbcTemplate = JdbcTemplateConfig.jdbcTemplate();

    private static final RowMapper<Member> memberRowMapper = (row, rowNumber) -> {
        int memberId = row.getInt("id");
        String fullName = row.getString("full_name");
        String membershipDate = row.getString("membership_date");

        return new Member(memberId, fullName, membershipDate);
    };

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, memberRowMapper);
    }
}
