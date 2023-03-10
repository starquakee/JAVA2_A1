import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;



public class test {
    public static void main(String[] args) {
        // 创建 reader
        try (BufferedReader br = Files.newBufferedReader(Paths.get("local.csv"))) {
            // CSV文件的分隔符
            String DELIMITER = ",";
            // 按行读取
            String line;
            while ((line = br.readLine()) != null) {
                // 分割
                String[] columns = line.split(DELIMITER);
                // 打印行
                System.out.println("User["+ String.join(", ", columns) +"]");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


   }