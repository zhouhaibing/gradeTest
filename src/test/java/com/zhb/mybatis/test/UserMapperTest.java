package com.zhb.mybatis.test;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;



public class UserMapperTest {
	
	SqlSession sqlSession = null;
	SqlSessionFactory sqlSessionFactory = null;
	@Before
	public void setUp() throws Exception {
		 // 通过配置文件获取数据库连接信息
        Reader reader = Resources.getResourceAsReader("com/zhb/mybatis/test/config/mybatis.xml");
        // 通过配置信息构建一个SqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        // 通过sqlSessionFactory打开一个数据库会话
        sqlSession = sqlSessionFactory.openSession();
	}
	
	@Test
	public void testHello() throws Exception {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.findUsers();
        //System.out.println(users);
        //sqlSession.close();
        sqlSession.commit();
        //sqlSession =  sqlSessionFactory.openSession();
        //userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> userss = userMapper.findUsers();
        System.out.println(userss);
        sqlSession.close();
	}
	
	@Test
	public void testSelectList() throws Exception {
		List<User> users = sqlSession.selectList("com.zhb.mybatis.test.UserMapper.findUsers");
		System.out.println(users);
	}
	
	
	

}
