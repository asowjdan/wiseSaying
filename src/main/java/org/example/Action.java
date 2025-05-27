package org.example;

import java.util.Scanner;

//입출력 파트
public class Action {
    static Scanner scanner = new Scanner(System.in);
    Logic logic = new Logic();

    public Action() {
        logic.loadLastSayings();
    }

    void regist() {
        System.out.print("내용) ");
        String content = scanner.nextLine();
        System.out.print("작성자) ");
        String author = scanner.nextLine();
        System.out.println(logic.write(content, author));
    }

    void list() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        for (WiseSaying wiseSaying : logic.findForList()) {
            System.out.println(wiseSaying.id + " / " + wiseSaying.author + " / " + wiseSaying.content);
        }
    }

    void modify(String command) {
        String[] split = command.split("\\?");
        if (split.length != 2 || split[1].isEmpty() || !split[1].startsWith("id=")) {
            System.out.println("명령어를 바르게 작성해 주세요. ex) 수정?id=1");
            return;
        }

        int id = Integer.parseInt(split[1].split("=")[1]);
        WiseSaying modifying = logic.findForModify(id);

        if (modifying == null) {
            System.out.println("해당하는 명언이 없습니다.");
            return;
        }

        System.out.println("변경 전 내용) " + modifying.content);
        System.out.print("내용) ");
        String content = scanner.nextLine();
        System.out.println("변경 전 작성자) " + modifying.author);
        System.out.print("작성자) ");
        String author = scanner.nextLine();

        System.out.println(logic.modify(id, content, author));
    }


    void notFound(String cmd) {
        if (cmd.isEmpty()) {
            System.out.println("존재하지 않는 명령어입니다.");
        }
    }

    void delete(String commend) {
        String[] split = commend.split("\\?");
        if (split.length != 2 || split[1].isEmpty() || !split[1].startsWith("id=")) {
            System.out.println("명령어를 바르게 작성해 주세요. ex) 삭제?id=1");
            return;
        }

        String[] cmdSplit = commend.split("=");
        int id = Integer.parseInt(cmdSplit[1]);

        System.out.println(logic.delete(id));
    }

    void save() {
        System.out.println(logic.save());
    }

    void search() {
        System.out.println("검색함");
    }

    void end(){
        System.out.println("종료합니다.");
        System.exit(0);
    }
}
