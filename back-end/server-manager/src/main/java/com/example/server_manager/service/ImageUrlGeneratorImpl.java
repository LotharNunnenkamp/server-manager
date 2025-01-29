package com.example.server_manager.service;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Random;

@Component
public class ImageUrlGeneratorImpl implements ImageUrlGenerator{

    private final String[] serversImageNames = {"server1.png", "server2.png", "server3.png", "server4.png"};

    @Override
    public String generateServerImageUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/server/image/" + serversImageNames[new Random().nextInt(4)])
                .toUriString();
    }
}
