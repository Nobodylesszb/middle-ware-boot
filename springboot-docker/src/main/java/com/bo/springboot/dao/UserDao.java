package com.bo.springboot.dao;

import com.bo.springboot.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void insert() {
        String time = String.valueOf(System.currentTimeMillis());
        String sql = "insert into t_user (username) values ('idea-" + time + "')";
        jdbcTemplate.update(sql);
        System.out.println("==========执行插入语句==========");
    }

    class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User unitPO = new User();
            unitPO.setId(resultSet.getInt("id"));
            unitPO.setUsername(resultSet.getString("username"));
            return unitPO;
        }
    }
}