package main.command;

// test fake — returns a fixed language code for every input
public class FakeLanguageDetector implements ILanguageDetector {

    private final String language;

    public FakeLanguageDetector(String language) {
        this.language = language;
    }

    @Override
    public String detect(String text) {
        return language;
    }
}
