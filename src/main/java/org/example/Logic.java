package org.example;

import java.util.ArrayList;

//데이터 처리 파트
public class Logic {

    ArrayList<WiseSaying> wiseSayings = new ArrayList<>();
    int lastId = 0;

    void write(String content, String author){
        WiseSaying wiseSaying = new WiseSaying();

        wiseSaying.id = ++lastId;
        wiseSaying.content = content;
        wiseSaying.author = author;

        wiseSayings.add(wiseSaying);
    }

    WiseSaying[] findForList(){
        WiseSaying[] lists = new WiseSaying[wiseSayings.size()];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = wiseSayings.get(i);
        }

        return lists;
    }

    void delete(int id) {
        for (int i = 0; i < wiseSayings.size(); i++) {
            if (wiseSayings.get(i).id == id) {
                wiseSayings.remove(i);
                System.out.println("삭제되었습니다.");
                return;
            }
        }
        System.out.println("삭제할 명언이 존재하지 않습니다.");
    }

    void modify(int id){
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        for (WiseSaying wiseSaying : wiseSayings)
            if (wiseSaying.id == id) {
                System.out.println("명언(기존) : " + wiseSaying.content);
                System.out.print("명언) ");
                String content = scanner.nextLine();
                System.out.println("작성자(기존) : " + wiseSaying.author);
                System.out.print("작성자) ");
                String author = scanner.nextLine();
                wiseSaying.content = content;
                wiseSaying.author = author;
                System.out.println("변경되었습니다.");
                return;
            }
        System.out.println("변경할 명언이 존재하지 않습니다.");
    }
}
