/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.websocket.encoders;

import com.dn.websocket.messages.SetupMessage;
import com.dn.websocket.messages.UseridMessage;
import java.io.StringWriter;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author DatNguyen
 */
public class SetupMessageEncoder implements Encoder.Text<SetupMessage> {
    
    @Override
    public void init(EndpointConfig ec) { }
    
    @Override
    public void destroy() { }
    
    @Override
    public String encode(SetupMessage object) throws EncodeException {
         StringWriter swriter = new StringWriter();
        try (JsonGenerator jsonGen = Json.createGenerator(swriter)) {
            jsonGen.writeStartObject()
                .write("type", "SetupMessage")
                .write("content", object.getContent())
                .writeEnd();
        }
        return swriter.toString();}
}
