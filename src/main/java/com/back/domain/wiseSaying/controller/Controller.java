package com.back.domain.wiseSaying.controller;
import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.service.Logic;

import java.util.Scanner;

public class Controller {
    private final Scanner scanner = new Scanner(System.in);
    private final Logic logic = new Logic();

    public boolean runCommand(String cmd) {
        String command = cmd.split("\\?")[0];
        switch (command) {
            case "등록":
                regist();
                break;
            case "목록":
                list();
                break;
            case "수정":
                modify(cmd);
                break;
            case "삭제":
                delete(cmd);
                break;
            case "저장":
                save();
                break;
            case "검색":
                search();
                break;
            case "종료":
                end();
                return false;
            default:
                notFound(cmd);
                break;
        }
        return true;
    }

    private void regist() {
        System.out.print("내용) ");
        String content = scanner.nextLine();
        System.out.print("작성자) ");
        String author = scanner.nextLine();
        System.out.println(logic.write(content, author));
    }

    private void list() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        System.out.println(logic.findForList());
    }

    private void modify(String command) {
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

        System.out.println("변경 전 내용) " + modifying.getContent());
        System.out.print("내용) ");
        String content = scanner.nextLine();
        System.out.println("변경 전 작성자) " + modifying.getAuthor());
        System.out.print("작성자) ");
        String author = scanner.nextLine();
        System.out.println(logic.modify(id, content, author));
    }

    private void delete(String command) {
        String[] split = command.split("\\?");
        if (split.length != 2 || split[1].isEmpty() || !split[1].startsWith("id=")) {
            System.out.println("명령어를 바르게 작성해 주세요. ex) 삭제?id=1");
            return;
        }

        int id = Integer.parseInt(command.split("=")[1]);
        System.out.println(logic.delete(id));
    }

    private void save() {
        System.out.println(logic.save());
    }

    private void search() {
        System.out.println("검색 기능 실행");
    }

    private void end() {
        System.out.println("종료합니다.");
        System.exit(0);
    }

    private void notFound(String cmd) {
        if (cmd.isEmpty()) {
            System.out.println("명령어를 입력해 주세요.");
            return;
        }
        System.out.println("존재하지 않는 명령어입니다.");
    }
}
