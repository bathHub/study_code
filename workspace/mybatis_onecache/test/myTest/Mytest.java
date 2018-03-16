package myTest;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.zhiyou.entity.User;
import com.zhiyou.util.MybatisUtil;

public class Mytest {
         private SqlSessionFactory sessionFactory;
         @Test
         public void test1(){
        	 sessionFactory = MybatisUtil.getSqlSessionFactory();
        	 SqlSession session = sessionFactory.openSession();
        	         User user1 = session.selectOne("user.select1", 1);
        	         User user2 = session.selectOne("user.select1", 1);
        	   System.out.println(user1==user2);
        	 
         }
	
	
}
