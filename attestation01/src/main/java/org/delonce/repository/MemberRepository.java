package org.delonce.repository;

import org.delonce.entity.Member;

import java.util.List;

public interface MemberRepository {
    List<Member> findAll();
}
