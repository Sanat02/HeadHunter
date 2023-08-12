package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.UserDto;
import com.example.demo.enums.AccountType;
import com.example.demo.model.User;
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

    private final UserDao userDao;
    private final ProfileImageService profileImageService;
    private final PasswordEncoder encoder;


    public List<UserDto> getAllUsers() {
        log.info("Gol all users");
        List<User> users = userDao.getAllUsers();
        List<UserDto> userDtos = users.stream()
                .map(e -> UserDto.builder()
                        .id(e.getId())
                        .phoneNumber(e.getPhoneNumber())
                        .accountType(getAccountType(e.getRoleId()))
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
        Optional<User> mayBeUser = userDao.getUserByEmail(email);
        return mayBeUser;
    }

    public String isUserExist(String email) {
        try {

            Optional<User> user = userDao.getUserByEmail(email);
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
        return userDao.getUserById(id);
    }


    public List<UserDto> getAllJobSeekers() {
        log.info("Got all job seekers");
        List<User> users = userDao.getAllJobSeekers();
        List<UserDto> userDtos = users.stream()
                .map(e -> UserDto.builder()
                        .id(e.getId())
                        .accountName(e.getAccountName())
                        .accountType(AccountType.JOB_SEEKER)
                        .password(e.getPassword())
                        .phoneNumber(e.getPassword())
                        .email(e.getEmail())
                        .build()
                ).toList();
        return userDtos;
    }

    public List<UserDto> getAllEmployers() {
        log.info("Got all job employers");
        List<UserDto> employers = getAllUsers().stream()
                .filter(e -> e.getAccountType().equals(AccountType.EMPLOYER)).collect(Collectors.toList());
        return employers;
    }


    public int save(UserDto userDto) {
        int roleId = userDto.getAccountType().equals(AccountType.JOB_SEEKER) ? 2 : 1;
        log.info("The user:" + userDto.getEmail() + " is saved!");
        return userDao.save(User.builder()
                .accountName(userDto.getAccountName())
                .email(userDto.getEmail())
                .password(encoder.encode(userDto.getPassword()))
                .phoneNumber(userDto.getPhoneNumber())
                .enabled(true)
                .roleId(roleId)
                .build());

    }

    public void update(UserDto userDto) {
        log.info("The user:" + userDto.getEmail() + " is updated!");
        userDao.update(User.builder()
                .accountName(userDto.getAccountName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .id(userDto.getId())
                .build());
    }

    public UserDto mapToUserDto(User user) {
        if (user != null) {
            return UserDto.builder()
                    .id(user.getId())
                    .accountName(user.getAccountName())
                    .email(user.getEmail())
                    .accountType(getAccountType(user.getRoleId()))
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
