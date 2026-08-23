package main.command;

import main.command.language.LanguagePlaylistContext;
import main.command.sharedCommands.GetUserCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetUserCommandTest {

    @Test
    void handleResponse_populatesUserIdAndReturnsTrue() {
        String body = "{\"id\":\"weezer\",\"display_name\":\"Rivers\"}";
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();

        boolean result = new GetUserCommand().handleResponse(body, ctx);

        assertTrue(result);
        assertEquals("weezer", ctx.userId);
    }

    @Test
    void handleResponse_returnsFalseWhenIdMissing() {
        String body = "{\"display_name\":\"Rivers\"}";
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();

        boolean result = new GetUserCommand().handleResponse(body, ctx);

        assertFalse(result);
        assertNull(ctx.userId);
    }

    @Test
    void execute_returnsFalseWhenTokenIsInvalid() {
        LanguagePlaylistContext ctx = new LanguagePlaylistContext();
        ctx.accessToken = "invalid_token";

        assertFalse(new GetUserCommand().execute(ctx));
        assertNull(ctx.userId);
    }
}
