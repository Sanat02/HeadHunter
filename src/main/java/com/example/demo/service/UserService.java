package com.example.demo.service;


import com.example.demo.dto.UserDto;
import com.example.demo.enums.AccountType;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {


    private final UserRepository userRepository;
    private final ProfileImageService profileImageService;
    private final PasswordEncoder encoder;
    private final RoleService roleService;


    public List<UserDto> getAllUsers() {
        log.info("Gol all users");
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map(e -> UserDto.builder()
                        .id(e.getId())
                        .phoneNumber(e.getPhoneNumber())
                        .accountType(getAccountType(e.getRole().getId().intValue()))
                        .password(e.getPassword())
                        .email(e.getEmail())
                        .accountName(e.getAccountName())
                        .profileImage(profileImageService.getImageByUserId(e.getId()))
                        .build()
                ).collect(Collectors.toList());

        return userDtos;
    }


    public Optional<User> getUserByEmail(String email) {
        log.info("Gol user by email:" + email);
        Optional<User> mayBeUser = userRepository.findUserByEmail(email);
        return mayBeUser;
    }

    public String isUserExist(String email) {
        try {
            Optional<User> user = userRepository.findUserByEmail(email);
            if (user.isPresent()) {
                log.error("User:" + email + "  exists!");
                return "1";
            } else {
                log.info("User:" + email + " does not exist!");
                return "0";
            }
        } catch (EmptyResultDataAccessException e) {
            log.error("Empty Result!");
            return "error";
        }
    }


    public Optional<User> getUserById(int id) {
        log.info("Got user by id:" + id);
        log.info("ROLE:"+userRepository.findById(id).get().getRole().getRole());
        return userRepository.findById(id);
    }


    public int save(UserDto userDto) {
        int roleId = userDto.getAccountType().equals(AccountType.JOB_SEEKER) ? 2 : 1;
        log.info("The user:" + userDto.getEmail() + " is saved!");
        return userRepository.save(User.builder()
                .accountName(userDto.getAccountName())
                .email(userDto.getEmail())
                .password(encoder.encode(userDto.getPassword()))
                .phoneNumber(userDto.getPhoneNumber())
                .enabled(true)
                .role(roleService.getRoleById(roleId))
                .build()).getId();

    }

    public void update(UserDto userDto) {
        log.info("The user:" + userDto.getEmail() + " is updated!");
        userRepository.updateUser(userDto.getId(), userDto.getAccountName(),
                userDto.getEmail(), userDto.getPassword(), userDto.getPhoneNumber());
    }

    public UserDto mapToUserDto(User user) {
        if (user != null) {
            return UserDto.builder()
                    .id(user.getId())
                    .accountName(user.getAccountName())
                    .email(user.getEmail())
                    .accountType(getAccountType(user.getRole().getId().intValue()))
                    .password(user.getPassword())
                    .phoneNumber(user.getPhoneNumber())
                    .profileImage(profileImageService.getImageByUserId(user.getId()))
                    .build();
        } else {
            return null;
        }
    }

    public AccountType getAccountType(int num) {
        if (num == 1) {
            return AccountType.EMPLOYER;
        }
        return AccountType.JOB_SEEKER;
    }
}
