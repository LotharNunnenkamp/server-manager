package com.example.server_manager.service;

import com.example.server_manager.dto.ServerDTO;
import com.example.server_manager.enums.Status;
import com.example.server_manager.mapper.ServerMapper;
import com.example.server_manager.model.Server;
import com.example.server_manager.repository.ServerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;
    private final ServerMapper serverMapper;
    private final ImageUrlGeneratorImpl imageUrlGenerator;
    private final NetworkPingerImpl networkPinger;

    @Override
    public ServerDTO create(ServerDTO serverDto) {
        log.info("Saving new server: {}", serverDto.getName());
        Server server = serverMapper.toModel(serverDto);
        server.setImageUrl(imageUrlGenerator.generateServerImageUrl());
        Server serverPersisted = serverRepository.save(server);
        return serverMapper.toDto(serverPersisted);
    }

    @Override
    public ServerDTO ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        server.setStatus(networkPinger.pingServer(ipAddress));
        serverRepository.save(server);
        return serverMapper.toDto(server);
    }

    @Override
    public Collection<ServerDTO> list(int limit) {
        log.info("Fetching all servers");
        List<Server> servers = serverRepository.findAll(PageRequest.of(0, limit)).toList();
        List<ServerDTO> serversDto = new ArrayList<>();
        servers.forEach(server -> {
            serversDto.add(serverMapper.toDto(server));
        });
        return serversDto;
    }

    @Override
    public ServerDTO get(Long id) {
        log.info("Fetching server by id: {}", id);
        Server serverFound = serverRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Server not found for id: " + id));
        return serverMapper.toDto(serverFound);
    }

    @Override
    public ServerDTO update(ServerDTO serverDto) {
        log.info("Updating server: {}", serverDto.getName());
        Server server = serverMapper.toModel(serverDto);
        Server serverUpdated = serverRepository.save(server);
        return serverMapper.toDto(serverUpdated);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server id: {}", id);
        serverRepository.deleteById(id);
        return Boolean.TRUE;
    }
}
