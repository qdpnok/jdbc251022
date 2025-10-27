package com.human.jdbc251022.dao;

import com.human.jdbc251022.model.QCVO;
import com.human.jdbc251022.model.PrdResult;
import com.human.jdbc251022.model.ResultQC;
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
    public List<QCVO> qcList() {
        String query = "SELECT * FROM QC";
        return jdbcTemplate.query(query, new QCDao.QCMapper());
    }

    private static class QCMapper implements RowMapper<QCVO> {

        @Override
        public QCVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new QCVO(
                    rs.getString("QC_ID"),
                    rs.getInt("SAMPLE_ID"),
                    rs.getString("TEST_ITEM"),
                    rs.getString("STATUS"),
                    rs.getString("PASS_FAIL"),
                    rs.getDate("QC_DATE"),
                    rs.getInt("TESTER")
            );
        }
}

    // QC 조회 (단건)
    public QCVO getQCVO(String qcId) {
        String query = "SELECT * FROM QC WHERE QC_ID = ?";
        try {
            return jdbcTemplate.queryForObject(query,
                    (rs, rowNum) -> new QCVO(
                            rs.getString("QC_ID"),
                            rs.getString("TEST_ITEM"),
                            rs.getString("STATUS"),
                            rs.getDate("QC_DATE"),
                            rs.getInt("TESTER")), qcId);
        } catch (Exception e) {
            log.error("QC 조회 실패: {}", e.getMessage());
            return null;
        }
    }
    // QC 번호 등록
    public boolean insertQCVO(QCVO qcvo) {
        int result = 0;
        String query = "INSERT INTO QC (QC_ID, SAMPLE_ID, TEST_ITEM, STATUS, PASS_FAIL, QC_DATE, TESTER) VALUES(?, ?, ?, ?, ?)";
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
        String query = "UPDATE QC SET pwd = ?";
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
    public List<Sample> sampleList() {
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
                    rs.getString("SAMPLE_DATE")
            );
        }
    }

    // Sample 조회 (단건)
    public Sample getSample(int sampleId) {
        String query = "SELECT * FROM Sample WHERE SAMPLE_ID = ?";
        try {
            return jdbcTemplate.queryForObject(query,
                    (rs, rowNum) -> new Sample(
                            rs.getInt("SAMPLE_ID"),
                            rs.getInt("BATCH_ID"),
                            rs.getInt("RESULT_ID"),
                            String.valueOf(rs.getInt("SAMPLE_DATE"))));
        } catch (Exception e) {
            log.error("Sample 조회 실패: {}", e.getMessage());
            return null;
        }
    }

    // Sample 번호 등록
    public boolean insertSample(Sample sample) {
        int result = 0;
        String query = "INSERT INTO Sample (SAMPLE_ID, BATCH_ID, RESULT_ID, SAMPLE_DATE) VALUES(?, ?, ?, ?, ?)";
        try {
            // 성공하면 성공한 갯수가 return. 1개 성공하면 1, 2개 성공하면 2
            // query 뒤에는 위쪽 ?에 들어갈 것들.
            result = jdbcTemplate.update(query, sample.getSampleId(),
            sample.getBatchId(), sample.getResultId(), sample.getSampleDate());

        } catch (Exception e) {
            // 로그 메시지. lombok에서 제공함.
            log.error("Sample 번호 등록 실패 : {}", e.getMessage());
        }
        return result > 0;
    }

    // Sample 정보 수정
    public boolean updateSample(Sample sample) {
        int result = 0;
        String query = "UPDATE Sample SET pwd = ?";
        try {
            result = jdbcTemplate.update(query, sample.getSampleId(),
                    sample.getBatchId(), sample.getResultId(), sample.getSampleDate());

        } catch (Exception e) {
            // 로그 메시지. lombok에서 제공함.
            log.error("Sample 정보 수정 실패 : {}", e.getMessage());
        }
        return result > 0;
    }

    // Sample 정보 삭제
    public boolean deleteSample(int sampleId) {
        int result = 0;
        String query = "DELETE FROM Sample WHERE sampleId = ?";
        try {
            result = jdbcTemplate.update(query, sampleId);

        } catch (Exception e) {
            // 로그 메시지. lombok에서 제공함.
            log.error("Sample 삭제 실패 : {}", e.getMessage());
        }
        return result > 0;
    }

    // Result 전체 조회
    public List<ResultQC> resultList() {
        String query = "SELECT * FROM ResultQC";
        return jdbcTemplate.query(query, new QCDao.ResultMapper());
    }

    private static class ResultMapper implements RowMapper<ResultQC> {

        @Override
        public ResultQC mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ResultQC(
                    rs.getInt("RESULT_ID"),
                    rs.getInt("PQTY"),
                    rs.getInt(" WQTY"),
                    rs.getDouble(" PE"),
                    rs.getString(" PROCESS_ID"),
                    rs.getString(" EMP_ID"),
                    rs.getString(" PRODUCTION_DATE")
            );
        }
    }

    // Result 조회 (단건)
    public ResultQC getResult(int resultId) {
        String query = "SELECT * FROM ResultQC WHERE RESULT_ID = ?";
        try {
            return jdbcTemplate.queryForObject(query,
                    (rs, rowNum) -> new ResultQC(
                            rs.getInt("RESULT_ID"),
                            rs.getInt("PQTY"),
                            rs.getInt(" WQTY"),
                            rs.getDouble(" PE"),
                            rs.getString(" PROCESS_ID"),
                            rs.getString(" EMP_ID"),
                            String.valueOf(rs.getInt("PRODUCTION_DATE"))));
        } catch (Exception e) {
            log.error("Sample 조회 실패: {}", e.getMessage());
            return null;
        }
    }

}
