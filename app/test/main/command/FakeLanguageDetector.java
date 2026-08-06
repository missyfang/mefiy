package main.command;

// test stub — returns a fixed language code for every input
public class StubLanguageDetector implements ILanguageDetector {

    private final String language;

    public StubLanguageDetector(String language) {
        this.language = language;
    }

    @Override
    public String detect(String text) {
        return language;
    }
}
