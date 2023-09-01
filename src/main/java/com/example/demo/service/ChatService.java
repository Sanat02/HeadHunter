package com.example.demo.service;

import com.example.demo.dao.ChatDao;
import com.example.demo.dto.ChatDto;
import com.example.demo.model.Chat;
import com.example.demo.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;

    public int saveMessage(ChatDto chatDto, Authentication auth) {
        Chat chat = Chat.builder()
                .message(chatDto.getMessage())
                .employerId(chatDto.getEmployerId())
                .userId(userService.getUserByEmail(auth.getName()).get().getId())
                .messageDate(LocalDate.now())
                .build();
        Chat savedChat = chatRepository.save(chat);
        return savedChat.getId();
    }

    public List<ChatDto> getMessagesByEmployerId(int userId, int employerId) {
        List<Chat> chats = chatRepository.findChatByEmployerIdAndUserId(employerId, userId);
        return chats.stream().map(e -> ChatDto.builder()
                .message(e.getMessage())
                .employerId(e.getEmployerId())
                .userId(e.getUserId())
                .messageDate(e.getMessageDate())
                .id(e.getId())
                .build()
        ).collect(Collectors.toList());
    }
}
