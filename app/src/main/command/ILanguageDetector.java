package main.command;

// detects the language
public interface ILanguageDetector {
    // returns an ISO code
    String detect(String lyricsText) throws Exception;
}
