package com.example.blip_be.global.socket;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
@RequiredArgsConstructor
public class SignalingSocketHandler implements WebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(SignalingSocketHandler.class);
    private final JvbService jvbService;

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        logger.info("{} 연결됨", session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        String payload = message.getPayload().toString();
        logger.info("메세지 수신 [{}] : {}", session.getId(), payload);


        if (payload.contains("\"type\":\"offer\"")) {
            jvbService.forwardToJvb(payload, "offer");
        } else if (payload.contains("\"type\":\"answer\"")) {
            jvbService.forwardToJvb(payload, "answer");
        } else if (payload.contains("\"candidate\"")) {
            jvbService.forwardToJvb(payload, "ice-candidate");
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws  Exception {
        logger.error("오류 발생 [{}]: {}", session.getId(), exception.getMessage());
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        logger.info("[{}] 끊김 ({})", session.getId(), status);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
