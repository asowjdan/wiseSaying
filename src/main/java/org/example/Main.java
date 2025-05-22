package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("== 명언 앱 ==");
        Scanner scanner = new Scanner(System.in);

        ArrayList<Word1> words = new ArrayList<>();
        HashMap<Integer, Word2> map = new HashMap<>();

        int arrayindex;
        int mapindex;

        String filePath = "src/main/resources/db/";

        //ArrayList 인덱스 불러오기
        try {
            FileReader fileReaderArray = new FileReader(filePath + "ArraylastId.txt");
            if (fileReaderArray.read() == -1) {
                arrayindex = 1;
                System.out.println("ArrayList 인덱스 값이 존재하지 않습니다.");
            }
            else {
                File arrayfile = new File(filePath + "ArraylastId.txt");
                BufferedReader br = new BufferedReader(new FileReader(arrayfile));
                String line = br.readLine();
                arrayindex = Integer.parseInt(line);
            }
        } catch (IOException e) {
            File file = new File(filePath + "ArraylastId.txt");
            file.createNewFile();

            arrayindex = 1;
            System.out.println("ArrayList 인덱스 파일이 존재하지 않습니다.");
        }

        //Map 인덱스 불러오기
        try {
            FileReader fileReaderMap = new FileReader(filePath + "MaplastId.txt");
            if (fileReaderMap.read() == -1) {
                mapindex = 1;
                System.out.println("Map 인덱스 값이 존재하지 않습니다.");
            }else {
                File mapfile = new File(filePath + "MaplastId.txt");
                BufferedReader br = new BufferedReader(new FileReader(mapfile));
                String line = br.readLine();
                mapindex = Integer.parseInt(line);
            }
        }catch (IOException e) {
            File file = new File(filePath + "MaplastId.txt");
            file.createNewFile();
            mapindex = 1;
            System.out.println("Map 인덱스 파일이 존재하지 않습니다.");
        }

        //메인 반복문
        while (true) {
            System.out.print("명령) ");
            String command = scanner.nextLine();

            switch (command) {
                case "등록":
                    //명언등록 ArrayList에 저장
                    System.out.print("명언 : ");
                    String content = scanner.nextLine();
                    System.out.print("작가 : ");
                    String author = scanner.nextLine();

                    Word1 word = new Word1();
                    word.id = arrayindex;
                    word.name = author;
                    word.word = content;
                    words.add(word);

                    System.out.println("ArrayList : "+word.id + "번 명언이 등록되었습니다.");

                    //ArrayList jsonObject 저장
                    try {
                        File file = new File(filePath + "arrayList/" + "ArrayList" + arrayindex + ".json");
                        file.createNewFile();
                        FileWriter fileWriterArray = new FileWriter(file);
                        fileWriterArray.write("{\n" + "\t\"id\" : " + word.id + ",\n" + "\t\"name\" : \"" + word.name + "\",\n" + "\t\"word\" : \"" + word.word + "\"\n" + "}");
                        fileWriterArray.close();
                    }catch (IOException e) {
                        System.out.println("ArrayList) json 파일 저장에 실패하였습니다.");
                    }
                    arrayindex++;

                    //Map에 저장
                    Word2 value = new Word2();
                    value.name = word.name;
                    value.word = word.word;
                    map.put(mapindex, value);
                    System.out.println("Map : "+ mapindex + "번 명언이 등록되었습니다.");
                    //Map jsonObject 저장
                    try {
                        File file = new File(filePath + "map/" + "Map" + mapindex + ".json");
                        file.createNewFile();
                        FileWriter fileWriterMap = new FileWriter(file);
                        fileWriterMap.write("{\n"+"\t\"id\" : " + mapindex + ",\n" +"\t\"name\" : \"" + value.name + "\",\n" + "\t\"word\" : \"" + value.word + "\"\n" + "}");
                        fileWriterMap.close();
                    }catch (IOException e) {
                        System.out.println("Map) json 파일 저장에 실패하였습니다.");
                    }
                    mapindex++;
                    break;

                case "목록":
                    //ArrayList
                    System.out.println("==========ArrayList========");
                    System.out.println("번호 / 작가 / 명언");
                    System.out.println("----------------------");

                    //ArrayList jsonObject 출력
                    try {
                        String fileName = "";

                        /*파일 경로에 있는 파일 가져오기*/
                        File rw = new File(filePath+"arrayList/");

                        /*파일 경로에 있는 파일 리스트 fileList[] 에 넣기*/
                        File [] fileList = rw.listFiles();

                        /*fileList에 있는거 for 문 돌려서 출력*/
                        ArrayList<String[]> dataList = new ArrayList<>();
                        for (File file : fileList) {
                            if (file.isFile()) {
                                fileName = file.getName();
                                String fileContents = "";
                                FileReader fileReader = new FileReader(filePath + "arrayList/" + fileName);
                                BufferedReader br = new BufferedReader(fileReader);
                                while ((fileContents = br.readLine()) != null) {
                                    if (fileContents.contains("\"id\"")) {
                                        String id = fileContents.split(":")[1].trim().replace(",", "");
                                        String name = br.readLine().split(":")[1].trim().replace("\"", "").replace(",", "");
                                        String word1 = br.readLine().split(":")[1].trim().replace("\"", "").replace(",", "");
                                        dataList.add(new String[]{id, name, word1});
                                        br.readLine();
                                    }
                                }
                                br.close();
                            }
                        }

                        dataList.sort((a, b) -> Integer.compare(Integer.parseInt(b[0]), Integer.parseInt(a[0])));

                        for (String[] data : dataList) {
                            System.out.println(data[0] + " / " + data[1] + " / " + data[2]);
                        }
                    }catch (Exception e) {
                        System.out.println("ArrayList) json 파일 출력에 실패하였습니다.");
                    }
                    
                    System.out.println("----------------------");

                    //Map
                    System.out.println("==========Map========");
                    System.out.println("번호 / 작가 / 명언");

                    //Map jsonObject 출력
                    try {
                        String fileName = "";

                        /*파일 경로에 있는 파일 가져오기*/
                        File rw = new File(filePath+"map/");

                        /*파일 경로에 있는 파일 리스트 fileList[] 에 넣기*/
                        File [] fileList = rw.listFiles();

                        /*fileList에 있는거 for 문 돌려서 출력*/
                        ArrayList<String[]> dataList = new ArrayList<>();
                        for (File file : fileList) {
                            if (file.isFile()) {
                                fileName = file.getName();
                                String fileContents = "";
                                FileReader fileReader = new FileReader(filePath + "map/" + fileName);
                                BufferedReader br = new BufferedReader(fileReader);
                                while ((fileContents = br.readLine()) != null) {
                                    if (fileContents.contains("\"id\"")) {
                                        String id = fileContents.split(":")[1].trim().replace(",", "");
                                        String name = br.readLine().split(":")[1].trim().replace("\"", "").replace(",", "");
                                        String word1 = br.readLine().split(":")[1].trim().replace("\"", "").replace(",", "");
                                        dataList.add(new String[]{id, name, word1});
                                        br.readLine();
                                    }
                                }
                                br.close();
                            }
                        }

                        dataList.sort((a, b) -> Integer.compare(Integer.parseInt(b[0]), Integer.parseInt(a[0])));

                        for (String[] data : dataList) {
                            System.out.println(data[0] + " / " + data[1] + " / " + data[2]);
                        }
                    }catch (Exception e) {
                        System.out.println("ArrayList) json 파일 출력에 실패하였습니다.");
                    }
                    System.out.println("----------------------");
                    break;

                case "삭제":
                    //ArrayList에서 삭제
                    System.out.print("삭제할 번호 : ");
                    int deleteId = Integer.parseInt(scanner.nextLine());
                    boolean found = false;

                    if(deleteId <= arrayindex){
                        File file = new File(filePath + "arrayList/" + "ArrayList" + deleteId + ".json");
                        if(file.exists()) {
                            file.delete();
                            System.out.println("ArrayList : " + deleteId + "번 명언의 json 파일이 삭제되었습니다.");
                            found = true;
                        }else {
                            System.out.println("ArrayList : " + deleteId + "번 명언의 json 파일이 존재하지 않습니다.");
                        }
                    } else if (!found) {
                        System.out.println("ArrayList : " + deleteId + "번 명언은 존재하지 않습니다.");
                    } else {
                        for (int i = 0; i < words.size(); i++) {
                            if (words.get(i).id == deleteId) {
                                words.remove(i);
                                System.out.println("ArrayList : " + deleteId + "번 명언이 삭제되었습니다.");
                                found = true;
                            }
                        }
                    }

                    //Map에서 삭제
                    if(deleteId <= mapindex){
                        File file = new File(filePath + "map/" + "Map" + deleteId + ".json");
                        if(file.exists()) {
                            file.delete();
                            System.out.println("Map : " + deleteId + "번 명언의 json 파일이 삭제되었습니다.");
                            break;
                        }else {
                            System.out.println("Map : " + deleteId + "번 명언의 json 파일이 존재하지 않습니다.");
                            break;
                        }
                    } else if (!map.containsKey(deleteId)) {
                        System.out.println("Map : " + deleteId + "번 명언은 존재하지 않습니다.");
                        break;
                    } else {
                        map.remove(deleteId);
                        System.out.println("Map : " + deleteId + "번 명언이 삭제되었습니다.");
                        break;
                    }

                case "저장" :
                    //ArrayList를 파일에 저장
                    String fileName = "ArrayList.json";

                    String ArrayIndex = "Array.txt";
                    String MapsIndex = "Map.txt";
                    File file = new File(filePath + ArrayIndex);
                    break;

                case "종료":
                    System.out.println("프로그램을 종료합니다.");
                    scanner.close();
                    //Array 인덱스 값 저장
                    try {
                        FileWriter fileWriterArray = new FileWriter(filePath + "ArraylastId.txt");
                        fileWriterArray.write(String.valueOf(arrayindex));
                        fileWriterArray.close();
                    }catch (IOException e) {
                        System.out.println("Array 인덱스 파일 저장에 실패하였습니다.");
                    }
                    //Map 인덱스 값 저장
                    try {
                        FileWriter fileWriterMap = new FileWriter(filePath + "MaplastId.txt");
                        fileWriterMap.write(String.valueOf(mapindex));
                        fileWriterMap.close();
                    }catch (IOException e) {
                        System.out.println("Map 인덱스 파일 저장에 실패하였습니다.");
                    }
                    return;

                default:
                    System.out.println("잘못된 명령입니다.");
            }
        }
    }

    public static class Word1 {
        int id = 0;
        String name;
        String word;
    }
    public static class Word2 {
        String name;
        String word;
    }
}