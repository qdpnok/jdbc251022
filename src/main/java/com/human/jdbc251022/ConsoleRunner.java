package com.human.jdbc251022;

import com.human.jdbc251022.dao.InOutMatMgmt;
import com.human.jdbc251022.dao.MemberDao;
import com.human.jdbc251022.dao.MgmtDao;
import com.human.jdbc251022.model.*;
import com.human.jdbc251022.model.Employee;
import com.human.jdbc251022.model.Material;
import com.human.jdbc251022.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

// 콘솔 입력 만들기 위한 파일
@Component // 의존성 주입, 자동 스캔
@RequiredArgsConstructor
public class ConsoleRunner implements CommandLineRunner {
    private final Scanner sc = new Scanner(System.in);
    private final MemberDao memberDao;
    private final MgmtDao mgmtDao;
    private final InOutMatMgmt inOutMatMgmt;
    @Override
    public void run(String... args) throws Exception {
        while (true) {
            System.out.println("===== 시스템/홈 =====");
            System.out.println("[1]관리 (Management)");
            System.out.println("[2]생산/작업 (Production/Work)");
            System.out.println("[3]입출고 및 자재 관리 (In/Outbound & Material Mgmt)");
            System.out.println("[4]품질 관리 (Quality Control - QC)");


            int sel = sc.nextInt();
            sc.nextLine();

            boolean isSuccess;

            switch (sel) {
                case 1: mgmt(); break;
                case 2: prodWork(); break;
                case 3: inOutMatMgmt(); break;
                case 4: qc(); break;
            }
        }
    }

    private Member regMember() {
        System.out.println("===== 회원 등록 =====");
        System.out.print("이메일: ");
        String email = sc.nextLine();
        System.out.print("비밀번호: ");
        String pwd = sc.nextLine();
        System.out.print("이름: ");
        String name = sc.nextLine();

        return new Member(email, pwd, name, null);
    }

    private Member editMember() {
        System.out.println("===== 회원 수정 =====");
        System.out.print("비밀번호: ");
        String pwd = sc.nextLine();

        return new Member(null, pwd, null, null);
    }
    private String unSign() {
        System.out.println("===== 회원 삭제 =====");
        System.out.print("이메일: ");

        return sc.nextLine();
    }

    // 관리 메뉴
    private void mgmt() {
        System.out.println("===== 관리 (Management) =====");
        System.out.println("[1]인사/조직 관리");
        System.out.println("[2]원자재/재고 관리");
        System.out.println("[3]성과 관리");


        int sel = sc.nextInt();
        sc.nextLine();

        switch(sel) {
            case 1: hrOrgMgmt(); break;
            case 2: matInvMgmt(); break;
            case 3: perfMgmt(); break;
        }
    }

    // 인사/조직 관리 메뉴
    private void hrOrgMgmt() {
        System.out.println("===== 인사/조직 관리 =====");
        System.out.println("[1] 사원 등록");
        System.out.println("[2] 사원 ID 조회");
        System.out.println("[3] 사원 전체 조회");
        System.out.println("[4] 사원 ID 삭제");
        System.out.println("[5] 부서 등록");
        System.out.println("[6] 부서 삭제");
        System.out.println("[7] 부서 담당자 지정");
        System.out.println("[8] 부서 담당자 조회");
        System.out.println("[0] 처음으로");
        System.out.print("선택 ▶ ");
        int sel = sc.nextInt();
        sc.nextLine();

        switch(sel) {
            case 1: insertEmployee(); break;
            case 2: getEmployee(); break;
            case 3: listEmployee(); break;
            case 4: deleteEmployee(); break;
            case 5: insertDept(); break;
            case 6: deleteDept(); break;
            case 7: assignManagerToDept(); break;
            case 8: getManager(); break;
            case 0: { return; }
            default : System.out.println("잘못된 선택입니다.");
        }
    }

    private int readInt() {
        return 0;
    }

