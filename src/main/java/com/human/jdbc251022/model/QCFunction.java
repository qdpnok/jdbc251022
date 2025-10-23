package com.human.jdbc251022.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; // ResultSet 사용을 위해 추가
import java.sql.SQLException;
import java.util.Date; // Date 사용을 위해 추가

public class QCFunction {

    // QC 번호와 테스트 정보
    public int insertQC(QCVO qc) { // ⚠️ 'public' 키워드 추가
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        String sql = "INSERT INTO QC (QC_ID, SAMPLE_ID, TEST_ITEM, STATUS, PASS_FAIL, QC_DATE, TESTER) VALUES (?, ?, ?, ?, ?, SYSDATE, ?)";

        try {
            conn = DB.getConnection(); // DBUtil로 통일
            pstmt = conn.prepareStatement(sql);

            // 1. QC 번호 (QC_ID): 임의 생성
            pstmt.setString(1, "QC" + System.nanoTime());

            // 2. 품질 테스트 (TEST_ITEM) 및 기타 필수 정보 설정
            pstmt.setInt(2, qc.getSampleId()); // ⚠️ 'getSampleI()' -> 'getSampleId()' 수정
            pstmt.setString(3, qc.getTestItem());

            // 3. 완료/취소 상태 (STATUS) 설정
            pstmt.setString(4, qc.getStatus());

            pstmt.setString(5, qc.getPassFail());
            pstmt.setInt(6, qc.getTester());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("QC 등록 중 오류 발생: " + e.getMessage());
        } finally {
            DB.close(conn, pstmt, null);
        }
        return result;
    }

    // QC 번호에 따라 상태와 완료 날짜를 수정
    public int updateQCStatusAndDate(String qcId, String newStatus, Date newQcDate) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        String sql = "UPDATE QC SET STATUS = ?, QC_DATE = ? WHERE QC_ID = ?";

        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(sql);

            // 1. 완료/취소 상태 (STATUS) 설정
            pstmt.setString(1, newStatus);

            // 2. 완료 날짜 (QC_DATE) 설정: java.util.Date -> java.sql.Date 변환 필수
            pstmt.setDate(2, new java.sql.Date(newQcDate.getTime()));

            // 3. QC 번호 (QC_ID) 설정
            pstmt.setString(3, qcId);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("QC 상태/날짜 수정 중 오류 발생: " + e.getMessage());
        } finally {
            DB.close(conn, pstmt, null);
        }
        return result;
    }

    // 특정 QC 번호의 상세 정보를 조회
    public QCVO selectQC(String qcId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        QCVO qc = null;

        String sql = "SELECT QC_ID, SAMPLE_ID, TEST_ITEM, STATUS, PASS_FAIL, QC_DATE, TESTER FROM QC WHERE QC_ID = ?";

        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, qcId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                qc = new QCVO();
                qc.setQcId(rs.getString("QC_ID"));
                qc.setSampleId(rs.getInt("SAMPLE_ID"));
                qc.setTestItem(rs.getString("TEST_ITEM"));
                qc.setStatus(rs.getString("STATUS"));
                qc.setPassFail(rs.getString("PASS_FAIL"));
                qc.setQcDate(rs.getDate("QC_DATE"));
                qc.setTester(rs.getInt("TESTER"));
            }

        } catch (SQLException e) {
            System.err.println("QC 조회 중 오류 발생: " + e.getMessage());
        } finally {
            DB.close(conn, pstmt, rs);
        }
        return qc;
    }
}