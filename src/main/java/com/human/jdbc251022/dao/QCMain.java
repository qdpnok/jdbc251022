package com.human.jdbc251022.dao;

import com.human.jdbc251022.model.DB;
import com.human.jdbc251022.model.QCVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class QCMain {

    // -------------------------------------------------------------
    // ⭐ DAO 역할의 메서드들을 Main 클래스 내부에 정의합니다.
    // (원래 QCFunction 클래스의 내용)
    // -------------------------------------------------------------

    // QC 번호와 테스트 정보 등록 (INSERT)
    public int insertQC(QCVO qc) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        // DB.getConnection()과 DB.close()는 별도의 파일(DB.java)에 존재해야 합니다.
        String sql = "INSERT INTO QC (QC_ID, SAMPLE_ID, TEST_ITEM, STATUS, PASS_FAIL, QC_DATE, TESTER) VALUES (?, ?, ?, ?, ?, SYSDATE, ?)";

        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "QC" + System.nanoTime());
            pstmt.setInt(2, qc.getSampleId());
            pstmt.setString(3, qc.getTestItem());
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

    // QC 번호에 따라 상태와 완료 날짜 수정 (UPDATE)
    public int updateQCStatusAndDate(String qcId, String newStatus, Date newQcDate) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        String sql = "UPDATE QC SET STATUS = ?, QC_DATE = ? WHERE QC_ID = ?";

        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newStatus);
            // java.util.Date를 java.sql.Date로 변환
            pstmt.setDate(2, new java.sql.Date(newQcDate.getTime()));
            pstmt.setString(3, qcId);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("QC 상태/날짜 수정 중 오류 발생: " + e.getMessage());
        } finally {
            DB.close(conn, pstmt, null);
        }
        return result;
    }

    // 특정 QC 번호의 상세 정보 조회 (SELECT)
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

    // -------------------------------------------------------------
    // ⭐ Main 실행 메서드 (원래 QCTestMain 클래스의 내용)
    // -------------------------------------------------------------
//    public static void main(String[] args) {
//        // DAO 역할을 수행하는 QCMainTest 객체 생성
//        QCMainTest qcDao = new QCMainTest();
//        String testQcId = "";
//
//        // 1. QC 등록 (INSERT) 테스트
//        System.out.println("### 1. QC 등록 테스트 (INSERT) ###");
//        QCVO newQc = new QCVO();
//        newQc.setSampleId(300001);
//        newQc.setTestItem("Purity");
//        newQc.setStatus("In Progress");
//        newQc.setPassFail("N/A");
//        newQc.setTester(1006);
//
//        int insertResult = qcDao.insertQC(newQc);
//
//        if (insertResult > 0) {
//            System.out.println("✅ QC 항목 등록 성공. 영향 받은 행 수: " + insertResult);
//            testQcId = "QC0009"; // 테스트를 위해 기존 ID 사용
//        } else {
//            System.out.println("❌ QC 항목 등록 실패.");
//            return;
//        }
//
//        System.out.println("\n" + "---".repeat(15) + "\n");
//
//        // 2. QC 상태 및 완료 날짜 수정 (UPDATE) 테스트
//        System.out.println("### 2. QC 상태/날짜 수정 테스트 (UPDATE) ###");
//        String updateId = "QC0006";
//        String newStatus = "Completed";
//        Date completeDate = new Date();
//
//        int updateResult = qcDao.updateQCStatusAndDate(updateId, newStatus, completeDate);
//
//        if (updateResult > 0) {
//            System.out.println("✅ QC ID: " + updateId + " 상태 및 날짜 수정 성공.");
//        } else {
//            System.out.println("❌ QC ID: " + updateId + " 수정 실패. (ID를 확인하세요)");
//        }
//
//        System.out.println("\n" + "---".repeat(15) + "\n");
//
//        // 3. QC 상세 정보 조회 (SELECT) 테스트
//        System.out.println("### 3. QC 상세 조회 테스트 (SELECT) ###");
//        String selectId = "QC0009";
//        QCVO fetchedQc = qcDao.selectQC(selectId);
//
//        if (fetchedQc != null) {
//            System.out.println("✅ QC ID: " + selectId + " 조회 성공.");
//            System.out.println("   > QC ID: " + fetchedQc.getQcId());
//            System.out.println("   > 샘플 ID: " + fetchedQc.getSampleId());
//            System.out.println("   > 테스트 항목: " + fetchedQc.getTestItem());
//            System.out.println("   > 상태: " + fetchedQc.getStatus());
//            System.out.println("   > 합격/불합격: " + fetchedQc.getPassFail());
//            System.out.println("   > 검사일: " + fetchedQc.getQcDate());
//            System.out.println("   > 담당자 ID: " + fetchedQc.getTester());
//        } else {
//            System.out.println("❌ QC ID: " + selectId + " 조회 실패.");
//        }
//    }
//}