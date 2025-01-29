package com.example.server_manager.mapper;

import com.example.server_manager.dto.ServerDTO;
import com.example.server_manager.model.Server;
import org.springframework.stereotype.Component;

@Component
public class ServerMapper {

    public ServerDTO toDto(Server server){
        return ServerDTO.builder()
                .id(server.getId())
                .ipAddress(server.getIpAddress())
                .name(server.getName())
                .memory(server.getMemory())
                .type(server.getType())
                .imageUrl(server.getImageUrl())
                .status(server.getStatus())
                .build();
    }

    public Server toModel(ServerDTO serverDto){
        return Server.builder()
                .id(serverDto.getId())
                .ipAddress(serverDto.getIpAddress())
                .name(serverDto.getName())
                .memory(serverDto.getMemory())
                .type(serverDto.getType())
                .imageUrl(serverDto.getImageUrl())
                .status(serverDto.getStatus())
                .build();
    }

}
