/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.websocket.encoders;

import com.dn.websocket.messages.ChatMessage;
import com.dn.websocket.messages.UseridMessage;
import java.io.StringWriter;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author tommypho
 */
public class UseridMessageEncoder implements Encoder.Text<UseridMessage> {
    
    @Override
    public void init(EndpointConfig ec) { }
    
    @Override
    public void destroy() { }
    
    @Override
    public String encode(UseridMessage object) throws EncodeException {
         StringWriter swriter = new StringWriter();
        try (JsonGenerator jsonGen = Json.createGenerator(swriter)) {
            jsonGen.writeStartObject()
                .write("type", "UseridMessage")
                .write("userid", object.getUserid())
                .writeEnd();
        }
        return swriter.toString();}
}
