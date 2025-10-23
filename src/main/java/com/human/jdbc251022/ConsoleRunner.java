package com.human.jdbc251022;

import com.human.jdbc251022.dao.MemberDao;
import com.human.jdbc251022.dao.MgmtDao;
import com.human.jdbc251022.model.*;
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
        System.out.println("[1]사원 정보 관리");
        System.out.println("[2]부서 및 공정 관리");

        int sel = sc.nextInt();
        sc.nextLine();

        switch(sel) {
            case 1: break;
            case 2: break;
        }
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
        System.out.print("원자재 id: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("원자재 이름: ");
        String name = sc.nextLine();

        System.out.print("원자재 type: ");
        String type = sc.nextLine();

        boolean isSuccess = mgmtDao.insertMaterial(new Material(id, name, type));
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

    private void invtList() {
        List<Inventory> invtList = mgmtDao.invtList();
        if(invtList.isEmpty()) {
            System.out.println("재고가 없습니다.");
            return;
        }
        for(Inventory e : invtList) System.out.println(e);
    }

    private void batchList() {
        List<Batch> batchList = mgmtDao.batchList();
        if(batchList.isEmpty()) {
            System.out.println("재고가 없습니다.");
            return;
        }
        for(Batch e : batchList) System.out.println(e);
    }

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
        System.out.println("[1]원자재 입고 관리");
        System.out.println("[2]원자재 출고 관리");
        System.out.println("[3]완제품 출고 관리");


        int sel = sc.nextInt();
        sc.nextLine();

        switch(sel) {
            case 1: matBatchIn(); break;
            case 2: break;
            case 3: break;
        }
    }

    // 원자재 입고 관리 메뉴
    private void matBatchIn() {
        System.out.println("===== 원자재 입고 관리 =====");
        System.out.println("[1]원자재 입고 조회");
        System.out.println("[2]원자재 입고 등록");
        System.out.println("[3]배치 등록");


        int sel = sc.nextInt();
        sc.nextLine();

        switch(sel) {
            case 1: break;
            case 2: break;
            case 3: break;
        }
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
