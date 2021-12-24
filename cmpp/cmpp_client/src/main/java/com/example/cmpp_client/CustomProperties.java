package com.example.cmpp_client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;

@Component
public class CustomProperties {

    @Value("${msg.msgContent}")
    private String msgContent;

    @Value("${msg.destterminalId}")
    private String destterminalId;

    public String getMsgContent() throws UnsupportedEncodingException {
        return new String(msgContent.getBytes("ISO_8859_1"));
    }

    public String getDestterminalId() {
        return destterminalId;
    }

}