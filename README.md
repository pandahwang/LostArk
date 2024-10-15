# 로스트아크 직업 추천 팀 프로젝트 백엔드    
Spring Boot와 React을 이용하여 구현한 웹 애플리케이션  
링크: https://la.classtest.site/  

## 프론트엔드 링크  
https://github.com/pandahwang/la-front-end  

## 사용 기술  
Java, Javascript, Typescript, SpringBoot, React, TailwindCSS, AWS RDS, EC2, Route53, ALB, ACM, Redis, Docker, Github Actions(CI/CD), Nginx  

## 인프라 아키텍처  
![la_classtest_site drawio](https://github.com/user-attachments/assets/aa62f48e-44b1-4083-b384-41259a1901ce)  

## 프로젝트 기능  
● 사용자의 성향을 분석하여 가장 적합한 5가지 직업을 추천.  
● 로스트아크 직업 통계와 테스트 결과 통계 조회.   
● 테스트 결과와 의견을 공유하는 댓글 기능.  
● 댓글 수정, 삭제 기능 및 직업명으로 검색하는 기능.  

## 성능 개선  
![캡처](https://github.com/user-attachments/assets/1937c5d5-2d81-46f7-ac76-ebfa980f9715)  

Redis를 활용한 캐싱 전략으로 성능을 크게 개선했습니다.  
k6를 이용한 성능 테스트 결과, Redis 캐싱 적용 전 87.9 TPS에서 적용 후 26,562.8 TPS로 초당 처리량이 약 300배 향상되었습니다.  
Postman을 통한 응답 속도 테스트에서는 캐싱 적용 후 응답 속도가 10배 개선되었습니다.  
이러한 성능 향상은 자주 조회되는 댓글 데이터를 Redis에 저장하고, 요청 시 먼저 캐시를 확인하는 방식으로 구현했습니다.  
댓글 조회 기능에 @Cacheable 어노테이션을 사용해 Cache Aside 전략을 적용하고, 댓글 데이터 변동 시 @CacheEvict로 캐시를 모두 삭제해 조회 시 다시 갱신하는 방식을 사용했습니다.  
이를 통해 데이터베이스 접근을 최소화하고 빠른 응답 시간을 확보하였습니다.  
