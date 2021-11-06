package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.data.AppUser;
import com.example.demo.api.data.Room;
import com.example.demo.api.data.Talk;
import com.example.demo.api.request.CreateRoomRequest;
import com.example.demo.auth.JwtAuthentication;
import com.example.demo.service.AppService;

@RestController
@RequestMapping(value = "/api/v1")
public class AppController {

    private final AppService appService;

    @Autowired
    public AppController(
            AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/users")
    public List<AppUser> getUsers(
            @RequestParam("limit") int limit,
            @RequestParam("offset") int offset) throws Exception {
        return appService.getUsers(limit, offset);
    }

    @PostMapping("/rooms")
    public Room createRoom(
            @RequestBody CreateRoomRequest request) throws Exception {
        Room room = appService.searchRoomFromMember(request);
        if (room != null) {
            return room;
        } else {
            return appService.createRoom(request);
        }
    }

    @GetMapping("/rooms")
    public List<Room> getRooms(
            @RequestParam("limit") int limit,
            @RequestParam("offset") int offset) throws Exception {
        return appService.getRooms(limit, offset);
    }

    @GetMapping("/rooms/{roomId}/talks")
    public List<Talk> getTalks(
            @PathVariable("roomId") Long roomId,
            @RequestParam("limit") int limit,
            @RequestParam("offset") int offset) throws Exception {
        return appService.getAllTalks(roomId);
    }

    @MessageMapping(value="/hello/{roomId}")
    @SendTo(value="/topic/greetings/{roomId}")
    public Talk greet(
            @DestinationVariable("roomId") Long roomId,
            @Payload String message,
            Principal principal) throws Exception {
        JwtAuthentication auth = (JwtAuthentication) principal;
        return appService.addTalk(roomId, auth, message);
    }
}
