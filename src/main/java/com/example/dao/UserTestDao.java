package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.example.model.User;

@Component
public class UserTestDao {
    
    @Autowired
    private JdbcTemplate secondJdbcTemplate;
    
    private static String add = "insert into user_test(name, password) values (?,?)";
    private static String get = "select * from user_test where id = ?";
    
    public boolean add(User user){
        if(user == null){
            return false;
        }
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        secondJdbcTemplate.update(new PreparedStatementCreator() {
            
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                if(conn != null){
                    PreparedStatement ps = conn.prepareStatement(add, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getPassword());
                    return ps;
                }
                return null;
            }
        }, keyHolder);
        if(keyHolder.getKey().longValue() > 0){
            user.setId(keyHolder.getKey().intValue());
            return true;
        }
        return false;
    }
    
    public User get(int id){
        if(id <= 0){
            return null;
        }
        return secondJdbcTemplate.query(get, new Object[]{id}, new ResultSetExtractor<User>() {

            @Override
            public User extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()){
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
                return null;
            }
        });
    }
    
}
