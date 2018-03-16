package mybatis_01_HelloWorld;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.zhiyou100.entity.Role;
import com.zhiyou100.entity.User;
import com.zhiyou100.util.MybatisUtil;

public class MyTest {
	@Test
	public void test1(){
		SqlSessionFactory sqlSession=MybatisUtil.getSqlSessionFactory();
		System.out.println(sqlSession);
	}
	
	@Test
	public void test2(){
		//1、获取sqlSession工厂对象
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2、打开会话
		SqlSession session=sqlSessionFactory.openSession();
		//3、调用方法进行crud操作
		List<User> users=session.selectList("user.selectAll");
		//4、打印输出
		for (User user : users) {
			System.out.println(user.getName());
		}
		//5、释放资源
		session.close();
	}
	
	@Test
	public void test3(){
		//1、获取sqlSession工厂对象
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2、打开会话
		SqlSession session=sqlSessionFactory.openSession();
		//3、调用方法进行crud操作
		//List<Role> roles=session.selectList("role.selectAllRole");
		List<Role> roles = session.selectList("role.selectAllRole2");
		//4、打印输出
		for (Role role : roles) {
			System.out.println(role);
		}
		//5、释放资源
		session.close();
	}
	
	@Test
	public void test4(){
		//1、获取sqlSession工厂对象
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2、打开会话
		SqlSession session=sqlSessionFactory.openSession();
		//3、调用方法进行crud操作
		//List<Role> roles=session.selectList("role.selectAllRole");
		List<Role> roles = session.selectList("role.select1");
		//4、打印输出
		for (Role role : roles) {
			System.out.println(role);
		}
		//5、释放资源
		session.close();
	}
	
	@Test
	public void test5(){
		//1、获取sqlSession工厂对象
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2、打开会话
		SqlSession session=sqlSessionFactory.openSession();
		//3、调用方法进行crud操作
		Role role=session.selectOne("role.select2", 1);
		//4、打印输出
		System.out.println(role.getName());
		//5、释放资源
		session.close();
	}
	
	@Test
	public void test6(){
		//1、获取sqlSession工厂对象
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2、打开会话
		SqlSession session=sqlSessionFactory.openSession();
		//参数的map集合
		Map<String, Object> map = new HashMap<>();
		map.put("a", 2);
		map.put("sal", 190);
		//3、调用方法进行crud操作
		Role role=session.selectOne("role.select3", map);
		//4、打印输出
		System.out.println(role.getName());
		//5、释放资源
		session.close();
	}
	
	@Test
	public void test7(){
		//1、获取sqlSession工厂对象
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2、打开会话
		SqlSession session=sqlSessionFactory.openSession();
		//参数的封装到对象中
		Role r = new Role();
		r.setId(2);
		r.setSal(190.0);
		//3、调用方法进行crud操作
		Role role=session.selectOne("role.select4", r);
		//4、打印输出
		System.out.println(role.getName());
		//5、释放资源
		session.close();
	}
	
	@Test
	public void test8(){
		//1、获取sqlSession工厂对象
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2、打开会话
		SqlSession session=sqlSessionFactory.openSession();
		//3、调用方法进行crud操作
		List<Role> roles=session.selectList("role.select5", "%武%");
		//4、打印输出
		System.out.println(roles.size());
		//5、释放资源
		session.close();
	}
	
	@Test
	public void test9(){
		//1、获取sqlSession工厂对象
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2、打开会话
		SqlSession session=sqlSessionFactory.openSession();
		//参数封装到对象中
		Role r = new Role();
		r.setName("武");
		//3、调用方法进行crud操作
		List<Role> roles=session.selectList("role.select6", r);
		//4、打印输出
		System.out.println(roles.size());
		//5、释放资源
		session.close();
	}
	
	@Test
	public void test10(){
		//1、获取sqlSession工厂对象
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2、打开会话
		SqlSession session=sqlSessionFactory.openSession();
		//参数封装到map集合中
		Map<String, Object> map = new HashMap<>();
		map.put("name", "宋江");
		map.put("sal", 2000);
		map.put("id", 3);
		map.put("birthday", new Date());
		//3、调用方法进行crud操作
		int i=session.update("role.update1", map);
		System.out.println(i);
		//提交事物
		session.commit();
		//5、释放资源
		session.close();
	}
	
	@Test
	public void test11(){
		//1、获取sqlSession工厂对象
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2、打开会话
		SqlSession session=sqlSessionFactory.openSession();
		//参数封装到map集合中
		Role r = new Role();
		r.setBirthday(new Date());
		r.setId(10);
		r.setName("吴用");
		r.setSal(1500.0);
		//3、调用方法进行crud操作
		session.insert("role.insert1", r);
		//提交事物
		session.commit();
		//5、释放资源
		session.close();
	}
	
	@Test
	public void test12(){
		//1、获取sqlSession工厂对象
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2、打开会话
		SqlSession session=sqlSessionFactory.openSession();
		//参数封装到map集合中
		Role r = new Role();
		r.setBirthday(new Date());
		r.setName("花荣");
		r.setSal(1500.0);
		//3、调用方法进行crud操作
		int i=session.insert("role.insert2", r);
		System.out.println(i);
		System.out.println(r.getId());
		//提交事物
		session.commit();
		//5、释放资源
		session.close();
	}
	
	@Test
	public void test13(){
		//1、获取sqlSession工厂对象
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2、打开会话
		SqlSession session=sqlSessionFactory.openSession();
		int i=session.delete("role.delete1", 14);
		System.out.println(i);
		//提交事物
		session.commit();
		//5、释放资源
		session.close();
	}
}
