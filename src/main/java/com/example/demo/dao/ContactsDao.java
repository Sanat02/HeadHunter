package com.example.demo.dao;

import com.example.demo.model.Contacts;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component

public class ContactsDao extends BaseDao {


    ContactsDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    public List<Contacts> getContactsByResumeId(int id) {
        String sql = "select * from contacts where resume_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Contacts.class), id);
    }

    @Override
    public int save(Object obj) {
        Contacts contacts=(Contacts) obj;
        String sql = "INSERT INTO contacts (resume_id, type, \"value\") " +
                "VALUES (?, ?, ?)";
        jdbcTemplate.update(con->{
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setInt(1,contacts.getResume().getId());
            ps.setString(2,contacts.getType().toString());
            ps.setString(3, contacts.getValue());
            return ps;
        },keyHolder);
        return  Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Object obj) {

    }
}
