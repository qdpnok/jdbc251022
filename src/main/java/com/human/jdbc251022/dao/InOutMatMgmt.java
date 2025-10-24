package com.human.jdbc251022.dao;

import com.human.jdbc251022.model.*;
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
public class InOutMatMgmt {
    private final JdbcTemplate jdbcTemplate;

    // 입고 정보 조회
    public List<Inbound> ibList() {
        String query = "SELECT * FROM inbound";
        return jdbcTemplate.query(query, new InOutMatMgmt.IBRowMapper());
    }

    private static class IBRowMapper implements RowMapper<Inbound> {

        // ResultSet -> DB에서 넘어온 결과, rowNum -> 행 번호
        // 행 번호로 자동으로 돌아서 Member에 넣어준다
        @Override
        public Inbound mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Inbound(
                    rs.getInt("ib_id"),
                    rs.getInt("material_id"),
                    rs.getInt("emp_id"),
                    rs.getInt("qty"),
                    rs.getTimestamp("ib_date").toLocalDateTime()
            );
        }
    }

    // 입고 등록
    public boolean insertInbound(Inbound ib) {
        int result = 0;
        String query = "INSERT INTO material(ib_id, material_id, emp_id, qty) VALUES(seq_ib, ?, ?, ?)";
        try {
            // 성공하면 성공한 갯수가 return. 1개 성공하면 1, 2개 성공하면 2
            // query 뒤에는 위쪽 ?에 들어갈 것들.
            result = jdbcTemplate.update(query, ib.getMatId(), ib.getEmpId(), ib.getQty());

        } catch (Exception e) {
            // 로그 메시지. lombok에서 제공함.
            log.error("입고 등록 실패 : {}", e.getMessage());
        }
        return result > 0;
    }

    // 배치 등록
    public boolean insertBatch(Batch batch) {
        int result = 0;
        String query = "INSERT INTO batch(batch_id, product_id, unit, status, note) VALUES(seq_batch, ?, ?, ?, ?)";
        try {
            // 성공하면 성공한 갯수가 return. 1개 성공하면 1, 2개 성공하면 2
            // query 뒤에는 위쪽 ?에 들어갈 것들.
            result = jdbcTemplate.update(query, batch.getProdId(), batch.getUnit(),
                    batch.getStatus(), batch.getNote());

        } catch (Exception e) {
            // 로그 메시지. lombok에서 제공함.
            log.error("배치 등록 실패 : {}", e.getMessage());
        }
        return result > 0;
    }

    // 출고 조회
    public List<Outbound> obList() {
        String query = "SELECT * FROM outbound";
        return jdbcTemplate.query(query, new InOutMatMgmt.OBRowMapper());
    }

    private static class OBRowMapper implements RowMapper<Outbound> {

        // ResultSet -> DB에서 넘어온 결과, rowNum -> 행 번호
        // 행 번호로 자동으로 돌아서 Member에 넣어준다
        @Override
        public Outbound mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Outbound(
                    rs.getInt("ob_id"),
                    rs.getInt("batch_id"),
                    rs.getInt("emp_id"),
                    rs.getInt("qty"),
                    rs.getTimestamp("ob_date").toLocalDateTime()
            );
        }
    }

    // 출고 등록
    public boolean insertOutbound(Outbound ob) {
        int result = 0;
        String query = "INSERT INTO outbound(ob_id, batch_id, emp_id, qty) VALUES(seq_ob, ?, ?, ?)";
        try {
            // 성공하면 성공한 갯수가 return. 1개 성공하면 1, 2개 성공하면 2
            // query 뒤에는 위쪽 ?에 들어갈 것들.
            result = jdbcTemplate.update(query, ob.getBatchId(), ob.getEmpId(),
                    ob.getQty());

        } catch (Exception e) {
            // 로그 메시지. lombok에서 제공함.
            log.error("출고 등록 실패 : {}", e.getMessage());
        }
        return result > 0;
    }
}
