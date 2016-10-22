package home.loggings

import groovy.util.logging.Slf4j
import org.elasticsearch.client.Client
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "home.loggings")
@Slf4j
class Application extends RepositoryRestConfigurerAdapter {
    static void main(def args) {
        SpringApplication.run Application.class
    }

//    @Bean
//    NodeBuilder nodeBuilder() {
//        new NodeBuilder();
//    }
//    @Bean
//    public ElasticsearchOperations elasticsearchTemplate() {
//        Settings.Builder elasticsearchSettings =
//                Settings.settingsBuilder()
//                        .put("http.enabled", "true") // 1
//                        .put("path.data", tmpDir.toAbsolutePath().toString()) // 2
//                        .put("path.home", "PATH_TO_YOUR_ELASTICSEARCH_DIRECTORY"); // 3
//
//        log.debug(tmpDir.toAbsolutePath().toString());
//
//        return new ElasticsearchTemplate(nodeBuilder()
//                .local(true)
//                .settings(elasticsearchSettings.build())
//                .node()
//                .client());
//    }
    @Bean
    public static Client client() {
        Client client = null;
        Settings settings = Settings.settingsBuilder().put("cluster.name", "elasticsearch").build();
        try {
            InetSocketTransportAddress transportAddress = new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300);
            client = TransportClient.builder().settings(settings).build().addTransportAddresses(transportAddress);
        }
        catch (UnknownHostException e) {
            log.error("Es Connection failed, application wont work as expected, FIX IT!!!!!!" + e);
        }
        return client;
    }

    @Bean
    public static ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(client());
    }
//    @Bean
//    public static ElasticsearchOperations elasticsearchTemplate() {
//        return new ElasticsearchTemplate(new NodeBuilder().local(true).node().client());
//    }
    @Bean
    CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // you USUALLY want this
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PUT");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
