package com.human.jdbc251022.dao;

import com.human.jdbc251022.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository // Spring container Bean 등록
@Slf4j
@RequiredArgsConstructor
public class MgmtDao {
    private final JdbcTemplate jdbcTemplate;

    // 사원 전체 조회
    public List<Employee> empList(Employee employee) {
        String query = "SELECT * FROM employee";
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

    // 사원 조회 (단건)
    public Employee getEmployee(String empId) {
        String query = "SELECT * FROM Employee WHERE EMP_ID = ?";
        try {
            return jdbcTemplate.queryForObject(query,
                    (rs, rowNum) -> new Employee(
                            rs.getString("emp_id"),
                            rs.getString("emp_name"),
                            rs.getString("job"),
                            rs.getString("dept_id"),
                            rs.getInt("sal")));
        } catch (Exception e) {
            log.error("사원 조회 실패: {}", e.getMessage());
            return null;
        }
    }
    // 사원 정보 등록
    public boolean insertEmployee(Employee employee) {
        int result = 0;
        String query = "INSERT INTO Employee (EMP_ID, EMP_NAME, JOB, DEPT_ID, SAL) VALUES(?, ?, ?, ?, ?)";
        try {
            // 성공하면 성공한 갯수가 return. 1개 성공하면 1, 2개 성공하면 2
            // query 뒤에는 위쪽 ?에 들어갈 것들.
            result = jdbcTemplate.update(query, employee.getId(),
                    employee.getName(), employee.getJob(), employee.getDeptId(), employee.getSal());

        } catch (Exception e) {
            // 로그 메시지. lombok에서 제공함.
            log.error("사원 정보 추가 실패 : {}", e.getMessage());
        }
        return result > 0;
    }

    // 사원 정보 수정
    public boolean updateEmployee(Employee employee) {
        int result = 0;
        String query = "UPDATE Employee SET pwd = ?";
        try {
            result = jdbcTemplate.update(query, employee.getId(),
                    employee.getName(), employee.getJob(), employee.getDeptId(), employee.getSal());

        } catch (Exception e) {
            // 로그 메시지. lombok에서 제공함.
            log.error("사원 정보 수정 실패 : {}", e.getMessage());
        }
        return result > 0;
    }

    // 사원 삭제
    public boolean deleteEmployee(String empid) {
        int result = 0;
        String query = "DELETE FROM Employee WHERE empid = ?";
        try {
            result = jdbcTemplate.update(query, empid);

        } catch (Exception e) {
            // 로그 메시지. lombok에서 제공함.
            log.error("사원 삭제 실패 : {}", e.getMessage());
        }
        return result > 0;
    }

    // 부서 등록
    public boolean insertDept(Department dept) {
        String sql = "INSERT INTO Department (DEPT_ID, DEPT_NAME, TYPE, FACTORY_NO, MANAGER, CREATE_DATE) VALUES (?, ?, ?, ?, ?, ?)";
        int result = 0;
        try {
            result = jdbcTemplate.update(sql,
                    dept.getId(), dept.getName(), dept.getType(),
                    dept.getFactoryNO(), dept.getManager(), dept.getCreateDate());
        } catch (Exception e) {
            log.error("부서 등록 실패 : {}", e.getMessage());
        }
        return result > 0;
    }

    // 부서 삭제
    public boolean deleteDept(int deptId) {
        String query = "DELETE FROM Department WHERE DEPT_ID = ?";
        try {
            int result = jdbcTemplate.update(query, deptId);
            return result > 0;
        } catch (Exception e) {
            log.error("부서 삭제 실패 : {}", e.getMessage());
            return false;
        }
    }

    // 소속 부서 조회
    public Department getDepartmentByEmployeeId(int empId) {
        String query = """
        SELECT d.*
        FROM Department d
        JOIN Employee e ON d.DEPT_ID = e.DEPT_ID
        WHERE e.EMP_ID = ?,""";
        try {
            return jdbcTemplate.queryForObject(query, new DeptRowMapper(), empId);
        } catch (Exception e) {
            log.error("소속 부서 조회 실패 : {}", e.getMessage());
            return null;
        }
    }

    // 공정 번호로 부서 조회
    public List<Department> getDeptProcessNo(int processNo) {
        String query = "SELECT * FROM Department WHERE PROCESS_NO = ?";
        try {
            return jdbcTemplate.query(query, new DeptRowMapper(), processNo);
        } catch (Exception e) {
            log.error("공정번호로 부서 조회 실패 : {}", e.getMessage());
            return List.of();
        }
    }
    private static class DeptRowMapper implements RowMapper<Department> {
        @Override
        public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Department(
                    rs.getInt("dept_id"),
                    rs.getString("dept_name"),
                    rs.getString("type"),
                    rs.getInt("factory_no"),
                    rs.getString("manager"),
                    rs.getTimestamp("create_date").toLocalDateTime()
            );
        }
    }

