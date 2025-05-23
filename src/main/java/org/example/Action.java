package org.example;


import java.util.Scanner;

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
        WiseSaying[] forListWiseSayings = logic.findForList();

        for (WiseSaying wiseSaying : forListWiseSayings) {
            System.out.printf("%d / %s / %s\n", wiseSaying.id, wiseSaying.author, wiseSaying.content);
        }
    }

    void modify() {
        System.out.println("수정함");
    }

    static void notFound(String cmd) {
        if(cmd.isEmpty()){
            System.out.println("존재하지 않는 명령어입니다.");
        }else {
            String[] split = cmd.split("\\?");
            if(!split[0].equals("삭제") || split.length != 2){
                System.out.println("존재하지 않는 명령어입니다.");
            }else if(split[1].isEmpty()){
                System.out.println("명령어를 바르게 작성해 주세요");
            }else{
                Action.delete(split[1]);
            }
        }
    }

    static void delete(String commend) {
        String[] cmdSplit = commend.split("=");
        if(!cmdSplit[0].equals("id")){
            System.out.println("존재하지 않는 명령어입니다.");
        }else{
            int id = Integer.parseInt(cmdSplit[1]);
            logic.delete(id);
        }
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
