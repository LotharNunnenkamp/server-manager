package com.example.server_manager.repository;

import com.example.server_manager.model.Server;
import org.springframework.data.repository.CrudRepository;

public interface ServerRepository extends CrudRepository<Server, Long> {
    Server findByIpAddress(String ipAddress);
}
