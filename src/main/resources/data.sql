INSERT INTO member(member_id,email,password,name) VALUES( 1,'asdf@naver.com', '1234','aaa');
INSERT INTO member(member_id,email,password,name) VALUES( 2,'asdf1@naver.com', '1234','bbb');
INSERT INTO member(member_id,email,password,name) VALUES( 3,'asdf2@naver.com', '1234','ccc');
--아직 테스트 데이터 들어감 application.properties 수정 피료할듯
--server.port = 8081
--
--spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
--spring.datasource.url=jdbc:mysql://localhost:3307/netflix?serverTimezone=UTC
--spring.datasource.username=root
--spring.datasource.password=1234
--
--spring.jpa.properties.hibernate.show_sql=true
--
--logging.level.org.hibernate.type.descriptor.sql=trace
--
--spring.jpa.hibernate.ddl-auto=create-drop
--spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
--spring.thymeleaf.cache=false
--spring.jpa.defer-datasource-initialization=true
--spring.jpa.show-sql=true
--logging.level.org.hibernate.SQL=DEBUG
--spring.datasource.initialization-mode=always
--spring.sql.init.mode=always