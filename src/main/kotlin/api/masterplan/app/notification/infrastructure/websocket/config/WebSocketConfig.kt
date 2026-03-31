package api.masterplan.app.notification.infrastructure.websocket.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig: WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        // Создание простого брокера для отправки сообщений (исходящая рассылка)
        registry.enableSimpleBroker("/topic")
        // Входящая рассылка
        registry.setApplicationDestinationPrefixes("/app")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        // Создание точки входа для клиентов
        registry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS()
    }

}