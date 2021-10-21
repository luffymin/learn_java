package com.example.cmpp_server.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "server_child")
@AllArgsConstructor
@Data
public class CmppServerChild implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;
    private String password;
    private String spCode;
    private String serviceId;
    private String msgSrc;
};