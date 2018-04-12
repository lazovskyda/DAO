package com.lazovsky.DAO;

import com.lazovsky.DAO.exceptions.MyException;
import com.lazovsky.DAO.mappers.MP3Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


@Component
public class PostgresDAO implements MP3Dao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void insert(MP3 mp3) throws BadSqlGrammarException{
//        String sql = "INSERT INTO \"MP3\" (name,author) VALUES (?,?)";
//        jdbcTemplate.update(sql, new Object[]{mp3.getName(), mp3.getAuthor()});

        try{
            MapSqlParameterSource params = new MapSqlParameterSource();
            String sql = "INSERT INTO \"MP3\" (name,author) VALUES (:name,:author)";

            params.addValue("name", mp3.getName());
            params.addValue("author", mp3.getAuthor());

            jdbcTemplate.update(sql, params);


        }catch (BadSqlGrammarException ex){
            System.err.println("something Wrong!!");


        }



    }

    @Override
    public void delete(MP3 mp3) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = "DELETE FROM \"MP3\" WHERE(name = :name)";
        params.addValue("name", mp3.getName());
        jdbcTemplate.update(sql, params);
    }

    @Override
    public MP3 getById(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = "SELECT \"MP3\".id, \"MP3\".name, \"MP3\".author FROM \"MP3\" where (\"MP3\".id = :id)";
        MP3 mp3 = new MP3();

        params.addValue("id", id);

        mp3 = (MP3) jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper(MP3.class));

        return mp3;
    }

    @Override
    public List<MP3> getMP3ListByName(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = "SELECT \"MP3\".id, \"MP3\".name, \"MP3\".author FROM \"MP3\" where (\"MP3\".name = :name)";
        List<MP3> list;
        params.addValue("name", name);

        list = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper(MP3.class));
        return list;
    }

    @Override
    public List<MP3> getMP3ListByAuthor(String author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = "SELECT \"MP3\".id, \"MP3\".name, \"MP3\".author FROM \"MP3\" where (\"MP3\".author = :author) order by \"MP3\".id desc";
        List<MP3> list = new ArrayList<>();

        params.addValue("author", author);
        list = jdbcTemplate.query(sql, params, new MP3RowMapper());
//        rows.forEach((row) -> {
//                    MP3 mp3 = new MP3();
//                    mp3.setId((Integer) row.get("id"));
//                    mp3.setName((String) row.get("name"));
//                    mp3.setAuthor((String) row.get("author"));
//                    list.add(mp3);
//                }
//        );
        return list;
    }

    private static final class MP3RowMapper implements RowMapper<MP3>{
        @Override
        public MP3 mapRow(ResultSet resultSet, int i) throws SQLException {
            MP3 mp3 = new MP3();
            mp3.setId(resultSet.getInt("id"));
            mp3.setName(resultSet.getString("name"));
            mp3.setName(resultSet.getString("author"));
            return mp3;
        }
    }

}
