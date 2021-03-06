package com.lazovsky.DAO;

import com.lazovsky.DAO.exceptions.MyException;
import com.lazovsky.DAO.mappers.MP3Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.beans.Transient;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


@Component
public class PostgresDAO implements MP3Dao {
    private SimpleJdbcInsert insertMp3;
    private NamedParameterJdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    @Qualifier("mp3Table")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertMp3 = new SimpleJdbcInsert(dataSource).withTableName("\"MP3\"").usingColumns("name", "author_id");

    }

    @Override
    @Transactional
    public void insert(MP3 mp3) {
        String sqlAuthor = "INSERT INTO Author (name) VALUES (:name)";
        Author author = mp3.getAuthor();
        Object obj = new Object();


        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", author.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sqlAuthor, params, keyHolder, new String[]{"id"});
        int author_id = keyHolder.getKey().intValue();


        String sqlMP3 = "INSERT INTO \"MP3\" (name,author_id) VALUES (:name,:authorId)";

        params = new MapSqlParameterSource();
        params.addValue("name", mp3.getName());
        params.addValue("authorId", author_id);
        jdbcTemplate.update(sqlMP3, params);


    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, timeout = 1)
    public void insertMP3(MP3 mp3) {

        System.out.println(TransactionSynchronizationManager.isActualTransactionActive());
        int author_id;
        try{
            author_id = insertAuthor(mp3.getAuthor());
        }catch (TransactionTimedOutException ex){
            System.out.println("ups");
            author_id = 1;
        }


        String sqlMP3 = "INSERT INTO \"MP3\" (name,author_id) VALUES (:name,:authorId)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", mp3.getName());
        params.addValue("authorId", author_id);

        try{
            jdbcTemplate.update(sqlMP3, params);
        }catch (TransactionTimedOutException ex){
            System.out.println("ups time");
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int insertAuthor(Author author) {
        System.out.println(TransactionSynchronizationManager.isActualTransactionActive());

        String sqlAuthor = "INSERT INTO Author (name) VALUES (:name)";
        //Author author = mp3.getAuthor();
        try {
            Thread.sleep(500);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", author.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sqlAuthor, params, keyHolder, new String[]{"id"});
        int author_id = keyHolder.getKey().intValue();
        return author_id;
    }


    public Author getAuthorByName(MP3 mp3) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        Author author = new Author();
        String sql = "SELECT * FROM author WHERE author.name = :author";

        params.addValue("author", mp3.getAuthor().getName());
        try {
            author = (Author) jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper(Author.class));
            System.out.println("has this author");
            System.out.println(author.getId());
            System.out.println(author.getName());

        } catch (EmptyResultDataAccessException ex) {
            System.out.println("Don't have this author");
            ex.printStackTrace();
            author = null;
        }

        return author;
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
        String sql = "SELECT \"MP3\".id, \"MP3\".name, author.id AS author_id, author.name AS author_name FROM \"MP3\" LEFT JOIN author ON (\"MP3\".author_id = author.id) WHERE (\"MP3\".id = :id)";
        MP3 mp3 = new MP3();

        params.addValue("id", id);

        mp3 = (MP3) jdbcTemplate.queryForObject(sql, params, new MP3RowMapper());

        return mp3;
    }


    public List<MP3> getByName(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = "SELECT \"MP3\".id, \"MP3\".name, author.id AS author_id, author.name AS author_name FROM \"MP3\" LEFT JOIN author ON (\"MP3\".author_id = author.id) WHERE (\"MP3\".name = :name)";
        List<MP3> list;

        params.addValue("name", name);

        list = jdbcTemplate.query(sql, params, new MP3RowMapper());

        return list;
    }


    @Override
    @Deprecated
    public List<MP3> getMP3ListByName(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = "SELECT \"MP3\".id, \"MP3\".name, author.id AS author_id, author.name AS author_name FROM \"MP3\" LEFT JOIN author ON (\"MP3\".author_id = author.id) WHERE (\"MP3\".name = :name)";
        List<MP3> list;
        Object obj = new Object();
        params.addValue("name", name);

        list = jdbcTemplate.query(sql, params, new MP3RowMapper());
        return list;
    }

    @Override
    public List<MP3> getMP3ListByAuthor(String author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = "SELECT \"MP3\".id, \"MP3\".name, author.id AS author_id, author.name AS author_name FROM \"MP3\" LEFT JOIN author ON (\"MP3\".author_id = author.id) WHERE (author.name = :author) ORDER BY \"MP3\".id DESC";
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

    public void batchInsert(List<MP3> listMP3) {

        listMP3.forEach(
                (mp3) -> {
                    MapSqlParameterSource params = new MapSqlParameterSource();


                    if (getAuthorByName(mp3) == null) {
                        MapSqlParameterSource authorParams = new MapSqlParameterSource();
                        String sql = "INSERT INTO author (name) VALUES (:name)";
                        authorParams.addValue("name", mp3.getAuthor().getName());
                        jdbcTemplate.update(sql, authorParams);

                    }
                    //author = getAuthorByName(mp3);
                    mp3.setAuthor(getAuthorByName(mp3));

                    String sql = "INSERT INTO \"MP3\" (name,author_id) VALUES (:name,:authorId)";

                    params.addValue("name", mp3.getName());
                    params.addValue("authorId", mp3.getAuthor().getId());

                    jdbcTemplate.update(sql, params);

                }
        );

    }

    private static final class MP3RowMapper implements RowMapper<MP3> {
        @Override
        public MP3 mapRow(ResultSet resultSet, int i) throws SQLException {
            MP3 mp3 = new MP3();
            Author author = new Author();
            mp3.setId(resultSet.getInt("id"));
            mp3.setName(resultSet.getString("name"));
            author.setId(resultSet.getInt("author_id"));
            author.setName(resultSet.getString("author_name"));

            mp3.setAuthor(author);

            return mp3;
        }
    }

    private static final class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            Author author = new Author();
            author.setId(resultSet.getInt("id"));
            author.setName(resultSet.getString("name"));
            return author;
        }
    }


}
