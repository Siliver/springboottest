package com.siliver.ch1.service.impl;

import com.siliver.ch1.dao.UserDao;
import com.siliver.ch1.dao.UserRepository;
import com.siliver.ch1.entity.Department;
import com.siliver.ch1.entity.User;
import com.siliver.ch1.service.CreditSystemService;
import com.siliver.ch1.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.query.criteria.internal.predicate.ImplicitNumericExpressionTypeDeterminer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    Log log= LogFactory.getLog(this.getClass());

    @Autowired
    CreditSystemService creditSystemService;

    @Autowired
    UserRepository userDao;

    @Autowired
    UserDao userDao2;

    @Autowired
    EntityManager em;

    public void updateUser(){
        User user=new User();
        user.setId(1);
        user.setName("hhaancd");
        em.merge(user);
    }

    public User findUser(int id){
        Optional<User> user=userDao.findById(id);
        return user.orElse(null);
    }

    public Integer addUser(User user){
        userDao.save(user);
        user.setName("1"+user.getName());
        userDao.save(user);
        return user.getId();
    }

    public List<User> getAllUser(int page,int size){
        PageRequest pageable=PageRequest.of(page,size);
        Page<User> pageObject=userDao.findAll(pageable);
        int totalPage=pageObject.getTotalPages();
        int realSize=pageObject.getSize();
        long count=pageObject.getTotalElements();

        return pageObject.getContent();
    }

    public User getUser(String name){
        return userDao.findByName(name);
    }

    public User getUser(String name,Integer departmentId){
        return userDao.nativeQuery(name,departmentId);
    }

    private List<Integer> getUser(Integer departmentId){
        List<User> list=userDao.queryUserCount();
        List<Integer> ids=userDao.queryUserIds(departmentId);
        return ids;
    }

    public Page<User> queryUser(Integer departmentId, Pageable page){
        return userDao.queryUsers(departmentId,page);
    }

    /**
     * 通过Page对象进行分页结果的查询
     * @param departmentId
     * @param page
     * @return
     */
    public Page<User> queryUser2(Integer departmentId,Pageable page){
        //构造JPQL和实际的参数
        StringBuilder sb=new StringBuilder("from User u where 1=1");
        Map<String,Object> paras=new HashMap<String,Object>();
        if (departmentId!=null){
            sb.append(" and u.department.id=:deptId ");
            paras.put("deptId",departmentId);
        }
        //查询满足条件的总数
        long count=getQueryCount(sb,paras);
        if (count==0){
            return new PageImpl(Collections.emptyList(),page,0);
        }
        //查询满足条件结果集
        List list=getQueryResult(sb,paras,page);

        //返回结果(分页对象拼接)
        Page ret=new PageImpl(list,page,count);
        return ret;
    }

    /**
     * 通过查询构造器进行数据的查询
     * @param name
     * @return
     */
    public List<User> getByExample(String name){
        User user=new User();
        Department dept=new Department();
        user.setName(name);
        //dept.setId(1);
        //dept.setName(name);
        //user.setDepartment(dept);
        //创建匹配器，使用条件查询
        ExampleMatcher matcher=ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase());
        //对User对象进行构造器的生成
        Example<User> example=Example.of(user,matcher);
        List<User> list=userDao.findAll(example);
        return list;
    }



    /**
     * 进行JPA数据查询语句的拼接
     * @param baseJpql
     * @param paras
     * @return
     */
    private Long getQueryCount(StringBuilder baseJpql,Map<String,Object> paras){
        Query query=em.createQuery("select count(1)"+baseJpql.toString());
        setQueryParameter(query,paras);
        Number number=(Number) query.getSingleResult();
        return number.longValue();
    }

    /**
     * 通过map类型的键值对通过entrySet()转换为键和值的集合，设置Query对象的参数集合
     * @param query
     * @param paras
     */
    private void setQueryParameter(Query query,Map<String,Object> paras){
        for (Map.Entry<String,Object> entity:paras.entrySet()){
            query.setParameter(entity.getKey(),entity.getValue());
        }
    }

    /**
     * 进行分页数据的获取
     * @param sb
     * @param paras
     * @param page
     * @return
     */
    private List getQueryResult(StringBuilder sb,Map<String,Object> paras,Pageable page){
        Query query=em.createQuery(" select u "+sb.toString());
        setQueryParameter(query,paras);
        //通过页面大小和页数进行偏移量的获取，并设置获取第一条数据的位置
        query.setFirstResult((int)page.getOffset());
        //通过分页量获取最大的检索数
        query.setMaxResults(page.getPageNumber());
        //获取查询结果列表
        List list=query.getResultList();
        return list;
    }

    @Override
    public int getCredit(int userId){
        return creditSystemService.getUserCredit(userId);
    }

    @Override
    public boolean updateUser(User user){
        int ret=userDao2.updateById(user);
        return ret==1;
    }
}
