package com.hjg.spring.config.httpPool;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpPoolConfig {

    @Bean
    public HttpClientProperties httpClientProperties() {
        return new HttpClientProperties();
    }

    //使用池化的连接管理器
    @Bean(name = "httpClientConnectionManager")
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(HttpClientProperties httpClientProperties) {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        //设定最大链接数
        manager.setMaxTotal(httpClientProperties.getMaxTotal());
        //设定每个路由信息的最大链接数
        manager.setDefaultMaxPerRoute(httpClientProperties.getDefaultMaxPerRoute());
        return manager;
    }

    /**
     * 配置HttpClientBuilder类。
     * @param httpClientProperties
     * @return
     */
    @Bean(name = "httpClientBuilder")
    public HttpClientBuilder httpClientBuilder(HttpClientProperties httpClientProperties) {

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(this.poolingHttpClientConnectionManager(httpClientProperties));
        httpClientBuilder.setDefaultRequestConfig(this.requestConfig(httpClientProperties));
        if (!httpClientProperties.isRequestSentRetryEnabled()) {
            httpClientBuilder.disableAutomaticRetries();
        } else {
            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(httpClientProperties.getRetryCount(), httpClientProperties.isRequestSentRetryEnabled()));
        }
        //close all expired connections and evict closed connections from the pool.
        //expired connections应该是指被服务端关闭了的连接，会开启一个线程默认每10秒执行一次。
        //也可以完全自定义一个evict policy.
        return httpClientBuilder.evictExpiredConnections();
    }

    /**
     * 配置全局的CloseableHttpClient类。
     * @param httpClientProperties
     * @return
     */
    @Bean
    public CloseableHttpClient closeableHttpClient(HttpClientProperties httpClientProperties) {

        ConnectionKeepAliveStrategy keepAliveStrategy = (response, context) -> {
            // Honor 'keep-alive' header
            HeaderElementIterator it = new BasicHeaderElementIterator(
                    response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    try {
                        return Long.parseLong(value) * 1000;
                    } catch(NumberFormatException ignore) {
                    }
                }
            }

            //keep alive for xx seconds
            return httpClientProperties.getKeepAliveTimeout() * 1000;
        };

        return httpClientBuilder(httpClientProperties)
                .setDefaultRequestConfig(this.requestConfig(httpClientProperties))
                .setKeepAliveStrategy(keepAliveStrategy)
                .build();
    }

    /**
     * 配置全局的HTTP请求行为。
     * @param httpClientProperties
     * @return
     */
    @Bean
    public RequestConfig requestConfig(HttpClientProperties httpClientProperties) {
        RequestConfig.Builder builder = RequestConfig.custom();
        builder.setConnectTimeout(httpClientProperties.getConnectTimeout())
                .setConnectionRequestTimeout(httpClientProperties.getConnectionRequestTimeout())
                .setSocketTimeout(httpClientProperties.getSocketTimeout());
        return builder.build();
    }
}
