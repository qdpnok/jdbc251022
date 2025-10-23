package com.human.jdbc251022.dao;

import com.human.jdbc251022.model.Employee;
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
@RequiredArgsConstructor
public class MgmtDao {
    private final JdbcTemplate jdbcTemplate;

    // 회원 전체 조회
    public List<Employee> empList() {
        String query = "SELECT * FROM member";
        return jdbcTemplate.query(query, new MgmtDao.EmpRowMapper());
    }

    private static class EmpRowMapper implements RowMapper<Employee> {

        // ResultSet -> DB에서 넘어온 결과, rowNum -> 행 번호
        // 행 번호로 자동으로 돌아서 Member에 넣어준다
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Employee(
                    rs.getInt("emp_id"),
                    rs.getString("emp_name"),
                    rs.getString("job"),
                    rs.getInt("dept_id"),
                    rs.getInt("sal"),
                    rs.getTimestamp("hiredate").toLocalDateTime()
            );
        }
    }

}
