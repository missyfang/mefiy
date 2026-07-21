package main;

import java.util.Scanner;

public class main {
    private String CLIENT_ID = "8a700cacda704fa28a412c549b93b1f1";
    private String CLIENT_SECRET = "";
    private String REDIRECT_URI = "http://127.0.0.1:3000";

    void main(String[] args) throws Exception {
        System.out.println("enter client secret: ");

        Scanner scanner = new Scanner(System.in);
        CLIENT_SECRET = scanner.next();
        String token = new AuthTokenGenerator(CLIENT_ID, CLIENT_SECRET, REDIRECT_URI).GetToken(3000);
        System.out.println("got the token: " + token);
    }
}
