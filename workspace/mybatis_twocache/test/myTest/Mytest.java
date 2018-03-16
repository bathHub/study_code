package myTest;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.zhiyou.entity.User;
import com.zhiyou.mapper.UserMapper;
import com.zhiyou.util.MybatisUtil;

public class Mytest {
         private SqlSessionFactory sessionFactory;
         @Test
         public void test1() throws Exception{
        	 sessionFactory = MybatisUtil.getSqlSessionFactory();
        	 SqlSession session = sessionFactory.openSession();
        	           UserMapper userMapper = session.getMapper(UserMapper.class);
        	           
        	              User user1 =  userMapper.select1(1);
        	              System.out.println(user1);
        	            session.commit(); 
        	            
        	            UserMapper userMapper1 = session.getMapper(UserMapper.class);
        	            User user2 =  userMapper1.select1(1);
        	            System.out.println(user2);
        	            
        	            
         }
	
	
}