    // 부서별 담당자 지정
    public boolean assignManagerToDept(int deptId, String manager) {
        String query = "UPDATE Department SET MANAGER = ? WHERE DEPT_ID = ?";
        try {
            int result = jdbcTemplate.update(query, manager, deptId);
            return result > 0;
        } catch (Exception e) {
            log.error("부서 담당자 지정 실패 : {}", e.getMessage());
            return false;
        }
    }

    // 부서별 담당자 조회
    public String getManagerByDept(int deptId) {
        String query = "SELECT MANAGER FROM Department WHERE DEPT_ID = ?";
        try {
            return jdbcTemplate.queryForObject(query, String.class, deptId);
        } catch (Exception e) {
            log.error("부서별 담당자 조회 실패 : {}", e.getMessage());
            return null;
        }
    }

    // 생산일 등록
    public boolean insertProduction(Production production) {
        String query = "INSERT INTO Production (PROCESS_NO, PRODUCT_NAME, QUANTITY, PRODUCTION_DATE, FACTORY_NO) VALUES (?, ?, ?, ?, ?)";
        try {
            int result = jdbcTemplate.update(query,
                    production.getProcessNo(),
                    production.getProductName(),
                    production.getQuantity(),
                    production.getProductionDate(),
                    production.getFactoryNo());
            return result > 0;
        } catch (Exception e) {
            log.error("생산일 등록 실패 : {}", e.getMessage());
            return false;
        }
    }
    // 생산일 수정
    public boolean updateProductionDate(int productionId, LocalDateTime newProductionDate) {
        String query = "UPDATE Production SET PRODUCTION_DATE = ? WHERE PRODUCTION_ID = ?";
        try {
            int result = jdbcTemplate.update(query, newProductionDate, productionId);
            return result > 0; // 수정된 행이 있으면 true 반환
        } catch (Exception e) {
            log.error("생산일 수정 실패 : {}", e.getMessage());
            return false;
        }
    }
    // 생산일 조회
    public List<LocalDateTime> getAllProductionDates() {
        String query = "SELECT PRODUCTION_DATE FROM Production ORDER BY PRODUCTION_DATE DESC";
        try {
            return jdbcTemplate.query(query, (rs, rowNum) ->
                    rs.getTimestamp("production_date").toLocalDateTime());
        } catch (Exception e) {
            log.error("생산일자 조회 실패 : {}", e.getMessage());
            return List.of();
        }
    }

    // 작업 등록
    public boolean insertWork(Work work) {
        String sql = """
        INSERT INTO Work 
        (PROCESS_NO, WORK_ORDER, ASSIGNED_TO, START_TIME, END_TIME, RESULT, 
         BATCH_INPUT, MATERIAL_INPUT, INPUT_QUANTITY)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) """;
        try {
            int result = jdbcTemplate.update(sql,
                    work.getWorkId(),          // 작업 번호, ID
                    work.getProcessNo(),       // 어떤 공정에 속한 작업인가
                    work.getWorkOrder(),       // 작업 지시 내용
                    work.getAssignedTo(),      // 담당자(작업 수행 하는 사람)
                    work.getStartTime(),       // 작업 시작 시간
                    work.getEndTime(),         // 작업 종료 시간
                    work.getResult(),          // 작업 결과 상태 (진행중 or 완료)
                    work.getBatchInput(),      // 투입된 자재 배치 정보
                    work.getMaterialInput(),   // 투입된 자재명
                    work.getInputQuantity());  // 자재 투입 수량
            return result > 0; // 성공 시 true
        } catch (Exception e) {
            log.error("작업 등록 실패 : {}", e.getMessage());
            return false;
        }
    }

