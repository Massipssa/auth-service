package com.anonymizer.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/index")
    public String index(Model model) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d yyyy : HH:mm:ss:SSS z");
        Date now = new Date();
        String dateStr = dateFormat.format( now );
        model.addAttribute("time", dateStr);
        return "index";
    }
}