package com.dyl.tool.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Description: HomeController
 * Author: DIYILIU
 * Update: 2018-10-25 16:58
 */

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(){

        return "index";
    }
}
