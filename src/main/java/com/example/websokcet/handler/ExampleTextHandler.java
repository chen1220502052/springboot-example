package com.example.websokcet.handler;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


public class ExampleTextHandler extends TextWebSocketHandler{

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        // TODO Auto-generated method stub
        super.handleBinaryMessage(session, message);
    }
}
