package main;

import java.net.URI;

public interface ICodeURIBuilder {
    ICodeURIBuilder withBaseUrl(String baseUrl);
    ICodeURIBuilder withClientId(String clientId);
    ICodeURIBuilder withResponseType(String responseType);
    ICodeURIBuilder withRedirectUri(String redirectUri);
    ICodeURIBuilder withScope(String scope);
    URI build();
}
