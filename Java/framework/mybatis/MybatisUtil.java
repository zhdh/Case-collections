package com.data.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * Initialization Mybatis
 * 
 * @author Administrator
 *
 */
public class MybatisUtil {

	private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();
	private static SqlSessionFactory sqlSessionFactory = null;

	static {
		String resource = "mybatis-config.xml";
		InputStream configure = null;
		try {
			configure = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(configure);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private MybatisUtil() {};

	/**
	 * Get Session
	 */
	public static SqlSession getSession() {
		SqlSession session = threadLocal.get();
		if (session == null) {
			session = (sqlSessionFactory != null) ? sqlSessionFactory.openSession() : null;
			threadLocal.set(session);
		}
		return session;
	}

	/**
	 * Close SqlSession
	 */
	public static void closeSqlSession() {
		SqlSession sqlSession = threadLocal.get();
		if (sqlSession != null) {
			sqlSession.close();
			threadLocal.remove();
		}
	}

	/**
	 * Get SqlSessionFactory
	 * 
	 * @return
	 */
	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

}
