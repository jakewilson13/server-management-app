package io.getarrays.server.service.implementation;

import io.getarrays.server.enumeration.Status;
import io.getarrays.server.model.Server;
import io.getarrays.server.repository.ServerRepository;
import io.getarrays.server.service.ServerService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;

@Service
@Transactional
public class ServerServiceImpl implements ServerService {

    private Logger log;
    private final ServerRepository serverRepo;

    @Autowired
    public ServerServiceImpl(ServerRepository serverRepo, Logger log) {
        this.serverRepo = serverRepo;
        this.log = log;
    }

    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}...", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);  //finds the IP
        InetAddress address = InetAddress.getByName(ipAddress); //Inet represents an internet protocall (IP) address.
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);   //if it's true it's set to up, if not true then it's set to down
        serverRepo.save(server);    //save the server we pinged & returned it
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        return null;
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
