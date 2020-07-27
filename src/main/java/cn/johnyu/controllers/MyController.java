package cn.johnyu.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @RequestMapping("/suc")
    public String find(){
        return "succ";
    }
    @RequestMapping("/forbidden")
    public String forbidden(){
        return "forbidden";
    }

}
