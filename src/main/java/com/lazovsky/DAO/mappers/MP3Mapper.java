package com.lazovsky.DAO.mappers;

import com.lazovsky.DAO.MP3;

import javax.swing.tree.RowMapper;
import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MP3Mapper implements RowMapper {
    public MP3 mapRaw(ResultSet rs, int rowNum) throws SQLException{
        MP3 mp3 = new MP3();
        mp3.setId(rs.getInt("id"));
        mp3.setName(rs.getString("name"));
        mp3.setAuthor(rs.getString("author"));
        return mp3;
    }

    @Override
    public int[] getRowsForPaths(TreePath[] path) {
        return new int[0];
    }
}
