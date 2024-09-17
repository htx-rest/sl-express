package com.sl.ms.search.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchConfig {

    private String host;
    private Integer port;

    @Bean
    public RestHighLevelClient client() {
        log.info("es连接ip：{}，端口：{}", host, port);
        //基本的连接
        RestClientBuilder clientBuilder = RestClient.builder(new HttpHost(host, port));
        return new RestHighLevelClient(clientBuilder);
    }
}