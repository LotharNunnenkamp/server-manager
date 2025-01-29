package com.example.server_manager.service;

import com.example.server_manager.enums.Status;

import java.io.IOException;
import java.net.UnknownHostException;

public interface NetworkPinger {
    Status pingServer(String ipAddress) throws IOException;
}
