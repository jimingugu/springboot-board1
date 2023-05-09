package com.example.demo.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

// 이 클래스는 설정 클래스가 됨
@Configuration											//설정파일로 선언
@PropertySource("classpath:/application.properties")	//참조파일 경로 선언
public class DatabaseConfig {
	
	
	@Autowired							//bean으로 등록되어 있는 메서드 들이 이 생성자로 매핑됨. 이 스프링 컨테이너에 Bean들을 등록해서 의존성이 높은 문제를 해결함.
	private ApplicationContext context;
	
	//설정 파일 연결
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")	//application.properties 파일에서 spring.datasource.hikari로 시작하는 모든 설정을 읽어들여 이 메소드에 매핑함. 그리고 히카리CP(커넥션 풀 라이브러리) 객체 생성함.
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	//DB 경로 저장해놓음
	//데이터소스 객체를 생성함.
	@Bean
	public DataSource dataSource() {
		return new HikariDataSource(hikariConfig());
		//데이터소스 = 커넥션 풀을 지원하기 위한 인터페이스 (커넥션 풀 : 커넥션 객체를 생성해두고, DB에 접근하는 사용자에게 미리 생성해둔 커넥션을 제공했다가 다시 돌려받는 방법)
	}
	
	//DB 설정 파일 경로 등 정보 가짐
	//sqlSessionFactory 객체 생성함. (DB 커넥션과 SQL 실행에 대한 모든 것을 갖는 객체)
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();	//SqlSessionFactoryBean : FactoryBean 인터페이스의 구현클래스임. 마이바티스와 스프링 연동 모듈로 사용됨.
		factoryBean.setDataSource(dataSource());							//factoryBean 객체는 데이터 소스를 참조하며, XML Mapper(SQL 쿼리 작성 파일)의 경로와 설정 파일 경로 등의 정보를 갖는 객체.
		factoryBean.setMapperLocations(context.getResources("classpath:/mappers/**/*Mapper.xml"));
		factoryBean.setConfiguration(mybatisConfig());						//mybatis 설정 정보. 밑에 ybatisConfig()를 가지고 Mybatis 옵션을 설정.
		return factoryBean.getObject();
	}
	
	//DB 실행에 필요한 모든 메서드 가짐.
	//sqlSession 객체 생성함.
	@Bean
	public SqlSessionTemplate sqlSession() throws Exception{
		return new SqlSessionTemplate(sqlSessionFactory());					//sqlSessionTemplate은 SqlSessionFactory를 통해 생성됨, DB의 커밋과 롤백 등 SQL의 실행에 필요한 모든 메서드를 갖는 객체.
	}
	
	//select 결과를 매핑하기 위해서는 테이블의 칼럼명과 변수명을 맞춰주어야 하기 때문에
	//application.properties에 설정을 해주었고, 그 설정을 spring이 알아볼 수 있도록 이 Bean을 추가함.
	@Bean
	@ConfigurationProperties(prefix="mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig(){
		return new org.apache.ibatis.session.Configuration();
	}
}