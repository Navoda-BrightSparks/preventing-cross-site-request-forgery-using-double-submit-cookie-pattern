package com.doublesubmittoken.demo.controllers;


import com.doublesubmittoken.demo.models.FundTransfer;
import com.doublesubmittoken.demo.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Handles fund transfer functions
 *
 * Created by navodasathsarani on 9/6/18.
 */
@Controller
public class FundsController {

    private static Logger logger = LoggerFactory.getLogger(FundsController.class);

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/transfer")
    public String transferFunds(@ModelAttribute FundTransfer fundTransfer, HttpServletRequest request) {
        logger.info("Request received for transferFunds...");
        logger.info("Authenticating user session...");

        if (authenticationService.isAuthenticated(request.getCookies(), fundTransfer.getCsrf())) {
            logger.error("Success..");
            return "redirect:/home?status=success";
        }
        logger.error("User not authenticated!!!");
        return "redirect:/home?status=failed";
    }


}
