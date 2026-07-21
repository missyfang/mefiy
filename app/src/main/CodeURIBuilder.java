package main;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


// builder class to create the uri needed to grab a code to exchange for an auth token
public class CodeURIBuilder {
    private String baseUrl;
    private String clientId;
    private String responseType;
    private String redirectUri;
    private String scope;

    public CodeURIBuilder withBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public CodeURIBuilder withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public CodeURIBuilder withResponseType(String responseType) {
        this.responseType = responseType;
        return this;
    }

    public CodeURIBuilder withRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public CodeURIBuilder withScope(String scope) {
        this.scope = scope;
        return this;
    }

    public URI build() {
        String uri = baseUrl
                + "?client_id=" + clientId
                + "&response_type=" + responseType
                + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)
                + "&scope=" + URLEncoder.encode(scope, StandardCharsets.UTF_8);
        return URI.create(uri);
    }
}
