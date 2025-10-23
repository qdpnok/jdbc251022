package com.human.jdbc251022.dao;

import com.human.jdbc251022.model.Employee;
import com.human.jdbc251022.model.QCVO;
import com.human.jdbc251022.model.Sample;
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

public class QCDao {
    private final JdbcTemplate jdbcTemplate;

    // QC 전체 조회
    public List<QCVO> empList(QCVO qcvo) {
        String query = "SELECT * FROM qcvo";
        return jdbcTemplate.query(query, new QCDao.QCMapper());
    }

    private static class QCMapper implements RowMapper<QCVO> {

        @Override
        public QCVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new QCVO(
                    rs.getString("QC_ID"),
                    rs.getString("QC.ITEM_ID"),
                    rs.getString("STATUS"),
                    rs.getDate("QC_DATE"),
                    rs.getInt("EMP_ID")
            );
        }
}

    // QC 조회 (단건)
    public QCVO getQCVO(String qcId) {
        String query = "SELECT * FROM QCVO WHERE QC_ID = ?";
        try {
            return jdbcTemplate.queryForObject(query,
                    (rs, rowNum) -> new QCVO(
                            rs.getString("QC_ID"),
                            rs.getString("QC.ITEM_ID"),
                            rs.getString("STATUS"),
                            rs.getDate("QC_DATE"),
                            rs.getInt("EMP_ID")));
        } catch (Exception e) {
            log.error("QC 조회 실패: {}", e.getMessage());
            return null;
        }
    }
    // QC 번호 등록
    public boolean insertQCVO(QCVO qcvo) {
        int result = 0;
        String query = "INSERT INTO QCVO (QC_ID, SAMPLE_ID, TEST_ITEM, STATUS, PASS_FAIL, QC_DATE, TESTER) VALUES(?, ?, ?, ?, ?)";
        try {
            // 성공하면 성공한 갯수가 return. 1개 성공하면 1, 2개 성공하면 2
            // query 뒤에는 위쪽 ?에 들어갈 것들.
            result = jdbcTemplate.update(query, qcvo.getQcId(), qcvo.getSampleId(),
                    qcvo.getTestItem(), qcvo.getStatus(), qcvo.getPassFail(), qcvo.getQcDate(), qcvo.getTester());

        } catch (Exception e) {
            // 로그 메시지. lombok에서 제공함.
            log.error("QC 번호 등록 실패 : {}", e.getMessage());
        }
        return result > 0;
    }

    // QC 정보 수정
    public boolean updateQCVO(QCVO qcvo) {
        int result = 0;
        String query = "UPDATE QCVO SET pwd = ?";
        try {
            result = jdbcTemplate.update(query, qcvo.getQcId(), qcvo.getSampleId(),
                    qcvo.getTestItem(), qcvo.getStatus(), qcvo.getPassFail(), qcvo.getQcDate(), qcvo.getTester());

        } catch (Exception e) {
            // 로그 메시지. lombok에서 제공함.
            log.error("QC 정보 수정 실패 : {}", e.getMessage());
        }
        return result > 0;
    }

    // QC 정보 삭제
    public boolean deleteQCVO(String qcId) {
        int result = 0;
        String query = "DELETE FROM Employee WHERE qcId = ?";
        try {
            result = jdbcTemplate.update(query, qcId);

        } catch (Exception e) {
            // 로그 메시지. lombok에서 제공함.
            log.error("QC 삭제 실패 : {}", e.getMessage());
        }
        return result > 0;
    }


    // Sample 전체 조회
    public List<Sample> sampleList(Sample sample) {
        String query = "SELECT * FROM Sample";
        return jdbcTemplate.query(query, new QCDao.SampleMapper());
    }

    private static class SampleMapper implements RowMapper<Sample> {

        @Override
        public Sample mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Sample(
                    rs.getInt("SAMPLE_ID"),
                    rs.getInt("BATCH_ID"),
                    rs.getInt("RESULT_ID"),
                    rs.getInt("SAMPLE_DATE")
            );
        }
    }



}
