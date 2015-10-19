package com.crm.guard.controller;

import com.crm.guard.filter.SearchFields;
import com.crm.guard.filter.SearchType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @RequestMapping("/login")
    public String showFormLogin(ModelMap model) throws IOException {
        return "auth/login";
    }



}
