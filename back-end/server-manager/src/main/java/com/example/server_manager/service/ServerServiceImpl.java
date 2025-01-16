package com.example.server_manager.service;

import com.example.server_manager.enums.Status;
import com.example.server_manager.model.Server;
import com.example.server_manager.repository.ServerRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@Transactional
public class ServerServiceImpl implements ServerService {

    @Autowired
    private ServerRepository serverRepository;

    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }

    @Override
    public Server ping(String ipAddress) {
        return null;
    }

    @Override
    public Collection<Server> list(int limit) {
        return List.of();
    }

    @Override
    public Server get(Long id) {
        return null;
    }

    @Override
    public Server update(Server server) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    private String setServerImageUrl() {
        return null;
    }
}
