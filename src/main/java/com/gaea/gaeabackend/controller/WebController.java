/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ndry93
 */
@Controller
@RequestMapping(value = "/web")
public class WebController {
    //admingaea@gaea.id:welcome1
    //https://stackoverflow.com/questions/33556250/implement-both-http-basic-and-forms-login-on-the-same-springboot-application
    //https://stackoverflow.com/questions/33739359/combining-basic-authentication-and-form-login-for-the-same-rest-api
    
    @RequestMapping("/")
    public String page(Model model) {
        model.addAttribute("attribute", "value");
        return "index";
    }
    
}
