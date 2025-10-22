package com.human.jdbc251022;

import com.human.jdbc251022.dao.MemberDao;
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
    @Override
    public void run(String... args) throws Exception {
        while (true) {
            System.out.println("===== 콘솔 회원 관리 시스템 =====");
            System.out.println("[1]회원 등록");
            System.out.println("[2]회원 목록 조회");
            System.out.println("[3]회원 정보 수정");
            System.out.println("[4]회원 삭제");

            int sel = sc.nextInt();
            sc.nextLine();

            boolean isSuccess;

            switch (sel) {
                case 1:
                    isSuccess = memberDao.insertMember(regMember());
                    System.out.println("회원 가입 " + (isSuccess ? "성공" : "실패"));
                    break;
                case 2:
                    List<Member> memberList = memberDao.memberList();
                    System.out.println("===== 회원 목록 조회 =====");
                    for(Member member : memberList) System.out.println(member);
                    break;
                case 3:
                    isSuccess = memberDao.updateMember(editMember());
                    System.out.println("정보 수정 " + (isSuccess ? "성공" : "실패"));
                    break;
                case 4:
                    isSuccess = memberDao.deleteMember(unSign());
                    System.out.println("회원 삭제 " + (isSuccess ? "성공" : "실패"));
                    break;
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
}
