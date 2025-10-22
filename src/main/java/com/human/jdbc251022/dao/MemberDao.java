package com.human.jdbc251022.dao;

import com.human.jdbc251022.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository // Spring container Bean 등록
@Slf4j
@RequiredArgsConstructor    // 생성자를 통해서 의존성 주입을 받음
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;

    // 회원 전체 조회
    public List<Member> memberList() {
        String query = "SELECT * FROM member";
        return jdbcTemplate.query(query, new MemberRowMapper());
    }

    private static class MemberRowMapper implements RowMapper<Member> {

        // ResultSet -> DB에서 넘어온 결과, rowNum -> 행 번호
        // 행 번호로 자동으로 돌아서 Member에 넣어준다
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Member(
                    rs.getString("email"),
                    rs.getString("pwd"),
                    rs.getString("name"),
                    rs.getTimestamp("reg_date").toLocalDateTime()
            );
        }
    }

    // 회원 등록
    public boolean insertMember(Member member) {
        int result = 0;
        String query = "INSERT INTO member(email, pwd, name) VALUES(?, ?, ?)";
        try {
            // 성공하면 성공한 갯수가 return. 1개 성공하면 1, 2개 성공하면 2
            // query 뒤에는 위쪽 ?에 들어갈 것들.
            result = jdbcTemplate.update(query, member.getEmail(),
                    member.getPwd(), member.getName());

        } catch (Exception e) {
            // 로그 메시지. lombok에서 제공함.
            log.error("회원 정보 추가 실패 : {}", e.getMessage());
        }
        return result > 0;
    }

    // 회원 정보 수정
    public boolean updateMember(Member member) {
        int result = 0;
        String query = "UPDATE member SET pwd = ?";
        try {
            // 성공하면 성공한 갯수가 return. 1개 성공하면 1, 2개 성공하면 2
            // query 뒤에는 위쪽 ?에 들어갈 것들.
            result = jdbcTemplate.update(query, member.getPwd());

        } catch (Exception e) {
            // 로그 메시지. lombok에서 제공함.
            log.error("회원 정보 수정 실패 : {}", e.getMessage());
        }
        return result > 0;
    }

    // 회원 삭제
    public boolean deleteMember(String email) {
        int result = 0;
        String query = "DELETE FROM member WHERE email = ?";
        try {
            // 성공하면 성공한 갯수가 return. 1개 성공하면 1, 2개 성공하면 2
            // query 뒤에는 위쪽 ?에 들어갈 것들.
            result = jdbcTemplate.update(query, email);

        } catch (Exception e) {
            // 로그 메시지. lombok에서 제공함.
            log.error("회원 정보 수정 실패 : {}", e.getMessage());
        }
        return result > 0;
    }

}
