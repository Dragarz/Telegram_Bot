package util;

import java.io.*;
import java.util.Scanner;

public class Util {
    private String UserName = null;
    private String Token = null;

    public Util(String fileName) {
        try(Scanner scanner = new Scanner(new FileReader(fileName))){
            UserName = scanner.next();
            Token = scanner.next();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getUserName() {
        return UserName;
    }

    public String getToken() {
        return Token;
    }


}
