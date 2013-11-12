/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.websocket.messages;

/**
 *
 * @author tommypho
 */
public class UseridMessage extends Message  {
    private String type;

    @Override
    public String toString() {
        return "UseridMessage{" + "type=" + type + ", userid=" + userid + '}';
    }

    public UseridMessage(String type, String userid) {
        this.type = type;
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    private String userid;

   
    
}
