package io.getarrays.server.controller;


import io.getarrays.server.model.HttpResponse;
import io.getarrays.server.service.implementation.ServerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/server")
public class ServerController {
    private final ServerServiceImpl serverService;

    @Autowired
    public ServerController(ServerServiceImpl serverService) {
        this.serverService = serverService;
    }

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
}
