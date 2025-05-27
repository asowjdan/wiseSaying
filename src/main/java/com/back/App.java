package com.back;

import com.back.domain.wiseSaying.controller.Controller;

import java.util.Scanner;

public class App {
    Scanner scanner = new Scanner(System.in);
    Controller controller = new Controller();

    void run() {
        System.out.println("============= 명언 앱 =============");
        boolean running = true; // 명령 실행 상태를 명시적으로 설정
        while (running) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine().trim();
            running = controller.runCommand(cmd); // 명령어 처리
        }
    }
}

