package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entity.ErrorMessage;
import com.back.domain.wiseSaying.entity.WiseSaying;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class WiseSayingRepository {
    String directoryPath = "src/main/resources/db/wiseSaying/";
    String LastIdfileName ="src/main/resources/db/wiseSaying/LastId.txt";

    public int loadLastId() {
        folderCheck(directoryPath);
        if (fileCheck(LastIdfileName)) {
            final String defaultContent = "0";
            writeToFile(LastIdfileName, defaultContent);
            return 0;
        } else if (fileRead(LastIdfileName)==null) {
            ArrayList<WiseSaying> list = loadData();
            if(list.isEmpty()) return 0;
            return list.getLast().getId();
        }
        return Integer.parseInt(fileRead(LastIdfileName));
    }

    public ArrayList<WiseSaying> loadData() {
        ArrayList<WiseSaying> wiseSayingList = new ArrayList<>();

        if (fileCheck(directoryPath)) {
            return wiseSayingList;
        }

        File dir = new File(directoryPath);
        String[] fileList = dir.list((dir1, name) -> name.endsWith(".json"));

        if (fileList != null) {
            Arrays.sort(fileList, (a, b) -> { // Add sorting logic
                int numA = Integer.parseInt(a.replace(".json", ""));
                int numB = Integer.parseInt(b.replace(".json", ""));
                return Integer.compare(numA, numB);
            });

            for (String fileName : fileList) {
                wiseSayingList.add(jsonRead(fileName));
            }
        }
        wiseSayingList.sort((o1, o2) -> o2.getId() - o1.getId());
        return wiseSayingList;
    }

    private void writeToFile(String fileName, String content) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(content);
        } catch (IOException e) {
            throw new RuntimeException("파일 작성에 실패했습니다. " + fileName, e);
        }
    }

    public boolean fileCheck(String fileName){
        File file = new File(fileName);
        return !file.exists();
    }

    public String folderCheck(String folderName){
        File file = new File(folderName);
        if(!file.exists()){
            if(!file.mkdirs()){
                return ErrorMessage.Error500();
            }
        }
        return null;
    }

    public String fileRead(String fileName){
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            return br.readLine();
        } catch (IOException e) {
            return ErrorMessage.Error500();
        }
    }

    public WiseSaying jsonRead(String fileName){
        try (BufferedReader br = new BufferedReader(new FileReader(directoryPath+fileName))) {
            StringBuilder jsonData = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                jsonData.append(line.trim());
            }

            String json = jsonData.toString();

            int id =Integer.parseInt(json
                    .split("\"id\":")[1]
                    .split(",")[0]
                    .trim()
            );

            String author = json
                    .split("\"author\":")[1]
                    .split(",")[0]
                    .replace("\"", "")
                    .trim();

            String content = json
                    .split("\"content\":")[1]
                    .replace("}", "")
                    .replace("\"", "")
                    .trim();

            return new WiseSaying(id, content, author);
        } catch (IOException e) {
            System.err.println("파일 읽기 오류: " + fileName);
        } catch (Exception e) {
            System.err.println("JSON 파싱 오류: " + fileName);
        }
        return null;
    }

    public void plusLastId(int lastId){
        writeToFile(LastIdfileName, String.valueOf(lastId));
    }
    
    public void jsonWrite(WiseSaying wiseSaying){
        int id = wiseSaying.getId();
        String content = wiseSaying.getContent();
        String author = wiseSaying.getAuthor();

        File file = new File(directoryPath+ id + ".json");

        try (FileWriter fw = new FileWriter(file)) {
            fw.write("{\n  \"id\": " + id + ",\n  \"author\": \"" + author + "\",\n  \"content\": \"" + content + "\"\n}\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String fileDelete(int id){
        File file = new File("src/main/resources/db/wiseSaying/" + id + ".json");
        if (!file.delete()) {
            return "명언 파일 삭제에 실패했습니다.";
        }
        return "명언을 삭제했습니다.";
    }
}
