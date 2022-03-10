package com.hebeu.utils;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: IndexConfig
 * @Author: Smoadrareun
 * @Description: TODO 服务启动时自动启动浏览器
 */

@Configuration
public class IndexConfig {

    @EventListener({ApplicationReadyEvent.class})
    public void applicationReadyEvent() {
        System.out.println("准备就绪 ... 正在启动浏览器");
        // 启动后访问地址
        String url = "http://localhost:8080/";
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
