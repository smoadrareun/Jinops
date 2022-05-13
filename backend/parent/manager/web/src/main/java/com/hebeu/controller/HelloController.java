package com.hebeu.controller;

import com.hebeu.common.DateUtil;
import com.hebeu.common.VerUtil;
import com.hebeu.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@CrossOrigin    //解决跨域问题
@Controller     //需跳转到页面，不能用@RestController
public class HelloController {

    private CartService cartService;

    @Autowired
    public void setCartVoService (CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping("/") //跳转的网址
    public ModelAndView sayHello(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello");
        modelAndView.addObject("key", "Hello, World!");
        modelAndView.addObject("value", DateUtil.getCurrentDateStr("yyyy-MM-dd HH:mm:ss"));
        String str=VerUtil.getRandom(6);
        modelAndView.addObject("verCode", str);
        modelAndView.addObject("verImage", VerUtil.getCodeImage(str));
        return modelAndView;
    }

}
