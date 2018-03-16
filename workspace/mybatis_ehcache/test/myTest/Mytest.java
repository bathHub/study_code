package myTest;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.zhiyou.entity.Orders;
import com.zhiyou.entity.User;
import com.zhiyou.mapper.OrdersMapper;
import com.zhiyou.mapper.UserMapper;
import com.zhiyou.util.MybatisUtil;

public class Mytest {
         private SqlSessionFactory sessionFactory;
         @Test
         public void test1() throws Exception{
        	 sessionFactory = MybatisUtil.getSqlSessionFactory();
        	 SqlSession session = sessionFactory.openSession();
        	           UserMapper userMapper = session.getMapper(UserMapper.class);
        	           
        	              User user1 =  userMapper.selectById(1);
        	              System.out.println(user1);
        	            session.commit(); 
        	            
        	            UserMapper userMapper1 = session.getMapper(UserMapper.class);
        	            User user2 =  userMapper1.selectById(1);
        	            System.out.println(user2);
        	            
        	            
         }
         
         //测试按需加载的实际情况
         
         @Test
         public void test2() throws Exception{
        	         sessionFactory = MybatisUtil.getSqlSessionFactory();
        	            SqlSession session = sessionFactory.openSession();
        	             OrdersMapper ordersMapper = session.getMapper(OrdersMapper.class);
        	              Orders orders = ordersMapper.select1(3);
        	              System.out.println(orders.getNumber());
        	              
        	              
        	              
        	 
        	 
         }
	
	
}
