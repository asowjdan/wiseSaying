package com.back.domain.wiseSaying.service;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;

import java.util.ArrayList;

public class Logic {
    WiseSayingRepository repository = new WiseSayingRepository();
    ArrayList<WiseSaying> wiseSayings = repository.loadData();

    int lastId = repository.loadLastId();

    public String write(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying(++lastId, content, author);

        wiseSayings.add(wiseSaying);

        repository.jsonWrite(wiseSaying);
        repository.plusLastId(lastId);
        return "명언을 저장했습니다.";

    }

    public String delete(int id) {
        return wiseSayings
                .stream()
                .filter(wiseSaying -> wiseSaying.getId() == id)
                .findFirst()
                .map(wiseSaying -> {
                    wiseSayings.remove(wiseSaying);
                    return repository.fileDelete(id);
                })
                .orElse("해당하는 명언이 없습니다.");
    }

    public String findForList(){
        ArrayList<WiseSaying> list = wiseSayings;
        StringBuilder allContent = new StringBuilder();
        for (WiseSaying wiseSaying : list) {
            int id = wiseSaying.getId();
            String author = wiseSaying.getAuthor();
            String content = wiseSaying.getContent();
            allContent.append(id).append(" / ").append(author).append(" / ").append(content).append('\n');
        }
        return allContent.toString();
    }

    public String modify(int id, String content, String author) {
        WiseSaying wiseSaying = findForModify(id);

        if (wiseSaying == null) {
            return "변경할 명언이 존재하지 않습니다.";
        }

        wiseSaying.setAuthor(author);
        wiseSaying.setContent(content);

        repository.jsonWrite(wiseSaying);
        return "변경되었습니다.";
    }

    public WiseSaying findForModify(int id){
        return wiseSayings
                .stream()
                .filter(wiseSaying -> wiseSaying.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public String save(){
        if(wiseSayings.isEmpty()||lastId==0) return "저장할 명언이 존재하지 않습니다.";
        System.out.println(findForList());
        return "저장되었습니다.";
    }
}