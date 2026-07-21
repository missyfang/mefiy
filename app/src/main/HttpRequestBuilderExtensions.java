package main;

import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HttpRequestBuilderExtensions {
    private final HttpRequest.Builder builder;

    public HttpRequestBuilderExtensions(HttpRequest.Builder builder) {
        this.builder = builder;
    }

    public HttpRequestBuilderExtensions withAuthorizationHeader(String clientId, String clientSecret) {
        String encoded = Base64.getEncoder().encodeToString(
                (clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));
        builder.header("Authorization", "Basic " + encoded);
        return this;
    }

    public HttpRequestBuilderExtensions withContentType(String contentType) {
        builder.header("Content-Type", contentType);
        return this;
    }

    public HttpRequestBuilderExtensions withPost(HttpRequest.BodyPublisher body) {
        builder.POST(body);
        return this;
    }

    public HttpRequest build() {
        return builder.build();
    }
}

class TokenRequestBodyBuilder {
    private String grantType;
    private String authCode;
    private String redirectUri;

    public TokenRequestBodyBuilder withGrantType(String grantType) {
        this.grantType = grantType;
        return this;
    }

    public TokenRequestBodyBuilder withAuthCode(String authCode) {
        this.authCode = authCode;
        return this;
    }

    public TokenRequestBodyBuilder withRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public HttpRequest.BodyPublisher build() {
        String body = "grant_type=" + grantType
                + "&code=" + authCode
                + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);
        return HttpRequest.BodyPublishers.ofString(body);
    }
}
