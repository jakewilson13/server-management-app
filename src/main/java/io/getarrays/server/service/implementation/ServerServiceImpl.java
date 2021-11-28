package io.getarrays.server.service.implementation;

import io.getarrays.server.enumeration.Status;
import io.getarrays.server.model.Server;
import io.getarrays.server.repository.ServerRepository;
import io.getarrays.server.service.ServerService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

@Service
@Transactional  //spring dynamically creates a proxy that implements the same interface(s) as the class you're annotating
                //when clients make calls into your object, the calls are intercepted and the behaviors injected via the proxy mechanism
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
        serverRepo.save(server);    //save the server we pinged & returned it                           //Test whether that address is reachable. Best effort is made by the implementation to try to reach the host, but firewalls and server configuration may block requests resulting in a unreachable status while some specific ports may be accessible
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers...");
        return serverRepo.findAll(PageRequest.of(0, limit)).toList();   //getting the first page (starting from the beginning) and then passing in our limit then returning a list
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id...");
        return serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: {}...", server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by ID: {}...", id);
        serverRepo.deleteById(id);
        return Boolean.TRUE;
    }

    private String setServerImageUrl() {
        String[] imageNames = {"server-image-1", "server-image-2", "server-image-3", "server-image-4"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imageNames[new Random().nextInt(4)]).toUriString(); //making sure the random number doesn't exceed 4 inside of the index
    }
}
