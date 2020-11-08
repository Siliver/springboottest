package com.siliver.ch1.common;


import com.siliver.ch1.dao.UserDao;
import com.siliver.ch1.entity.User;
import org.beetl.sql.core.*;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 先修改为帮助类，后期改为工厂类
 */
public class BeetlSqlConfig {

    static String driver="com.mysql.cj.jdbc.Driver";
    static String url="jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT";
    static String userName="root";
    static String password="123456";

    /**
     * 创建SQLManager
     * @return
     */
    public static SQLManager getsqlmarager() throws Exception {
        ConnectionSource source = ConnectionSourceHelper.getSimple(driver, url, userName, password);
        DBStyle mysql = new MySqlStyle();
        // sql语句放在classpagth的/sql 目录下
        SQLLoader loader = new ClasspathLoader("/sql");
        // 数据库命名跟java命名转化规则，UnderlinedNameConversion 指数据库表和列是下划线分割
        UnderlinedNameConversion nc = new  UnderlinedNameConversion();
        // 最后，创建一个SQLManager,DebugInterceptor 不是必须的，但可以通过它查看sql执行情况
        SQLManager sqlManager = new SQLManager(mysql,loader,source,nc,new Interceptor[]{new DebugInterceptor()});
        gen(sqlManager);
        genCode(sqlManager);
        return sqlManager;
    }

    /**
     * 创建打印切面类到控制台
     * @param sqlManager
     * @throws Exception
     */
    public static void gen(SQLManager sqlManager) throws Exception {
        sqlManager.genPojoCodeToConsole("user");
    }

    /**
     * 通过java路径创建切面类
     * @param sqlManager
     * @throws Exception
     */
    public static void genCode(SQLManager sqlManager) throws Exception {
        sqlManager.genPojoCode("user","com.siliver.ch1.entity");
    }

    /**
     * 添加用户方法
     * @param sqlManager
     */
    public static int addUser(SQLManager sqlManager){
        User user=new User();
        //user.setDepartmentId(1);
        user.setCreateTime(new Date());
        user.setName("小明");
        int count=sqlManager.insert(user);
        return count;
    }

    /**
     * 查找用户
     * @param sqlManager
     */
    public static User findUser(SQLManager sqlManager){
        Integer key=1;
        User user=sqlManager.unique(User.class,key);
        return user;
    }

    /**
     * 更新用户
     * @param sqlManager
     * @return
     */
    public static int updateUser(SQLManager sqlManager){
        Long key=1l;
        User user=sqlManager.unique(User.class,key);
        user.setName("NewName");
        int count=sqlManager.updateById(user);
        return count;
    }

    /**
     * 根据模板更新用户
     * @param sqlManager
     * @return
     */
    public static int updateTemplateUser(SQLManager sqlManager){
        Integer key=1;
        User user=new User();
        user.setId(key);
        user.setName("updateTemplate");
        int count=sqlManager.updateTemplateById(user);
        return count;
    }

    /**
     * 进行用户信息的查询
     * @param sqlManager
     * @return
     */
    public static List<User> queryUser(SQLManager sqlManager){
        User query=new User();
        query.setName("NewName");
        List<User> list=sqlManager.select("user.selectSample",User.class,query);
        return list;
    }

    /**
     * 通过键值对进行数据查询
     * @param sqlManager
     * @return
     */
    public static List<User> queryUerByMap(SQLManager sqlManager){
        Map paras=new HashMap();
        paras.put("name","NewName");
        List<User> list=sqlManager.select("user.selectSample",User.class,paras);
        return list;
    }

    /**
     * 通过反射查询类进行数据查询
     * @param sqlManager
     * @return
     */
    public static List<User> daoquery(SQLManager sqlManager){
        User query=new User();
        query.setName("NewName");
        UserDao dao=sqlManager.getMapper(UserDao.class);
        List<User> list=dao.selectSample(query);
        return list;
    }
}
