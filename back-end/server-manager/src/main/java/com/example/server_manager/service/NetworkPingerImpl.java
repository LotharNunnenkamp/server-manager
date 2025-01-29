package com.example.server_manager.service;

import com.example.server_manager.enums.Status;

import java.io.IOException;
import java.net.InetAddress;

public class NetworkPingerImpl implements NetworkPinger{
    @Override
    public Status pingServer(String ipAddress) throws IOException {
        InetAddress inetAddress = InetAddress.getByName(ipAddress);
        return inetAddress.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN;
    }
}
