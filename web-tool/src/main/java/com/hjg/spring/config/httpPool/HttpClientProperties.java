package com.hjg.spring.config.httpPool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "http")
public class HttpClientProperties {

    //连接管理器中的最大连接数：池化的连接管理器中默认值为20
    private Integer maxTotal = 200;

    //每条路由的最大连接：池化的连接管理器中默认值为2
    private Integer defaultMaxPerRoute = 50;

    //创建链接的最大时间，单位毫秒
    private Integer connectTimeout = 2000;

    //从链接管理器获取链接获取超时时间，单位毫秒
    private Integer connectionRequestTimeout = 4000;

    //建立链接之后等待数据的时间：2次数据包之间最大的不活跃时间，单位毫秒
    private Integer socketTimeout = 3000;

    private int retryCount = 3;

    private boolean requestSentRetryEnabled = false;

    /**
     * 服务器与客户端连接维持时间，与服务端keepalive配置有关，单位秒。
     */
    private Integer keepAliveTimeout = 60;
}
