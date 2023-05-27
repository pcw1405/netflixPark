INSERT INTO Member(id, email, password) VALUES(1, 'asdf@naver.com', '1234');
INSERT INTO Member(id, email, password) VALUES(2, 'asdf1@naver.com', '1234');
INSERT INTO Member(id, email, password) VALUES(3, 'asdf2@naver.com', '1234');
--아직 테스트 안해봄

--# 15강: data.sql 적용을 위한 설정(스프링부트 2.5 이상)
--spring.jpa.defer-datasource-initialization=true
--이 코드가 필요할지도