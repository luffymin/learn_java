package com.example.cmpp_server.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.cmpp_server.jpa.model.CmppServerChild;
import com.example.cmpp_server.jpa.repository.CmppServerChildRepository;


@Service
public class CmppServerChildService {

    @Autowired
    private CmppServerChildRepository repository;

    public List<CmppServerChild> findAll() {
        return (List<CmppServerChild>) repository.findAll();


    }

}