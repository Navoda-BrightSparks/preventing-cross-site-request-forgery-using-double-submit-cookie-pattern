package com.doublesubmittoken.demo.controllers;



import com.doublesubmittoken.demo.exceptions.UnauthorizedException;
import com.doublesubmittoken.demo.models.SessionStore;
import com.doublesubmittoken.demo.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by navodasathsarani on 9/6/18.
 */

@RestController
public class CSRFController {

    private Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping(path="/token")
    public String getToken(HttpServletRequest request) throws UnauthorizedException {
        String sessionId = authenticationService.sessionIdFromCookies(request.getCookies());
        logger.info("Request received for CSRF token...");
        logger.info("Authenticating user session...");

        if (null != sessionId && null != SessionStore.getUserCredentialsStore().getTokenFromSession(sessionId)){
            logger.info("Successfully authenticated user session...");
            return SessionStore.getUserCredentialsStore().getTokenFromSession(sessionId);
        }
        logger.error("Failed to authenticate user!!!");
        throw new UnauthorizedException();
    }
}
