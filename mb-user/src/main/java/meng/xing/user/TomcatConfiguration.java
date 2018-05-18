//package meng.xing.user;
//
//import org.apache.catalina.connector.Connector;
//import org.apache.coyote.http11.Http11NioProtocol;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class TomcatConfiguration {
//    @Value("${my_server.port}")
//    private int port;
//    @Value("${my_server.maxConnections}")
//    private int maxConnections;
//    @Value("${my_server.maxThreads}")
//    private int maxThreads;
//    @Value("${my_server.connectionTimeout}")
//    private int connectionTimeout;
//    @Value("${my_server.keepAliveTimeout}")
//    private int keepAliveTimeout;
//
//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//        tomcat.addConnectorCustomizers(new MyTomcatConnectorCustomizer());
//        return tomcat;
//    }
//
//    class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer {
//        @Override
//        public void customize(Connector connector) {
//            Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
//            //设置最大连接数
//            protocol.setMaxConnections(maxConnections);
//            //设置最大线程数
//            protocol.setMaxThreads(maxThreads);
//            protocol.setConnectionTimeout(connectionTimeout);
//            protocol.setKeepAliveTimeout(keepAliveTimeout);
//            protocol.setPort(port);
//
//        }
//    }
//}
//
