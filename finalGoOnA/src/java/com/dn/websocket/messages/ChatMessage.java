
package com.dn.websocket.messages;

/* Represents a chat message */
public class ChatMessage extends Message {
    private String type;

    public ChatMessage(String type, String sender, String receiver, String content, int messageid) {
        this.type = type;
        this.messageid = messageid;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }
    private int messageid;

    

    public int getMessageid() {
        return messageid;
    }

    public void setMessageid(int messageid) {
        this.messageid = messageid;
    }

    @Override
    public String toString() {
        return "ChatMessage{" + "type=" + type + ", messageid=" + messageid + ", sender=" + sender + ", receiver=" + receiver + ", content=" + content + '}';
    }

    

  
    private String sender;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    private String receiver;
    private String content;
   
   
    
   
}
