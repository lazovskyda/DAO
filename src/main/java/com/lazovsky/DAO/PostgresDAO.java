package com.lazovsky.DAO;

import com.lazovsky.DAO.mappers.MP3Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.*;


@Component
public class PostgresDAO implements MP3Dao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insert(MP3 mp3) {
        String sql = "INSERT INTO \"MP3\" (name,author) VALUES (?,?)";
        jdbcTemplate.update(sql, new Object[]{mp3.getName(), mp3.getAuthor()});
    }

    @Override
    public void delete(MP3 mp3) {
        String sql = "DELETE FROM \"MP3\" WHERE(name = ?)";
        jdbcTemplate.update(sql, mp3.getName());
    }

    @Override
    public MP3 getById(int id) {
        String sql = "SELECT \"MP3\".id, \"MP3\".name, \"MP3\".author FROM \"MP3\" where (\"MP3\".id = ?)";
        MP3 mp3 = new MP3();

        mp3 = (MP3) jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper(MP3.class));

        return mp3;
    }

    @Override
    public List<MP3> getMP3ListByName(String name) {
        String sql = "SELECT \"MP3\".id, \"MP3\".name, \"MP3\".author FROM \"MP3\" where (\"MP3\".name = ?)";
        List<MP3> list;

        list = jdbcTemplate.query(sql, new Object[]{name}, new BeanPropertyRowMapper(MP3.class));
        return list;
    }

    @Override
    public List<MP3> getMP3ListByAuthor(String author) {
        String sql = "SELECT \"MP3\".id, \"MP3\".name, \"MP3\".author FROM \"MP3\" where (\"MP3\".author = ?) order by \"MP3\".id desc";
        List<MP3> list = new ArrayList<>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[]{author});
        rows.forEach((row) -> {
                    MP3 mp3 = new MP3();
                    mp3.setId((Integer) row.get("id"));
                    mp3.setName((String) row.get("name"));
                    mp3.setAuthor((String) row.get("author"));
                    list.add(mp3);
                }
        );
        return list;
    }
}
