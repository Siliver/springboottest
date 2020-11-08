package com.siliver.ch1.dao;

import com.siliver.ch1.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao2 {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 对JdbcTemplate类进行了封装从而支持命名参数特性
     */
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 使用jdbc模板对象进行？内容的解析替换
     * @param departmentId
     * @return
     */
    public Integer totalUserInDepartment(Long departmentId){
        String sql="SELECT count(1) FROM test.user WHERE department_id=?";
        Integer count=jdbcTemplate.queryForObject(sql,Integer.class,departmentId);
        return count;
    }

    /**
     * 使用namedParameterJdbcTemplate 进行命名特性的替换
     * @param departmentId
     * @return
     */
    public Integer totalUserInDepartment2(Long departmentId){
        String sql="SELECT count(1) FROM test.user WHERE department_id=:deptId";
        MapSqlParameterSource namedParameters=new MapSqlParameterSource();
        namedParameters.addValue("deptId",departmentId);
        Integer count=namedParameterJdbcTemplate.queryForObject(sql,namedParameters,Integer.class);
        return count;
    }

    /**
     * 是同JDBCTemplate 调用RowMapper匹配方式进行查询
     * @param userId
     * @return
     */
    public User findUserById(Long userId){
        String sql="SELECT * FROM test.user WHERE id=?";
        User user=jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            User user1 =new User();
            user1.setId(rs.getInt("id"));
            user1.setName(rs.getString("name"));
            //user1.setDepartmentId(rs.getInt("department_id"));
            return user1;
        },1);
        return user;
    }

    /**
     * 通过字段映射对象进行数据查询获取
     * @param departmentId
     * @return
     */
    public List<User> getUserByDepartmentId(Long departmentId){
        String sql="SELECT * FROM test.user WHERE department_id=? ";
        List<User> users=jdbcTemplate.query(sql,new UserRowMapper(),1);
        return users;
    }

    static class UserRowMapper implements RowMapper<User>{
        @Override
        public User mapRow(ResultSet rs,int rowNum) throws SQLException {
            User user=new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            //user.setDepartmentId(rs.getInt("department_id"));
            return user;
        }
    }

    /**
     * 通过JDBC模板替换进行数据查询
     * @param userId
     * @return
     */
    public Map findUserById2(Integer userId){
        String sql="SELECT * FROM test.user WHERE id=?";
        Map map=jdbcTemplate.queryForMap(sql,userId);
        return map;
    }

    /**
     * 通过对象字段内容获取，进行更新
     * @param user
     * @return
     */
    public int updateInfo(User user){
        String sql="UPDATE test.user SET name=?, department_id=? WHERE id=?";
        //int count=jdbcTemplate.update(sql,user.getName(),user.getDepartmentId(),user.getId());
        return 0;
    }

    /**
     * 通过对象进行键值对的转换，进行字段内容的更新
     * @param user
     * @return
     */
    public int updateInfoByNamedJdbc(User user){
        String sql="UPDATE test.user SET name=:name, department_id=:departmetId WHERE id=:id";
        //为给定的参数创建一个新的参数
        SqlParameterSource source=new BeanPropertySqlParameterSource(user);
        int count=namedParameterJdbcTemplate.update(sql,source);
        return count;
    }

    /**
     * 通过KeyHolder 进行字段的获取，并进行类型的转换
     * @param user
     * @return
     */
    public Integer insertUser(final User user){
        final String sql="INSERT INTO test.user (name,department_id) VALUES (?,?)";
        //使用默认列表创建一个新的GeneratedKeyHolder。
        KeyHolder keyHolder=new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps=con.prepareStatement(sql,new String[]{"id"});
                ps.setString(1,user.getName());
                //ps.setInt(2,user.getDepartmentId());
                return ps;
            }
        },keyHolder);
        return keyHolder.getKey().intValue();
    }
}
