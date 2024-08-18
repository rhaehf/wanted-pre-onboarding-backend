# Wanted-pre-onboarding-backend
> 아래 서비스 개요 및 요구사항을 만족하는 API 서버를 구현한다.

## 1. 서비스 개요
- 본 서비스는 기업의 채용을 위한 웹 서비스 입니다.
- 회사는 채용공고를 생성하고, 이에 사용자는 지원합니다.

## 2. 요구사항 분석
- 회사, 사용자 등록 절차는 생략합니다. (DB에 임의로 생성하여 진행)
- 로그인 등 사용자 인증절차(토큰 등)는 생략합니다.

### 🗂️ 주요 기능
> 필수 구현 기능 : 1, 2, 3, 4-1  </br>
> 선택사항 및 가산점 : 4-2, 5, 6

1. 회사는 **채용공고를 등록**한다.
2. 회사는 **채용공고를 수정**한다.
3. DB에서 **채용공고를 삭제**한다.
4. 채용공고 목록 가져오기 </br>
   4-1. 사용자는 **채용공고 목록 전체를 조회**할 수 있다. </br>
   4-2. 사용자는 **채용공고를 검색**할 수 있다.
    1. ex) 회사명 or 채용포지션
5. 사용자는 **채용공고 상세 페이지를 확인**할 수 있다.
    1. "채용내용"이 추가적으로 담겨있음
    2. 해당 회사가 올린 다른 채용공고 가 추가적으로 포함됨
6. 사용자는 **채용공고를 지원**한다.
    1. 사용자는 1회만 지원 가능함

### 🏠 모델
- 회사 (Company)
- 사용자 (Member)
- 채용공고 (Post)
- 지원내역(선택사항)

이외 추가 모델정의 자유

## 3. 개발 환경
- 언어 : Java 17
- 프레임워크 : Spring Boot 3.2.8
    - 빌드 관리 도구 : Gradle 8.8 - Groovy
    - Packaging : Jar
- ORM : Spring Data JPA
- DB : MySQL 8.0.29
- [build.gradle 파일](build.gradle)

## 4. ERD
- 회사(company) : 채용공고(posts) = 1 : N

![ERD](src/main/resources/image/ERD.png)</br>

## 5. API 명세서
#### [노션 링크 (각 기능별로 실행 사진이 첨부되어있음)](https://jinhui-portfolio.notion.site/API-90e049d731e44579aa35f96eb38c78a9?pvs=4)

| 완료  | 기능                     | Method | URI                                                           |
|:---:|:-----------------------|:------:|:--------------------------------------------------------------|
|  ✅  | 1. 채용공고 등록             |  `POST`  | `/posts`                                                      |
|  ✅  | 2. 채용공고 수정             | `PATCH`  | `/posts/{id}`                                                 |
|  ✅  | 3. 채용공고 삭제             | `DELETE` | `/posts/{id}`                                                 |
|  ✅  | 4-1. 채용공고 목록 전체 조회     |  `GET`   | `/posts`                                                      |
|  ✅  | 4-2. 채용공고 검색 (채용포지션으로) |  `GET`   | `/posts/search?position={keyword}`                            |
|  ✅  | 5. 채용공고 상세 페이지 확인      |  `GET`   | `/posts/detail?postId={postId}`                               |
|  -  | 6. 채용공고 지원             |  `POST`  | -                                                             |

## 6. 프로젝트 특징
### Exception Handling [📁](https://github.com/rhaehf/wanted-pre-onboarding-backend/blob/3fb654b51c5dd7a11f2ca7e982769dd56ca494f1/src/main/java/com/wanted/pre_onboarding_backend/exception)
다양한 예외 상황을 클라이언트에게 체계적으로 응답하기 위해서 구현했습니다.
- **ErrorCode** : enum 타입으로 예외 코드를 HttpStatus와 에러 메시지로 정의하고, 상황 별로 에러 코드를 만들었습니다.
- **CustomException** : RuntimeException을 상속받으며 ErrorCode에서 정의한 예외 코드를 사용하고, 새로운 에러 메시지도 적용할 수 있습니다.
- **ErrorResponse** : 에러용 응답 파일로 timestamp(시간), status(http status code), error(http status), code(ErrorCode의 이름), message(ErrorCode의 메시지)로 구성되어있습니다.
- **GlobalExceptionHandler** : 예외 클래스별로 구현되어있으며 HttpStatus와 ErrorResponse를 리턴합니다.


### Git Commit Message Convension
[Wiki 문서](https://github.com/rhaehf/wanted-pre-onboarding-backend/wiki/Git-Commit-Message-Convension) 의 기준에 따라서 git commit message를 작성했습니다.
