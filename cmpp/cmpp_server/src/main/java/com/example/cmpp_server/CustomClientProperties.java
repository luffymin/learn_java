package com.example.cmpp_server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomClientProperties {

    @Value("${client.id}")
    private String id;

    @Value("${client.msgSrc}")
    private String msgSrc;

    @Value("${client.spCode}")
    private String spCode;

    @Value("${client.serviceId}")
    private String serviceId;

    @Value("${client.groupName}")
    private String groupName;

    @Value("${client.userName}")
    private String userName;

    @Value("${client.password}")
    private String password;

    public String getId() {
        return id;
    }

    public String getMsgSrc() {
        return msgSrc;
    }

    public String getSpCode() {
        return spCode;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}