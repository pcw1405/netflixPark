INSERT INTO member(member_id,email,password) VALUES( 1,'asdf@naver.com', '1234');
INSERT INTO member(member_id,email,password) VALUES( 2,'asdf1@naver.com', '1234');
INSERT INTO member(member_id,email,password) VALUES( 3,'asdf2@naver.com', '1234');

INSERT INTO user(user_id,email,name,member_id) VALUES( 1,'asdf@naver.com','윤영민',1);
INSERT INTO user(user_id,email,name,member_id) VALUES( 2,'asdf@naver.com','박청원',1);
INSERT INTO user(user_id,email,name,member_id) VALUES( 3,'asdf@naver.com','강동언',1);
INSERT INTO user(user_id,email,name,member_id) VALUES( 4,'asdf@naver.com','추가',1);
INSERT INTO user(user_id,email,name,member_id) VALUES( 5,'asdf@naver.com','추가1',1);
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