/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.websocket.messages;

/**
 *
 * @author DatNguyen
 */
public class SetupMessage extends Message{
    private String type;

    @Override
    public String toString() {
        return "SetupMessage{" + "type=" + type + ", content=" + content + '}';
    }

    public SetupMessage(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    private String content;
    
}
