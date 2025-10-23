package com.human.jdbc251022.dao;

import com.human.jdbc251022.model.Employee;
import com.human.jdbc251022.model.Material;
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

    // 원자재 조회 - 전체
    public List<Material> matList() {
        String query = "SELECT * FROM material";
        return jdbcTemplate.query(query, new MgmtDao.MatRowMapper());
    }

    // 원자재 조회 - 검색(이름)
    public List<Material> matNameList(String name) {
        String query = "SELECT * FROM material WHERE material_name = ?";
        return jdbcTemplate.query(query, new MgmtDao.MatRowMapper(), name);
    }

    // 원자재 등록
    public boolean insertMaterial(Material mat) {
        int result = 0;
        String query = "INSERT INTO material(material_id, material_name, type) VALUES(?, ?, ?)";
        try {
            // 성공하면 성공한 갯수가 return. 1개 성공하면 1, 2개 성공하면 2
            // query 뒤에는 위쪽 ?에 들어갈 것들.
            result = jdbcTemplate.update(query, mat.getId(), mat.getName(), mat.getType());

        } catch (Exception e) {
            // 로그 메시지. lombok에서 제공함.
            log.error("원자재 정보 등록 실패 : {}", e.getMessage());
        }
        return result > 0;
    }

    // 원자재 정보 수정
    public boolean updateMaterial(Material mat) {
        int result = 0;
        String query = "UPDATE material SET material_name = ?, type = ? WHERE material_id = ?";
        try {
            // 성공하면 성공한 갯수가 return. 1개 성공하면 1, 2개 성공하면 2
            // query 뒤에는 위쪽 ?에 들어갈 것들.
            result = jdbcTemplate.update(query, mat.getName(), mat.getType(), mat.getId());

        } catch (Exception e) {
            // 로그 메시지. lombok에서 제공함.
            log.error("원자재 정보 수정 실패 : {}", e.getMessage());
        }
        return result > 0;
    }

    // 원자재 삭제
    public boolean deleteMaterial(Material mat) {
        int result = 0;
        String query = "DELETE FROM material WHERE material_id = ?";
        try {
            // 성공하면 성공한 갯수가 return. 1개 성공하면 1, 2개 성공하면 2
            // query 뒤에는 위쪽 ?에 들어갈 것들.
            result = jdbcTemplate.update(query, mat.getId());
        } catch (Exception e) {
            // 로그 메시지. lombok에서 제공함.
            log.error("원자재 삭제 실패 : {}", e.getMessage());
        }
        return result > 0;
    }

    // 원자재 매퍼
    private static class MatRowMapper implements RowMapper<Material> {

        // ResultSet -> DB에서 넘어온 결과, rowNum -> 행 번호
        // 행 번호로 자동으로 돌아서 Member에 넣어준다
        @Override
        public Material mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Material(
                    rs.getInt("material_id"),
                    rs.getString("material_name"),
                    rs.getString("type")
            );
        }
    }


}
