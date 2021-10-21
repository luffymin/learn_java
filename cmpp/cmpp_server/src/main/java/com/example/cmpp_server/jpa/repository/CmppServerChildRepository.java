package com.example.cmpp_server.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.cmpp_server.jpa.model.CmppServerChild;

@Repository
public interface CmppServerChildRepository extends CrudRepository<CmppServerChild, Long> {

}