package com.hebeu.controller;

import com.hebeu.common.DateUtil;
import com.hebeu.common.VerCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@CrossOrigin    //解决跨域问题
@Controller     //需跳转到页面，不能用@RestController
public class HelloController {

    @RequestMapping("/") //跳转的网址
    public ModelAndView sayHello(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello");
        modelAndView.addObject("key", "Hello, World!");
        modelAndView.addObject("value", DateUtil.getCurrentDateStr("yyyy-MM-dd HH:mm:ss"));
        Map<String,String> map= VerCode.getVerCode(6);
        modelAndView.addObject("verCode", map.get("verCode"));
        modelAndView.addObject("verImage", map.get("verImage"));
        return modelAndView;
    }

}
