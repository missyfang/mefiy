package main.command.language;

import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;

// detects language from text using the Lingua
public class LinguaLanguageDetector implements ILanguageDetector {

    private final LanguageDetector detector;

    public LinguaLanguageDetector() {
        this.detector = LanguageDetectorBuilder
                .fromAllLanguages()
                .build();
    }

    @Override
    public String detect(String text) {
        if (text == null || text.isBlank()) return null;
        Language language = detector.detectLanguageOf(text);
        if (language == Language.UNKNOWN) return null;
        return language.getIsoCode639_1().toString().toLowerCase();
    }
}
