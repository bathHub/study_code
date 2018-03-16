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
		//1����ȡsqlSession��������
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2���򿪻Ự
		SqlSession session=sqlSessionFactory.openSession();
		//3�����÷�������crud����
		List<User> users=session.selectList("user.selectAll");
		//4����ӡ���
		for (User user : users) {
			System.out.println(user.getName());
		}
		//5���ͷ���Դ
		session.close();
	}
	
	@Test
	public void test3(){
		//1����ȡsqlSession��������
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2���򿪻Ự
		SqlSession session=sqlSessionFactory.openSession();
		//3�����÷�������crud����
		//List<Role> roles=session.selectList("role.selectAllRole");
		List<Role> roles = session.selectList("role.selectAllRole2");
		//4����ӡ���
		for (Role role : roles) {
			System.out.println(role);
		}
		//5���ͷ���Դ
		session.close();
	}
	
	@Test
	public void test4(){
		//1����ȡsqlSession��������
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2���򿪻Ự
		SqlSession session=sqlSessionFactory.openSession();
		//3�����÷�������crud����
		//List<Role> roles=session.selectList("role.selectAllRole");
		List<Role> roles = session.selectList("role.select1");
		//4����ӡ���
		for (Role role : roles) {
			System.out.println(role);
		}
		//5���ͷ���Դ
		session.close();
	}
	
	@Test
	public void test5(){
		//1����ȡsqlSession��������
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2���򿪻Ự
		SqlSession session=sqlSessionFactory.openSession();
		//3�����÷�������crud����
		Role role=session.selectOne("role.select2", 1);
		//4����ӡ���
		System.out.println(role.getName());
		//5���ͷ���Դ
		session.close();
	}
	
	@Test
	public void test6(){
		//1����ȡsqlSession��������
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2���򿪻Ự
		SqlSession session=sqlSessionFactory.openSession();
		//������map����
		Map<String, Object> map = new HashMap<>();
		map.put("a", 2);
		map.put("sal", 190);
		//3�����÷�������crud����
		Role role=session.selectOne("role.select3", map);
		//4����ӡ���
		System.out.println(role.getName());
		//5���ͷ���Դ
		session.close();
	}
	
	@Test
	public void test7(){
		//1����ȡsqlSession��������
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2���򿪻Ự
		SqlSession session=sqlSessionFactory.openSession();
		//�����ķ�װ��������
		Role r = new Role();
		r.setId(2);
		r.setSal(190.0);
		//3�����÷�������crud����
		Role role=session.selectOne("role.select4", r);
		//4����ӡ���
		System.out.println(role.getName());
		//5���ͷ���Դ
		session.close();
	}
	
	@Test
	public void test8(){
		//1����ȡsqlSession��������
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2���򿪻Ự
		SqlSession session=sqlSessionFactory.openSession();
		//3�����÷�������crud����
		List<Role> roles=session.selectList("role.select5", "%��%");
		//4����ӡ���
		System.out.println(roles.size());
		//5���ͷ���Դ
		session.close();
	}
	
	@Test
	public void test9(){
		//1����ȡsqlSession��������
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2���򿪻Ự
		SqlSession session=sqlSessionFactory.openSession();
		//������װ��������
		Role r = new Role();
		r.setName("��");
		//3�����÷�������crud����
		List<Role> roles=session.selectList("role.select6", r);
		//4����ӡ���
		System.out.println(roles.size());
		//5���ͷ���Դ
		session.close();
	}
	
	@Test
	public void test10(){
		//1����ȡsqlSession��������
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2���򿪻Ự
		SqlSession session=sqlSessionFactory.openSession();
		//������װ��map������
		Map<String, Object> map = new HashMap<>();
		map.put("name", "�ν�");
		map.put("sal", 2000);
		map.put("id", 3);
		map.put("birthday", new Date());
		//3�����÷�������crud����
		int i=session.update("role.update1", map);
		System.out.println(i);
		//�ύ����
		session.commit();
		//5���ͷ���Դ
		session.close();
	}
	
	@Test
	public void test11(){
		//1����ȡsqlSession��������
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2���򿪻Ự
		SqlSession session=sqlSessionFactory.openSession();
		//������װ��map������
		Role r = new Role();
		r.setBirthday(new Date());
		r.setId(10);
		r.setName("����");
		r.setSal(1500.0);
		//3�����÷�������crud����
		session.insert("role.insert1", r);
		//�ύ����
		session.commit();
		//5���ͷ���Դ
		session.close();
	}
	
	@Test
	public void test12(){
		//1����ȡsqlSession��������
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2���򿪻Ự
		SqlSession session=sqlSessionFactory.openSession();
		//������װ��map������
		Role r = new Role();
		r.setBirthday(new Date());
		r.setName("����");
		r.setSal(1500.0);
		//3�����÷�������crud����
		int i=session.insert("role.insert2", r);
		System.out.println(i);
		System.out.println(r.getId());
		//�ύ����
		session.commit();
		//5���ͷ���Դ
		session.close();
	}
	
	@Test
	public void test13(){
		//1����ȡsqlSession��������
		SqlSessionFactory sqlSessionFactory=MybatisUtil.getSqlSessionFactory();
		//2���򿪻Ự
		SqlSession session=sqlSessionFactory.openSession();
		int i=session.delete("role.delete1", 14);
		System.out.println(i);
		//�ύ����
		session.commit();
		//5���ͷ���Դ
		session.close();
	}
}
