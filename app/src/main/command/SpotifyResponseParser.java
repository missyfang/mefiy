package main.command;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/// class to pull the data need out of the json response using regex to figure out what we need
public class SpotifyResponseParser {

    public static String parseFirst(String body, Pattern pattern) {
        Matcher matcher = pattern.matcher(body);
        return matcher.find() ? matcher.group(1) : null;
    }

    public static List<String> parseAll(String body, Pattern pattern) {
        List<String> results = new ArrayList<>();
        Matcher matcher = pattern.matcher(body);
        while (matcher.find()) {
            results.add(matcher.group(1));
        }
        return results;
    }

    public static boolean hasMatch(String body, Pattern pattern) {
        return pattern.matcher(body).find();
    }
}
