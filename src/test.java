import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;



public class test {
    public static void main(String[] args) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get("local.csv"))) {
            String DELIMITER = ",";
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(DELIMITER);
                System.out.println("User["+ String.join(", ", columns) +"]");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


   }