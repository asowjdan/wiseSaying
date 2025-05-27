package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

//데이터 처리 파트
public class Logic {
    ArrayList<WiseSaying> wiseSayings = new ArrayList<>();

    int lastId = loadLastId();

    int loadLastId() {
        File lastIdFile = new File("src/main/resources/db/wiseSaying/LastId.txt");

        if (!lastIdFile.exists()) {
            return 0;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(lastIdFile))) {
            String lastIdStr = br.readLine();
            return Integer.parseInt(lastIdStr.trim());
        } catch (IOException | NumberFormatException e) {
            return -1;
        }
    }


    void loadLastSayings() {
        File directory = new File("src/main/resources/db/wiseSaying/");

        if (!directory.exists() || directory.list() == null) {
            return;
        }

        for (String fileName : Objects.requireNonNull(directory.list())) {
            if (!fileName.equals("LastId.txt")) {
                File file = new File(directory, fileName);
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    StringBuilder jsonData = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        jsonData.append(line.trim());
                    }
                    String jsonString = jsonData.toString();

                    int id = Integer.parseInt(jsonString.split("\"id\"")[1]
                            .split(":")[1]
                            .split(",")[0]
                            .trim());

                    String author = jsonString.split("\"author\"")[1]
                            .split(":")[1]
                            .split(",")[0]
                            .trim()
                            .replace("\"", "");

                    String content = jsonString.split("\"content\"")[1]
                            .split(":")[1]
                            .trim()
                            .replace("\"", "")
                            .replace("}", "");

                    WiseSaying dbList = new WiseSaying();
                    dbList.id = id;
                    dbList.content = content;
                    dbList.author = author;
                    wiseSayings.add(dbList);
                } catch (IOException e) {
                    System.out.println("명언 파일 로드에 실패했습니다." + e.getMessage());
                }
            }
        }
    }


    String write(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying();

        wiseSaying.id = ++lastId;
        wiseSaying.content = content;
        wiseSaying.author = author;

        wiseSayings.add(wiseSaying);

        File directory = new File("src/main/resources/db/wiseSaying/");
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                System.err.println("디렉토리를 생성하는 데 실패했습니다: " + directory.getAbsolutePath());
            }
        }

        
        File file = new File(directory, wiseSaying.id + ".json");
        File lastIdFile = new File(directory, "LastId.txt");

        try (FileWriter fw = new FileWriter(file); FileWriter li = new FileWriter(lastIdFile)) {
            fw.write("{\n  \"id\": " + wiseSaying.id + ",\n  \"author\": \"" + wiseSaying.author + "\",\n  \"content\": \"" + wiseSaying.content + "\"\n}\n");
            li.write(String.valueOf(lastId));

            return "명언을 저장했습니다.";
        } catch (IOException e) {
            return "명언 저장에 실패했습니다." + e.getMessage();
        }
    }

    String delete(int id) {
        ArrayList<WiseSaying> list = wiseSayings;
        return wiseSayings
                .stream()
                .filter(wiseSaying -> wiseSaying.id == id)
                .findFirst()
                .map(wiseSaying -> {
                    list.remove(wiseSaying);
                    fileDelete(id);
                    return "명언을 삭제했습니다.";
                })
                .orElse("해당하는 명언이 없습니다.");
    }

    void fileDelete(int id){
        File file = new File("src/main/resources/db/wiseSaying/" + id + ".json");
        if (!file.delete()) {
            System.err.println("파일 삭제에 실패했습니다: " + file.getAbsolutePath());
        }
    }

    ArrayList<WiseSaying> findForList(){
        ArrayList<WiseSaying> list =  new ArrayList<>(wiseSayings);
        list.sort((o1, o2) -> o2.id - o1.id);
        return list;
    }

    String modify(int id, String content, String author) {
        WiseSaying wiseSaying = findForModify(id);

        if (wiseSaying == null) {
            return "변경할 명언이 존재하지 않습니다.";
        }

        wiseSaying.content = content;
        wiseSaying.author = author;

        File file = new File("src/main/resources/db/wiseSaying/" + wiseSaying.id + ".json");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("{\n  \"id\": " + wiseSaying.id + ",\n  \"author\": \"" + wiseSaying.author + "\",\n  \"content\": \"" + wiseSaying.content + "\"\n}\n");
            return "변경되었습니다.";
        } catch (IOException e) {
            return "변경에 실패했습니다. " + e.getMessage();
        }
    }


    WiseSaying findForModify(int id){
        return wiseSayings
                .stream()
                .filter(wiseSaying -> wiseSaying.id == id)
                .findFirst()
                .orElse(null);
    }

    String save(){
        if(wiseSayings.isEmpty()||lastId==0) return "저장할 명언이 존재하지 않습니다.";
        return "저장되었습니다.";
    }
}
