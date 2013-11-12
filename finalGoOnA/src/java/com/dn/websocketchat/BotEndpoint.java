
package com.dn.websocketchat;



import com.dn.entity.Messages;
import com.dn.websocket.decoders.MessageDecoder;
import com.dn.websocket.encoders.ChatMessageEncoder;
import com.dn.websocket.encoders.SetupMessageEncoder;
import com.dn.websocket.encoders.UseridMessageEncoder;
import com.dn.websocket.messages.ChatMessage;
import com.dn.websocket.messages.Message;
import com.dn.websocket.messages.SetupMessage;
import com.dn.websocket.messages.UseridMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


    /* Websocket endpoint */
    @ServerEndpoint(
        value = "/chatchat",
        decoders = {MessageDecoder.class}, 
        encoders = {ChatMessageEncoder.class, UseridMessageEncoder.class, SetupMessageEncoder.class}
        )
    public class BotEndpoint {

   
    /* Executor service for asynchronous processing */

    @OnOpen
    public void openConnection(Session session) throws IOException, EncodeException {
        SetupMessage setupMessage = new SetupMessage("SetupMessage", "send me your userid");
        session.getBasicRemote().sendObject(setupMessage);
        
    }
    
    @OnClose
    public void closedConnection(Session session) {
       
    }
    
     @OnError
    public void error(Session session, Throwable t) {
        
    }
    
    @OnMessage
    public void message(final Session session, Message msg) throws IOException, EncodeException {
        if (msg instanceof UseridMessage)
        {
            session.getUserProperties().put("userid", ((UseridMessage) msg).getUserid());
            SetupMessage setupMessage = new SetupMessage("SetupMessage", "chat starting");
            session.getBasicRemote().sendObject(setupMessage);
        }
        
        if (msg instanceof ChatMessage)
        {
            ChatMessage cmsg = (ChatMessage) msg;
           ApiRestClient apiRestClient = new ApiRestClient();
          // MessageRestClient messageRestClient = new MessageRestClient();
           Messages m = new Messages();
            m.setSender(cmsg.getSender());
            m.setReceiver(cmsg.getReceiver());
            m.setContent(cmsg.getContent());
            m.setStatus("un-seen");
            //messageRestClient.create_JSON(m);
            String messageid = apiRestClient.createMessage_JSON(m);
            cmsg.setMessageid(Integer.parseInt(messageid));
            
            try {
                for (Session s : session.getOpenSessions()) {
                   if (s.getUserProperties().get("userid").equals(cmsg.getReceiver()))
                        s.getBasicRemote().sendObject(cmsg);
                }
            } catch (IOException | EncodeException e) {}   
        }
        
    }

    public synchronized void sendAll(Session session, ChatMessage msg) {
        try {
            for (Session s : session.getOpenSessions()) {
               s.getBasicRemote().sendObject(msg);
            }
        } catch (IOException | EncodeException e) {}   
    }
}



