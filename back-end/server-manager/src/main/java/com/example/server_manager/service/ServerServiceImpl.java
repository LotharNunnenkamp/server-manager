package com.example.server_manager.service;

import com.example.server_manager.dto.ServerDTO;
import com.example.server_manager.enums.Status;
import com.example.server_manager.model.Server;
import com.example.server_manager.repository.ServerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    @Override
    public ServerDTO create(ServerDTO serverDto) {
        log.info("Saving new server: {}", serverDto.getName());
        Server server = convertDtoToModel(serverDto);
        Server serverPersisted = serverRepository.save(server);
        return convertModelToDto(serverPersisted);
    }

    @Override
    public ServerDTO ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress inetAddress = InetAddress.getByName(ipAddress);
        server.setStatus(inetAddress.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepository.save(server);
        return convertModelToDto(server);
    }

    @Override
    public Collection<ServerDTO> list(int limit) {
        log.info("Fetching all servers");
        List<Server> servers = serverRepository.findAll(PageRequest.of(0, limit)).toList();
        List<ServerDTO> serversDto = new ArrayList<>();
        servers.forEach(server -> {
            serversDto.add(convertModelToDto(server));
        });
        return serversDto;
    }

    @Override
    public ServerDTO get(Long id) {
        log.info("Fetching server by id: {}", id);
        Server serverFound = serverRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Server not found for id: " + id));
        return convertModelToDto(serverFound);
    }

    @Override
    public ServerDTO update(ServerDTO serverDto) {
        log.info("Updating server: {}", serverDto.getName());
        Server server = convertDtoToModel(serverDto);
        Server serverUpdated = serverRepository.save(server);
        return convertModelToDto(serverUpdated);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server id: {}", id);
        serverRepository.deleteById(id);
        return Boolean.TRUE;
    }

    private String setServerImageUrl() {
        String[] serversImageNames = {"server1.png", "server2.png", "server3.png", "server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/server/image/" + serversImageNames[new Random().nextInt(4)])
                .toUriString();
    }

    private ServerDTO convertModelToDto(Server server){
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

    private Server convertDtoToModel(ServerDTO serverDto){
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
