package org.example;

public class Logic {

    WiseSaying[] wiseSayings = new WiseSaying[100];
    int wiseSayingsLastIndex = -1;
    int lastId = 0;

    WiseSaying write(String content, String author){
        WiseSaying wiseSaying = new WiseSaying();
        wiseSaying.id = ++lastId;
        wiseSaying.content = content;
        wiseSaying.author = author;

        wiseSayings[++wiseSayingsLastIndex] = wiseSaying;

        return wiseSaying;
    }

    int getSize(){
        return wiseSayingsLastIndex + 1;
    }

    WiseSaying[] findForList() {
        WiseSaying[] forListWiseSayings = new WiseSaying[getSize()];

        int forListWiseSayingsIndex = -1;

        for (int i = wiseSayingsLastIndex; i >= 0; i--) {
            forListWiseSayings[++forListWiseSayingsIndex] = wiseSayings[i];
        }

        return forListWiseSayings;
    }

    void delete(int id){
        if(id < 1 || id > getSize()){
            System.out.println("존재하지 않는 명언입니다.");
        }else {
            for (int i = 0; i <= wiseSayingsLastIndex; i++) {
                if (wiseSayings[i].id == id) {
                    for (int j = i; j <= wiseSayingsLastIndex; j++) {
                        wiseSayings[j] = wiseSayings[j + 1];
                    }
                    wiseSayingsLastIndex--;
                    break;
                }
            }
            System.out.println("삭제되었습니다.");
        }
    }
}
