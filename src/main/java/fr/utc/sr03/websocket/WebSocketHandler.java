package fr.utc.sr03.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class WebSocketHandler extends TextWebSocketHandler {

    private final String nameChat;
    private final Logger logger = Logger.getLogger(WebSocketHandler.class.getName());
    private final HashMap<Integer,ArrayList<WebSocketSession>> sessions;
    private final HashMap<Integer,ArrayList<MessageSocket>> messageSocketsHistory;

    public WebSocketHandler(String nameChat) {
        this.nameChat = nameChat;
        this.messageSocketsHistory = new HashMap<Integer, ArrayList<MessageSocket>>();
        this.sessions = new HashMap<Integer,ArrayList<WebSocketSession>>();
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        String receivedMessage = (String) message.getPayload();
        MessageSocket messageSocket = mapper.readValue(receivedMessage, MessageSocket.class);

        //Pour stocker le message dans l'historique
        //messageSocketsHistory.add(messageSocket);

        //Envoi du message à tous les connectés
        this.broadcast(messageSocket.getChannel_id(), messageSocket.getUser()+ " : " + messageSocket.getMessage());

    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        //On stocke la session du client dans une liste
        java.net.URI uri = session.getUri();
        System.out.println(session);
        String id_temp = null;
        if (uri != null) {
            id_temp = UriComponentsBuilder.fromUri(uri)
                    .build()
                    .getQueryParams()
                    .getFirst("id");
        }
        if (id_temp==null){
            logger.info("Id de channel inconnu");
            throw new IOException("Id de channel inconnu");
        }
        Integer channel_id = Integer.parseInt(id_temp);
        sessions.computeIfAbsent(channel_id, k -> new ArrayList<>());
        sessions.get(channel_id).add(session);
        logger.info(session.getId());

        //J'affiche l'historique du salon
        //for(MessageSocket messageSocket : messageSocketsHistory){
        //    session.sendMessage(new TextMessage(messageSocket.getUser()+ " : " + messageSocket.getMessage()));
        //}

        logger.info("Connecté sur le " + this.nameChat);

    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

        //Quand le client quitte, on retire sa session
        sessions.forEach((id,session_list)->
            session_list.remove(session));
        logger.info("Déconnecté du " + this.nameChat);

    }

    public void broadcast(Integer channel_id,String message) throws IOException {
        System.out.println("broadcasted");
        System.out.println(sessions);
        if (sessions.get(channel_id)==null){
            return;
        }
        for (WebSocketSession session : sessions.get(channel_id)) {
            session.sendMessage(new TextMessage(message));
        }
    }
}
