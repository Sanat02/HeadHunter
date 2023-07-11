package com.example.demo.dao;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component

public class UserDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //поиск всех пользователей
    public List<User> getAllUsers() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    //поиск пользователей по имени
    public Optional<User> getUserByName(String accountname) {
        String sql = "select * from users where accountname=?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), accountname);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    //поиск пользователей по почте
    public Optional<User> getUserByEmail(String email) {
        String sql = "select * from users where email = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    //Проверка на наличие пользователя в системе. Пользователи уникально идентифицируются по email.
    public Boolean isExists(String email) {
        String sql = "select case\n" +
                "           when exists(select *\n" +
                "                       from users\n" +
                "                       where email = ?)\n" +
                "               then true\n" +
                "           else false\n" +
                "           end";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Boolean.class), email);
    }


}