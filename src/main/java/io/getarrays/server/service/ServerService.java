package io.getarrays.server.service;


import io.getarrays.server.model.Server;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {    //defining features we want inside of the application
    Server create(Server server);   //creating a new server that a user wants to create
    Server ping(String ipAddress) throws IOException;
    Collection<Server> list(int limit); //limiting the number of servers that will be pulled from the database
    Server get(Long id);
    Server update(Server server);
    Boolean delete(Long id);
}