    private void insertEmployee() {
        System.out.println("\n[사원 등록]");
        System.out.print("사원 ID : ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("사원 이름 : ");
        String name = sc.nextLine();

        System.out.print("직무 : ");
        String job = sc.nextLine();

        System.out.print("부서 ID : ");
        int deptId = sc.nextInt();
        sc.nextLine();

        System.out.print("연봉 : ");
        int sal = sc.nextInt();
        sc.nextLine();

        Employee emp = new Employee(id, name, job, deptId, sal, null);
        boolean ok = mgmtDao.insertEmployee(emp);
        System.out.println(ok ? "사원 등록 성공" : "사원 등록 실패");
    }

    private void getEmployee() {
        System.out.println("\n[사원 조회]");
        System.out.print("조회할 사원 ID: ");
        int id = readInt();
        Employee emp = mgmtDao.getEmployee(id);
        if (emp == null) {
            System.out.println("해당 ID의 사원이 없습니다.");
            return;
        }
        System.out.println(emp);
    }

    private void listEmployee() {
        System.out.println("\n[사원 전체 조회]");
        List<Employee> list = mgmtDao.listEmployee();
        if (list.isEmpty()) {
            System.out.println("조회되는 정보가 없습니다.");
            return;
        }
        list.forEach(System.out::println);
    }

    private void deleteEmployee() {
        System.out.println("\n[사원 삭제]");
        System.out.print("삭제할 사원 ID: ");
        int id = readInt();
        boolean ok = mgmtDao.deleteEmployee(id);
        System.out.println(ok ? "사원 삭제 성공" : "사원 삭제 실패");
    }

    private void insertDept() {
        System.out.println("\n[부서 등록]");
        System.out.print("부서 ID : ");
        int id = readInt();

        if (mgmtDao.getDepartment(id) != null) {
            System.out.println("이미 존재하는 부서 ID입니다.");
            return;
        }

        System.out.print("부서명: ");
        String name = sc.nextLine().trim();

        Department dept = new Department(id, name);
        boolean ok = mgmtDao.insertDept(dept);
        System.out.println(ok ? "부서 등록 성공" : "부서 등록 실패");
    }

    private void deleteDept() {
        System.out.println("\n[부서 삭제]");
        System.out.print("삭제할 부서 ID: ");
        int id = readInt();
        boolean ok = mgmtDao.deleteDept(id);
        System.out.println(ok ? "부서 삭제 성공" : "부서 삭제 실패(존재하지 않음)");
    }

    private void assignManagerToDept() {
        System.out.println("\n[부서 담당자(매니저) 지정]");
        System.out.print("부서 ID: ");
        int deptId = readInt();

        Department dept = mgmtDao.getDepartment(deptId);
        if (dept == null) {
            System.out.println("존재하지 않는 부서 ID입니다.");
            return;
        }

        System.out.print("담당자(사원) ID: ");
        int empId = readInt();

        Employee emp = mgmtDao.getEmployee(empId);
        if (emp == null) {
            System.out.println("존재하지 않는 사원 ID입니다.");
            return;
        }

        boolean ok = mgmtDao.assignManagerToDept(deptId, empId);
        System.out.println(ok ? "담당자 지정 완료" : "담당자 지정 실패");
    }

    private void getManager() {
        System.out.println("\n[부서 담당자 조회]");
        System.out.print("부서 ID: ");
        int deptId = readInt();

        Department dept = mgmtDao.getDepartment(deptId);
        if (dept == null) {
            System.out.println("존재하지 않는 부서 ID입니다.");
            return;
        }

        Employee mgr = mgmtDao.getManager(deptId);
        if (mgr == null) {
            System.out.println("지정된 담당자가 없습니다.");
            return;
        }
        System.out.println("부서: " + dept.getName() + " (ID: " + dept.getId() + ")");
        System.out.println("담당자: " + mgr);
    }







    // 원자재/재고 관리 메뉴
    private void matInvMgmt() {
        System.out.println("===== 원자재/재고 관리 =====");
        System.out.println("[1]원자재 관리");
        System.out.println("[2]재고 및 배치 관리");

        int sel = sc.nextInt();
        sc.nextLine();

        switch(sel) {
            case 1: matMgmt(); break;
            case 2: invBatchMgmt(); break;
        }
    }

    // 원자재 관리 메뉴
    private void matMgmt() {
        System.out.println("===== 원자재 관리 =====");
        System.out.println("[1]전체 조회");
        System.out.println("[2]이름으로 검색");
        System.out.println("[3]원자재 등록");
        System.out.println("[4]원자재 정보 수정");
        System.out.println("[5]원자재 삭제");

        int sel = sc.nextInt();
        sc.nextLine();

        switch(sel) {
            case 1: matList(); break;
            case 2: matNameSearch(); break;
            case 3: insertMaterial(); break;
            case 4: updateMaterial(); break;
            case 5: deleteMaterial(); break;

        }
    }

    // 원자재 관리 - 전체 조회
    private void matList() {
        List<Material> materialList = mgmtDao.matList();
        if(materialList.isEmpty()) {
            System.out.println("등록된 원자재가 없습니다.");
            return;
        }
        for(Material e : materialList) System.out.println(e);
    }

    // 원자재 관리 - 이름 조회
    private void matNameSearch() {
        System.out.print("검색할 원자재명 입력: ");

        List<Material> materialList = mgmtDao.matNameList(sc.nextLine());
        if(materialList.isEmpty()) {
            System.out.println("해당 이름으로 등록된 원자재가 없습니다.");
            return;
        }
        for(Material e : materialList) System.out.println(e);
    }

    // 원자재 관리 - 원자재 등록
    private void insertMaterial() {
        System.out.print("원자재 이름: ");
        String name = sc.nextLine();

        System.out.print("원자재 type: ");
        String type = sc.nextLine();

        boolean isSuccess = mgmtDao.insertMaterial(new Material(0, name, type));
        System.out.println("원자재 등록 " + (isSuccess ? "성공" : "실패"));
    }

    // 원자재 관리 - 원자재 정보 수정
    private void updateMaterial() {
        System.out.print("수정할 원자재 id: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("원자재 이름: ");
        String name = sc.nextLine();

        System.out.print("원자재 type: ");
        String type = sc.nextLine();

        boolean isSuccess = mgmtDao.updateMaterial(new Material(id, name, type));
        System.out.println("원자재 정보 수정 " + (isSuccess ? "성공" : "실패"));
    }

    // 원자재 관리 - 원자재 삭제
    private void deleteMaterial() {
        System.out.print("삭제할 원자재 id: ");
        int id = sc.nextInt();
        sc.nextLine();

        boolean isSuccess = mgmtDao.deleteMaterial(new Material(id, null, null));
        System.out.println("원자재 삭제 " + (isSuccess ? "성공" : "실패"));
    }


    // 재고 및 배치 관리 메뉴
    private void invBatchMgmt() {
        System.out.println("===== 재고 및 배치 관리 =====");
        System.out.println("[1]재고 조회");
        System.out.println("[2]배치 조회");
        System.out.println("[3]원자재 위치 조회");

        int sel = sc.nextInt();
        sc.nextLine();

        switch(sel) {
            case 1: invtList(); break;
            case 2: batchList(); break;
            case 3: matLocList(); break;
        }
    }

    // 재고 및 배치 관리 - 재고 조회
    private void invtList() {
        List<Inventory> invtList = mgmtDao.invtList();
        if(invtList.isEmpty()) {
            System.out.println("재고가 없습니다.");
            return;
        }
        for(Inventory e : invtList) System.out.println(e);
    }

    // 재고 및 배치 관리 - 배치 조회
    private void batchList() {
        List<Batch> batchList = mgmtDao.batchList();
        if(batchList.isEmpty()) {
            System.out.println("재고가 없습니다.");
            return;
        }
        for(Batch e : batchList) System.out.println(e);
    }

    // 재고 및 배치 관리 - 원자재 위치 조회
    private void matLocList() {
        System.out.print("위치를 조회할 원자재 이름: ");
        List<MaterialLoc> matLocList = mgmtDao.matLocList(sc.nextLine());
        if(matLocList.isEmpty()) {
            System.out.println("재고가 없습니다.");
            return;
        }
        for(MaterialLoc e : matLocList) System.out.println(e);
    }


    // 성과 관리 - 생산/품질 성과 조회 메뉴
    private void perfMgmt() {
        System.out.println("===== 성과 관리 =====");
        System.out.println("[1]QC 결과 조회");
        System.out.println("[2]생산성 조회");
        System.out.println("[2]효율 정보 모니터링");

        int sel = sc.nextInt();
        sc.nextLine();

        switch(sel) {
            case 1: break;
            case 2: break;
            case 3: break;
        }
    }

    // 생산/작업 메뉴
    private void prodWork() {
        System.out.println("===== 생산/작업 (Production/Work) =====");
        System.out.println("[1]작업 지시 및 배정");
        System.out.println("[2]작업 실적 입력");
        System.out.println("[3]생산 자재 투입");


        int sel = sc.nextInt();
        sc.nextLine();

        switch(sel) {
            case 1: break;
            case 2: break;
            case 3: break;
        }
    }

    // 입출고 및 자재 관리 메뉴
    private void inOutMatMgmt() {
        System.out.println("===== 입출고 및 자재 관리 (In/Outbound & Material Mgmt) =====");
        System.out.println("[1]입고 관리");
        System.out.println("[2]출고 관리");

        int sel = sc.nextInt();
        sc.nextLine();

        switch(sel) {
            case 1: matBatchIn(); break;
            case 2: matOut(); break;
        }
    }

    // 입고 관리 메뉴
    private void matBatchIn() {
        System.out.println("===== 입고 관리 =====");
        System.out.println("[1]입고 조회");
        System.out.println("[2]입고 등록");
        System.out.println("[3]배치 등록");


        int sel = sc.nextInt();
        sc.nextLine();

        switch(sel) {
            case 1: ibList(); break;
            case 2: insertInbound(); break;
            case 3: insertBatch(); break;
        }
    }

    // 입고 관리 - 입고 조회
    private void ibList() {
        List<Inbound> ibList = inOutMatMgmt.ibList();
        if(ibList.isEmpty()) {
            System.out.println("입고 기록이 없습니다.");
            return;
        }
        for(Inbound e : ibList) System.out.println(e);
    }

    // 입고 관리 - 입고 등록
    private void insertInbound() {
        System.out.print("원자재 번호: ");
        int matId = sc.nextInt();
        sc.nextLine();

        System.out.print("사원 번호: ");
        int empId = sc.nextInt();
        sc.nextLine();

        System.out.print("수량: ");
        int qty = sc.nextInt();
        sc.nextLine();

        boolean isSuccess = inOutMatMgmt.insertInbound(new Inbound(0, matId, empId, qty, null));
        System.out.println("입고 등록 " + (isSuccess ? "성공" : "실패"));
    }

    // 입고 관리 - 배치 등록
    private void insertBatch() {
        System.out.print("완제품 번호: ");
        int prodId = sc.nextInt();
        sc.nextLine();

        System.out.print("단위: ");
        int unit = sc.nextInt();
        sc.nextLine();

        System.out.print("상태: ");
        String status = sc.nextLine();
        sc.nextLine();

        System.out.print("비고: ");
        String note = sc.nextLine();

        boolean isSuccess = inOutMatMgmt.insertBatch(new Batch(0, prodId, unit, status, note));
        System.out.println("배치 등록 " + (isSuccess ? "성공" : "실패"));
    }

    // 출고 메뉴
    private void matOut() {
        System.out.println("===== 출고 관리 =====");
        System.out.println("[1]출고 조회");
        System.out.println("[2]출고 등록");

        int sel = sc.nextInt();
        sc.nextLine();

        switch(sel) {
            case 1: obList(); break;
            case 2: insertOutbound(); break;
        }
    }

    // 출고 관리 - 출고 조회
    private void obList() {
        List<Outbound> obList = inOutMatMgmt.obList();
        if(obList.isEmpty()) {
            System.out.println("출고 기록이 없습니다.");
            return;
        }
        for(Outbound e : obList) System.out.println(e);
    }

    // 출고 관리 - 출고 등록
    private void insertOutbound() {
        System.out.print("배치 번호: ");
        int batchId = sc.nextInt();
        sc.nextLine();

        System.out.print("출고 담당자: ");
        int empId = sc.nextInt();
        sc.nextLine();

        System.out.print("수량: ");
        int qty = sc.nextInt();
        sc.nextLine();

        boolean isSuccess = inOutMatMgmt.insertOutbound(new Outbound(0, batchId, empId, qty, null));
        System.out.println("출고 등록 " + (isSuccess ? "성공" : "실패"));
    }

    // 품질관리 메뉴
    private void qc() {
        System.out.println("===== 품질 관리 (Quality Control - QC) =====");
        System.out.println("[1]QC 테스트 관리");
        System.out.println("[2]샘플 관리");
        System.out.println("[3]검사 결과 등록/조회");


        int sel = sc.nextInt();
        sc.nextLine();

        switch(sel) {
            case 1: matBatchIn(); break;
            case 2: break;
            case 3: break;
        }
    }

}
