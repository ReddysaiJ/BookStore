package com.example.bookstore.webapp.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class SecurityServices {
    private final OAuth2AuthorizedClientService authorizedClientService;

    public SecurityServices(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    public String getAccessToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof OAuth2AuthenticationToken oAuthToken))
            return null;
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                oAuthToken.getAuthorizedClientRegistrationId(), oAuthToken.getName()
        );
        return client.getAccessToken().getTokenValue();
    }
}
