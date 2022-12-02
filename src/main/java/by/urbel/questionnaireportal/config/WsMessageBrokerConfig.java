package by.urbel.questionnaireportal.config;

import by.urbel.questionnaireportal.constants.WebSockets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WsMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {
    @Value(value = "${client.url}")
    private String clientUrl;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(WebSockets.WS_ENDPOINT).setAllowedOrigins(clientUrl).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(WebSockets.RESPONSES_TOPIC);
        registry.setApplicationDestinationPrefixes(WebSockets.WS_APP_ENDPOINT);
    }
}
