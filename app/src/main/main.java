package main;

import java.util.Scanner;

public class main {
    void main(String[] args) throws Exception {
        AppConfig config = new AppConfig();

        System.out.println("enter client secret: ");
        Scanner scanner = new Scanner(System.in);
        String clientSecret = scanner.next();

        String token = new AuthTokenGenerator(config.getClientId(), clientSecret, config.getRedirectUri()).GetToken(3000);
        System.out.println("got the token: " + token);
    }
}
