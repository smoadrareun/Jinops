package com.hebeu.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

@Slf4j
@Configuration
public class IndexConfig {

    @Value("${server.port}")
    private String port;
    @Value("${server.servlet.context-path}")
    private String path;

    @EventListener({ApplicationReadyEvent.class})
    public void applicationReadyEvent() {
        // 启动后访问地址
        String url = "http://localhost:"+port+path;
        Runtime runtime = Runtime.getRuntime();
        log.info("准备就绪... 即将启动浏览器: {}",url);
        try {
            runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
        } catch (IOException e) {
            log.error("浏览器启动失败：",e);
        }
    }

}
