package fr.utc.sr03.websocket;

import fr.utc.sr03.model.User;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.ObjectMapper;
import fr.utc.sr03.services.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class WebSocketHandler extends TextWebSocketHandler {

    private final String nameChat;
    private final Logger logger = Logger.getLogger(WebSocketHandler.class.getName());
    private final ConcurrentHashMap<Integer,ArrayList<WebSocketSession>> sessions;
    private final ConcurrentHashMap<Integer,ArrayList<MessageSocket>> messageSocketsHistory;
    private final UserService userService;

    public WebSocketHandler(String nameChat,UserService userService) {
        this.nameChat = nameChat;
        this.messageSocketsHistory = new ConcurrentHashMap<Integer, ArrayList<MessageSocket>>();
        this.sessions = new ConcurrentHashMap<Integer,ArrayList<WebSocketSession>>();
        this.userService=userService;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        String receivedMessage = (String) message.getPayload();
        MessageSocket messageSocket = mapper.readValue(receivedMessage, MessageSocket.class);
        //Keep history
//        messageSocketsHistory.computeIfAbsent(messageSocket.getChannel_id(), k -> new ArrayList<>());
//        messageSocketsHistory.get(messageSocket.getChannel_id()).add(messageSocket);
        this.broadcast(messageSocket);

    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        //get channel id
        System.out.println("hey");
        java.net.URI uri = session.getUri();
        System.out.println("session");
        System.out.println(session);
        int user_id=-1;
        int channel_id=-1;
        //recup channel_id, user_id
        if (uri != null) {
            MultiValueMap<String,String> param = UriComponentsBuilder.fromUri(uri)
                    .build()
                    .getQueryParams();
            String channel_id_temp=param.getFirst("channel_id");
            String user_id_temp=param.getFirst("user_id");
            if (channel_id_temp==null){
                logger.info("Id de channel inconnu");
                throw new IOException("Id de channel inconnu");
            }
            channel_id = Integer.parseInt(channel_id_temp);
            if (user_id_temp==null){
                logger.info("User_id inconnu");
                throw new IOException("User_id inconnu");
            }
        }

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
        //remove the session for each channels
        sessions.forEach((id,session_list)->
            session_list.remove(session));
        logger.info("Déconnecté du " + this.nameChat);
    }

    public void broadcast(MessageSocket messageSocket) throws IOException {
        int channel_id=messageSocket.getChannel_id();
        int user_id=messageSocket.getUser_id();
        if (sessions.get(channel_id)==null){
            return;
        }
        //send to everyone
        for (WebSocketSession session : sessions.get(channel_id)) {
            User user = userService.getUserById(user_id);
            String payload=String.format("{\"l_name\":\"%s\", \"f_name\":\"%s\",\"message\":\"%s\",\"files\":%s}",user.getLastname(),user.getFirstname(),messageSocket.getMessage(),messageSocket.getFilesString());
            session.sendMessage(new TextMessage(payload));
        }
    }

//    public void updateCurrentUsers(int channel_id){
//        for (WebSocketSession session : sessions.get(channel_id)){
//            session.sendMessage(new TextMessage(String.format("{\"info\"=updateCurrentUsers,%s",sessions.get(channel_id))));
//        }
//    }

}
