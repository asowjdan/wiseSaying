package org.example;

import java.util.Scanner;

//입출력 파트
public class Action {
    static Scanner scanner = new Scanner(System.in);
    static Logic logic = new Logic();

    void regist() {
        System.out.print("내용) ");
        String content = scanner.nextLine();
        System.out.print("작성자) ");
        String author = scanner.nextLine();
        logic.write(content, author);
    }

    void list() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        WiseSaying[] Lists = logic.findForList();
        for (int i = Lists.length-1; i >= 0; i--) {
            System.out.println(Lists[i].id + " / " + Lists[i].author + " / " + Lists[i].content);
        }
    }

    void modify() {
        System.out.println("id를 입력해 주세요.");
        int id = scanner.nextInt();
        scanner.nextLine();
    }

    static void notFound(String cmd) {
        if (cmd.isEmpty() || !cmd.startsWith("삭제?")) {
            System.out.println("존재하지 않는 명령어입니다.");
            return;
        }

        String[] split = cmd.split("\\?");
        if (split.length != 2 || split[1].isEmpty() || !split[1].startsWith("id=")) {
            System.out.println("명령어를 바르게 작성해 주세요. ex) 삭제?id=1");
            return;
        }

        Action.delete(split[1]);
    }


    static void delete(String commend) {
        String[] cmdSplit = commend.split("=");
        int id = Integer.parseInt(cmdSplit[1]);
        logic.delete(id);
    }

    void save() {
        System.out.println("저장함");
    }
    void search() {
        System.out.println("검색함");
    }
    void end(){
        System.out.println("종료합니다.");
        System.exit(0);
    }
}
