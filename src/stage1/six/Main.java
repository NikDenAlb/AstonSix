package stage1.six;

import com.google.gson.Gson;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        ClassToSave objToFile = new ClassToSave("Cтрока", 11, 124214, true);
        System.out.println("Object of serialization:\n" + objToFile);

        //обычная серилизация

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/stage1/six/files/ClassToSave.txt", false))) {
            oos.writeObject(objToFile);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/stage1/six/files/ClassToSave.txt"))) {

            ClassToSave objFromFile = (ClassToSave) ois.readObject();
            System.out.println("Usual deserialization result:\n" + objFromFile);

        } catch (Exception e) {
            e.fillInStackTrace();
        }

        //Серилизация через com.google.gson.Gson

        ClassToSave objFromFile = null;
        String sFromGson = new Gson().toJson(objToFile);
        System.out.println("String to file:\n" + sFromGson);

        try {
            FileWriter writer = new FileWriter("src/stage1/six/files/gsonSave.txt");
            writer.write(sFromGson);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }

        try (FileReader fileReader = new FileReader("src/stage1/six/files/gsonSave.txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("String from file:\n" + line);
                objFromFile = new Gson().fromJson(line, ClassToSave.class);
            }
        } catch (IOException e) {
            System.out.println("Error reading file:\n" + e.getMessage());
        }

        System.out.println("Gson deserialization result:\n" + objFromFile);
    }
}