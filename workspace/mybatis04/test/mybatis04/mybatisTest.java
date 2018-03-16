package mybatis04;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.zhiyou.entity.Orders;
import com.zhiyou.entity.User;
import com.zhiyou.mapper.OrdersMapper;
import com.zhiyou.mapper.UserMapper;
import com.zhiyou.util.MybatisUtil;

public class mybatisTest {
        private SqlSessionFactory factory;
         @Test
           public void test1() throws Exception{
        	       SqlSessionFactory sFactory =  MybatisUtil.getSqlSessionFactory();
        	  SqlSession session = sFactory.openSession();
        		
        
        			
        			OrdersMapper ordersMapper=session.getMapper(OrdersMapper.class);
        			  List<Orders> list= ordersMapper.select1();
        			  for (Orders orders : list) {
						System.out.println(orders.getNumber()+"|"+orders.getUser().getUsername() );
					}
        			
        			}
         @Test
         public void test2() throws Exception{
      	       SqlSessionFactory sFactory =  MybatisUtil.getSqlSessionFactory();
      	  SqlSession session = sFactory.openSession();
      		
      
      			
      			          UserMapper userMapper = session.getMapper(UserMapper.class);
      			           List<User> list = userMapper.select3();
      			         for (User user : list) {
							//System.out.println(user.getUsername()+":"+user.getId());
							List<Orders> list2 = user.getOrders();
						    for (Orders orders : list2) {
								System.out.println(orders.getNumber()+":"+orders.getUser().getUsername());
							}
						}  
        }
         @Test
         public void test3() throws Exception{
    	       SqlSessionFactory sFactory =  MybatisUtil.getSqlSessionFactory();
    	  SqlSession session = sFactory.openSession();
    		
    
    			
    			          UserMapper userMapper = session.getMapper(UserMapper.class);
    			           List<User> list = userMapper.select4();
    			       for (User user : list) {
						System.out.println(user.getUsername()+"33333"+user.getOrders());
					}
        }
}
        