    // 작업 목록 조회
    public List<Work> getAllWorks() {
        String sql = "SELECT * FROM Work ORDER BY WORK_ID DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Work work = new Work();
            work.setWorkId(rs.getInt("WORK_ID"));
            work.setProcessNo(rs.getInt("PROCESS_NO"));
            work.setWorkOrder(rs.getString("WORK_ORDER"));
            work.setAssignedTo(rs.getString("ASSIGNED_TO"));
            work.setStartTime(rs.getTimestamp("START_TIME") != null ?
                    rs.getTimestamp("START_TIME").toLocalDateTime() : null);
            work.setEndTime(rs.getTimestamp("END_TIME") != null ?
                    rs.getTimestamp("END_TIME").toLocalDateTime() : null);
            work.setResult(rs.getString("RESULT"));
            work.setBatchInput(rs.getString("BATCH_INPUT"));
            work.setMaterialInput(rs.getString("MATERIAL_INPUT"));
            work.setInputQuantity(rs.getInt("INPUT_QUANTITY"));
            return work;
        });
    }

    // 작업 지시 및 배정
    public boolean insertWorkOrder(Work work) {
        String sql = """
        INSERT INTO Work (PROCESS_NO, WORK_ORDER, ASSIGNED_TO, START_TIME, RESULT)
        VALUES (?, ?, ?, ?, ?)
    """;
        try {
            int result = jdbcTemplate.update(sql,
                    work.getProcessNo(),
                    work.getWorkOrder(),
                    work.getAssignedTo(),
                    work.getStartTime(),      // 작업 시작 시간
                    "지시됨"                   // 기본 상태 : 지시됨
            );
            return result > 0;
        } catch (Exception e) {
            log.error("작업 지시 및 배정 실패 : {}", e.getMessage());
            return false;
        }
    }

    // 작업 실적 수정 (작업 시작 및 종료 시간)
    public boolean updateWorkResult(int workId, String result, LocalDateTime endTime) {
        String sql = "UPDATE Work SET RESULT = ?, END_TIME = ? WHERE WORK_ID = ?";
        try {
            int updated = jdbcTemplate.update(sql, result, endTime, workId);
            return updated > 0; // 수정되면 = true
        } catch (Exception e) {
            log.error("작업 실적 수정 실패 : {}", e.getMessage());
            return false;
        }
    }

    // 작업 삭제
    public boolean deleteWork(int workId) {
        String sql = "DELETE FROM Work WHERE WORK_ID = ?";
        try {
            int r = jdbcTemplate.update(sql, workId);
            return r > 0;
        } catch (Exception e) {
            log.error("작업 삭제 실패 : {}", e.getMessage());
            return false;
        }
    }

    // 공정별 자재 투입 내역 조회
    public List<MaterialInput> getMaterialInputsByProcess(int processNo) {
        String sql = "SELECT * FROM MaterialInput WHERE PROCESS_NO = ? ORDER BY INPUT_DATE DESC";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                MaterialInput material = new MaterialInput();
                material.setMaterialId(rs.getInt("MATERIAL_ID"));
                material.setProcessNo(rs.getInt("PROCESS_NO"));
                material.setMaterialName(rs.getString("MATERIAL_NAME"));
                material.setInputQuantity(rs.getInt("INPUT_QUANTITY"));
                material.setInputDate(rs.getTimestamp("INPUT_DATE").toLocalDateTime());
                material.setFactoryNo(rs.getInt("FACTORY_NO"));
                return material;
            }, processNo);
        } catch (Exception e) {
            log.error("공정별 자재 투입 내역 조회 실패 : {}", e.getMessage());
            return List.of();
        }
    }

    // 원자재별 투입 내역 조회
    public List<MaterialInput> getMaterialInputsByName(String materialName) {
        String sql = "SELECT * FROM MaterialInput WHERE MATERIAL_NAME = ? ORDER BY INPUT_DATE DESC";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                MaterialInput material = new MaterialInput();
                material.setMaterialId(rs.getInt("MATERIAL_ID"));
                material.setProcessNo(rs.getInt("PROCESS_NO"));
                material.setMaterialName(rs.getString("MATERIAL_NAME"));
                material.setInputQuantity(rs.getInt("INPUT_QUANTITY"));
                material.setInputDate(rs.getTimestamp("INPUT_DATE").toLocalDateTime());
                material.setFactoryNo(rs.getInt("FACTORY_NO"));
                return material;
            }, materialName);
        } catch (Exception e) {
            log.error("원자재별 투입 내역 조회 실패 : {}", e.getMessage());
            return List.of();
        }
    }

    // 원자재별 투입 수량 조회
    public int getTotalInputQuantityByMaterial(String materialName) {
        String sql = "SELECT COALESCE(SUM(INPUT_QUANTITY), 0) FROM MaterialInput WHERE MATERIAL_NAME = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, materialName);
        } catch (Exception e) {
            log.error("원자재별 총 투입 수량 조회 실패 : {}", e.getMessage());
            return 0;
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

    // 재고 조회 - 전체
    public List<Inventory> invtList() {
        String query = "SELECT * FROM inventory";
        return jdbcTemplate.query(query, new MgmtDao.InvtRowMapper());
    }

    // 재고 매퍼
    private static class InvtRowMapper implements RowMapper<Inventory> {

        // ResultSet -> DB에서 넘어온 결과, rowNum -> 행 번호
        // 행 번호로 자동으로 돌아서 Member에 넣어준다
        @Override
        public Inventory mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Inventory(
                    rs.getInt("invt_id"),
                    rs.getInt("material_id"),
                    rs.getInt("ib_id"),
                    rs.getTimestamp("exp_date").toLocalDateTime(),
                    rs.getInt("qty"),
                    rs.getString("loc")
            );
        }
    }

    // 배치 조회 - 전체
    public List<Batch> batchList() {
        String query = "SELECT * FROM batch";
        return jdbcTemplate.query(query, new MgmtDao.BatchRowMapper());
    }

    // 배치 매퍼
    private static class BatchRowMapper implements RowMapper<Batch> {

        // ResultSet -> DB에서 넘어온 결과, rowNum -> 행 번호
        // 행 번호로 자동으로 돌아서 Member에 넣어준다
        @Override
        public Batch mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Batch(
                    rs.getInt("batch_id"),
                    rs.getInt("product_id"),
                    rs.getInt("unit"),
                    rs.getString("status"),
                    rs.getString("note")
            );
        }
    }

    // 원자재 이름으로 위치 조회
    public List<MaterialLoc> matLocList(String name) {
        String query = "SELECT invt_id, material_name, qty, loc FROM inventory i JOIN material m ON i.material_id = m.material_id WHERE material_name = ?";
        return jdbcTemplate.query(query, new MgmtDao.MatLocRowMapper(), name);
    }

    // 원자재 위치 매퍼
    private static class MatLocRowMapper implements RowMapper<MaterialLoc> {

        // ResultSet -> DB에서 넘어온 결과, rowNum -> 행 번호
        // 행 번호로 자동으로 돌아서 Member에 넣어준다
        @Override
        public MaterialLoc mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new MaterialLoc(
                    rs.getInt("invt_id"),
                    rs.getString("material_name"),
                    rs.getInt("qty"),
                    rs.getString("loc")
            );
        }
    }

    // QC 결과 조회
    public List<QCResult> qcResultList() {
        String query = "SELECT QC_ID, QC.SAMPLE_ID, PASS_FAIL, BATCH_ID, RESULT_ID FROM QC JOIN SAMPLE S ON QC.SAMPLE_ID = S.SAMPLE_ID;";
        return jdbcTemplate.query(query, new MgmtDao.QCResultRowMapper());
    }

    // QC 결과 매핑
    private static class QCResultRowMapper implements RowMapper<QCResult> {

        // ResultSet -> DB에서 넘어온 결과, rowNum -> 행 번호
        // 행 번호로 자동으로 돌아서 Member에 넣어준다
        @Override
        public QCResult mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new QCResult(
                    rs.getInt("QC_ID"),
                    rs.getInt("QC.SAMPLE_ID"),
                    rs.getString("PASS_FAIL"),
                    rs.getInt("BATCH_ID"),
                    rs.getInt("RESULT_ID")
            );
        }
    }
}
