package org.example;

import java.util.Scanner;

public class App {
    Scanner scanner = new Scanner(System.in);
    Action action = new Action();

    void run() {
        System.out.println("=============명언 앱====================");
        boolean running = true; // 실행 중 상태를 명시적으로 설정
        while (running) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine().trim();
            switch (cmd.split("\\?")[0]) {
                case "등록":
                    action.regist();
                    break;
                case "목록":
                    action.list();
                    break;
                case "수정":
                    action.modify(cmd);
                    break;
                case "저장":
                    action.save();
                    break;
                case "검색":
                    action.search();
                    break;
                case "삭제":
                    action.delete(cmd);
                    break;
                case "종료":
                    action.end();
                    running = false;
                    break;
                default:
                    action.notFound(cmd);
                    break;
            }
        }
    }
}
