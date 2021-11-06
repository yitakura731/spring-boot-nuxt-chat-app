package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.api.data.AppUser;
import com.example.demo.api.data.Member;
import com.example.demo.api.data.Room;
import com.example.demo.api.data.Talk;
import com.example.demo.api.request.CreateRoomRequest;
import com.example.demo.auth.JwtAuthentication;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.TalkRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.data.BaseMember;
import com.example.demo.repository.data.BaseRoom;
import com.example.demo.repository.data.BaseTalk;
import com.example.demo.repository.data.BaseUser;
import com.example.demo.util.LambdaUtil;
import com.example.demo.util.Tools;

@Service
public class AppService {

    private final AuthService authService;

    private final RoomRepository roomRepository;

    private final TalkRepository roomItemRepository;

    private final UserRepository userRepository;

    @Autowired
    AppService(
            AuthService authService,
            RoomRepository roomRepository,
            TalkRepository roomItemRepository,
            UserRepository userRepository) {
        this.authService = authService;
        this.roomRepository = roomRepository;
        this.roomItemRepository = roomItemRepository;
        this.userRepository = userRepository;
    }

    public List<AppUser> getUsers(int limit, int offset) throws Exception {
        AppUser currUser = authService.getCurrentUser();
        List<BaseUser> baseUsers = userRepository.findByIdNot(currUser.getId());
        return baseUsers.stream().
                map(e -> AppUser.builder().
                        id(e.getId()).
                        userId(e.getUsername()).
                        email(e.getEmail()).
                        name(e.getName()).
                        build()).
                collect(Collectors.toList());
    }

    public Room searchRoomFromMember(CreateRoomRequest request) throws Exception {
        Iterable<BaseRoom> rooms = roomRepository.findAll();
        for(BaseRoom room : rooms) {
            List<Long> memberIds = room.getMembers().stream().
                    map(e -> e.getId()).
                    collect(Collectors.toList());
            if (memberIds.equals(request.getUserIds())) {
                return toRoom(room);
            };
        }
        return null;
    }

    public Room createRoom(CreateRoomRequest request) throws Exception {
        List<BaseMember> memberIds = this.toMemberIds(request);
        BaseRoom baseTalkRoom = roomRepository.save(
                BaseRoom.builder().
                createDate(Tools.nowDateTime()).
                updateDate(Tools.nowDateTime()).
                members(memberIds).
                build());
        return toRoom(baseTalkRoom);
    }

    public List<Room> getRooms(int limit, int offset) throws Exception {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateDate");
        PageRequest pageRequest = PageRequest.of(offset, limit, sort);
        Page<BaseRoom> page = roomRepository.findAll(pageRequest);
        return page.toList().stream().
                map(LambdaUtil.rethrowFunction(e -> toRoom(e))).
                collect(Collectors.toList());
    }

    public List<Talk> getAllTalks(long roomId) throws Exception {
        List<BaseTalk> talkItems =
                roomItemRepository.findByRoomIdOrderByCreateDateAsc(roomId);
        return talkItems.stream().
                map(LambdaUtil.rethrowFunction(e -> toTalk(e))).
                collect(Collectors.toList());
    }

    private Talk toTalk(BaseTalk baseTalk) {
        BaseUser user = userRepository.findById(baseTalk.getUserId()).get();
        return Talk.builder().
                id(baseTalk.getId()).
                user(Tools.toAppUser(user)).
                date(baseTalk.getCreateDate()).
                message(baseTalk.getMessage()).
                build();
    }

    public Talk addTalk(
            long roomId,
            JwtAuthentication user,
            String message) throws Exception {
        BaseTalk talkItem = roomItemRepository.save(BaseTalk.builder().
                userId(user.getId()).
                createDate(LocalDateTime.now()).
                message(message).
                roomId(roomId).
                build());
        return Talk.builder().
                id(talkItem.getId()).
                user(Tools.toAppUser(user)).
                date(talkItem.getCreateDate()).
                message(talkItem.getMessage()).
                build();
    }

    private List<BaseMember> toMemberIds(CreateRoomRequest request) throws Exception {
        List<BaseMember> retVal = request.getUserIds().stream().
                map(e -> BaseMember.builder().userId(e).build()).
                collect(Collectors.toList());
        AppUser currUser = authService.getCurrentUser();
        retVal.add(BaseMember.builder().
                userId(currUser.getId()).
                build());
        return retVal;
    }

    private Room toRoom(BaseRoom baseRoom) throws Exception {
        String latestMessage = null;
        LocalDateTime latestDate = null;
        List<Member> members = new ArrayList<>();
        for (BaseMember bm : baseRoom.getMembers()) {
            BaseUser user = userRepository.findById(bm.getUserId()).get();
            members.add(Member.builder().
                    id(bm.getId()).
                    name(user.getName()).
                    userId(user.getUserId()).
                    build());
        }
        List<BaseTalk> items = roomItemRepository.
                findByRoomIdOrderByCreateDateDesc(baseRoom.getId());
        if (items != null && items.size() > 0) {
            BaseTalk latestItem =items.get(0);
            latestMessage = latestItem.getMessage();
            latestDate = latestItem.getCreateDate();
        }
        return Room.builder().
                id(baseRoom.getId()).
                members(members).
                latestDate(latestDate).
                latestMessage(latestMessage).
                build();
    }
}