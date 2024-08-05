insert into company (company_id, company_name, country, region) values (1, '원티드랩', '한국', '서울');
insert into company (company_id, company_name, country, region) values (2, '네이버', '한국', '판교');
insert into company (company_id, company_name, country, region) values (3, '현대오토에버', '한국', '서울');

insert into posts (post_id, company_id, position, signing_bonus, content, skill) values (1,	1, '백엔드 시니어 개발자', 100000, '원티드랩에서 백엔드 시니어 개발자를 채용합니다. 자격요건은..', 'Python');
insert into posts (post_id, company_id, position, signing_bonus, content, skill) values (2,	2, '백엔드 주니어 개발자', 200000, '네이버에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..', 'Java');
insert into posts (post_id, company_id, position, signing_bonus, content, skill) values (3,	3, '백엔드 주니어 개발자', 300000, '현대오토에버에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..', 'Node.js');