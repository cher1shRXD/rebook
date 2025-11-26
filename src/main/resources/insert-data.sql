-- ID 시퀀스 재설정
ALTER TABLE users ALTER COLUMN id RESTART WITH 1;

-- 사용자 데이터 삽입 (ID 명시)
INSERT INTO users (id, email, nickname, password, role, username) VALUES
(1, 'admin@rebook.com', '관리자', '$2y$10$qEETX1RXUedVHlwmNu.iTetnsPLIHKQZ.1o88ZnR4H8WQSiKT.qFS', 1, 'admin'),
(2, 'user1@rebook.com', '독서왕', '$2y$10$qEETX1RXUedVHlwmNu.iTetnsPLIHKQZ.1o88ZnR4H8WQSiKT.qFS', 0, 'user1'),
(3, 'user2@rebook.com', '책벌레', '$2y$10$qEETX1RXUedVHlwmNu.iTetnsPLIHKQZ.1o88ZnR4H8WQSiKT.qFS', 0, 'user2'),
(4, 'user3@rebook.com', '북러버', '$2y$10$qEETX1RXUedVHlwmNu.iTetnsPLIHKQZ.1o88ZnR4H8WQSiKT.qFS', 0, 'user3');

-- 다음 ID를 위한 시퀀스 재설정
ALTER TABLE users ALTER COLUMN id RESTART WITH 5;
ALTER TABLE books ALTER COLUMN id RESTART WITH 1;

-- 도서 데이터 삽입
INSERT INTO books (title, author, added_by_id) VALUES
('클린 코드', '로버트 C. 마틴', 1),
('이펙티브 자바', '조슈아 블로크', 1),
('스프링 부트와 AWS로 혼자 구현하는 웹 서비스', '이동욱', 2),
('자바의 정석', '남궁성', 2),
('토비의 스프링', '토비', 2),
('리팩터링', '마틴 파울러', 3),
('테스트 주도 개발', '켄트 벡', 3),
('도메인 주도 설계', '에릭 에반스', 3),
('헤드 퍼스트 디자인 패턴', '에릭 프리먼', 4),
('자바 ORM 표준 JPA 프로그래밍', '김영한', 4);

-- 리뷰 시퀀스 재설정
ALTER TABLE reviews ALTER COLUMN id RESTART WITH 1;

-- 리뷰 데이터 삽입
INSERT INTO reviews (title, content, rating, book_id, user_id) VALUES
('깔끔한 코드의 정석', '클린 코드의 원칙들을 배울 수 있는 최고의 책입니다. 모든 개발자가 읽어야 할 필독서!', 5, 1, 2),
('자바 개발자 필독서', '이펙티브 자바는 정말 자바를 제대로 사용하는 방법을 알려줍니다.', 5, 2, 2),
('실무에 바로 적용 가능', '스프링 부트와 AWS를 활용한 실전 프로젝트를 따라하며 많이 배웠습니다.', 4, 3, 3),
('자바 기초 다지기', '자바의 정석으로 기초부터 탄탄히 다질 수 있었습니다.', 4, 4, 3),
('스프링의 바이블', '토비의 스프링은 스프링을 깊이 이해하는 데 큰 도움이 됩니다.', 5, 5, 4),
('코드 개선의 지침서', '리팩터링 기법들을 체계적으로 배울 수 있었습니다.', 4, 6, 1),
('TDD 입문서', 'TDD를 처음 접하는 사람에게 추천합니다. 실습 예제가 좋아요.', 3, 7, 1),
('DDD의 정수', '도메인 주도 설계의 개념을 이해하는 데 많은 도움이 되었습니다.', 4, 8, 2),
('디자인 패턴 쉽게 배우기', '그림과 예제로 디자인 패턴을 쉽게 설명해줍니다.', 5, 9, 3),
('JPA 완벽 가이드', 'JPA를 제대로 사용하려면 꼭 읽어야 할 책입니다.', 5, 10, 4);

-- 추가 리뷰 데이터 (통계를 위해)
INSERT INTO reviews (title, content, rating, book_id, user_id) VALUES
('두 번째 읽기', '다시 읽어도 새로운 내용을 발견하게 됩니다.', 5, 1, 3),
('실무 적용 후기', '실제 프로젝트에 적용해보니 정말 유용했습니다.', 5, 2, 4),
('초보자도 OK', '초보자도 따라할 수 있게 잘 설명되어 있습니다.', 4, 3, 1);