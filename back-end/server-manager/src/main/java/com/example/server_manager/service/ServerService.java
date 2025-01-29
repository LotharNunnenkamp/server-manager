package com.example.server_manager.service;

import com.example.server_manager.dto.ServerDTO;
import com.example.server_manager.model.Server;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {

    ServerDTO create(ServerDTO serverDTO);

    ServerDTO ping(String ipAddress) throws IOException;

    Collection<ServerDTO> list(int limit);

    ServerDTO get(Long id);

    ServerDTO update(ServerDTO serverDto);

    Boolean delete(Long id);

}
