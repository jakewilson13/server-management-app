package io.getarrays.server.controller;


import io.getarrays.server.enumeration.Status;
import io.getarrays.server.model.HttpResponse;
import io.getarrays.server.model.Server;
import io.getarrays.server.service.implementation.ServerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
public class ServerController {
    private final ServerServiceImpl serverService;

    @Autowired
    public ServerController(ServerServiceImpl serverService) {
        this.serverService = serverService;
    }

    @GetMapping("/list")
    public ResponseEntity<HttpResponse> getServers() {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timStamp(LocalDateTime.now())
                        .data(Map.of("servers", serverService.list(30)))
                        .message("Servers retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<HttpResponse> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timStamp(LocalDateTime.now())
                        .data(Map.of("server", server))
                        .message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed") //if the status equals server_up then it will return "Ping success" if not then "ping failed"
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<HttpResponse> getServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timStamp(LocalDateTime.now())
                        .data(Map.of("server", serverService.get(id)))
                        .message("Server retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<HttpResponse> saveServer(@RequestBody @Valid Server server) { //grabbing the request body that contains the server, @valid aligns with the @notempty validation annotation inside of our server model class
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timStamp(LocalDateTime.now())
                        .data(Map.of("server", serverService.create(server)))
                        .message("Server created") //if the status equals server_up then it will return "Ping success" if not then "ping failed"
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> deleteServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timStamp(LocalDateTime.now())
                        .data(Map.of("deleted", serverService.delete(id)))  //will be true or false if it's deleted (won't see the false because it will throw an exception)
                        .message("Server deleted")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE) //has to be the same path we defined in the server image method inside of our service & only produces png content
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Desktop/server/" + fileName));
    }
}
