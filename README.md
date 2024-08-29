# Todo List

## 개요

간단한 일정 관리를 위한 웹사이트로, 유저가 개인 일정을 작성하고 관리할 수 있습니다.
## 기능 구현

| 페이지 (기능)  | 이미지                                                      |
|-----------|----------------------------------------------------------|
| 회원 가입     | ![회원가입 페이지](https://github.com/JUNKKC/todoapp/blob/main/images/%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85.gif?raw=true)              |
| 로그인       | ![로그인](https://github.com/JUNKKC/todoapp/blob/main/images/%EB%A1%9C%EA%B7%B8%EC%9D%B8.gif?raw=true)                      |
| 할 일 추가    | ![할일 추가 기능](https://github.com/JUNKKC/todoapp/blob/main/images/%ED%95%A0%EC%9D%BC%20%EC%B6%94%EA%B0%80%20%EA%B8%B0%EB%8A%A5.gif?raw=true)                 |
| 할 일 완료 삭제 | ![기능 구현 상태변경 삭제](https://github.com/JUNKKC/todoapp/blob/main/images/%EA%B8%B0%EB%8A%A5%20%EA%B5%AC%ED%98%84%20%EC%83%81%ED%83%9C%EB%B3%80%EA%B2%BD%20%EC%82%AD%EC%A0%9C.gif?raw=true) |
| 검색 기능     | ![기능 구현 검색](https://github.com/JUNKKC/todoapp/blob/main/images/%EA%B8%B0%EB%8A%A5%20%EA%B5%AC%ED%98%84%20%EA%B2%80%EC%83%89.gif?raw=true)                          |
| 정보 수정     | ![내정보 페이지](https://github.com/JUNKKC/todoapp/blob/main/images/%EB%82%B4%EC%A0%95%EB%B3%B4%20%ED%8E%98%EC%9D%B4%EC%A7%80.gif?raw=true)                         |


## 기술 스택

### 백엔드
- **언어**: Java
- **데이터베이스**: MySQL
- **프레임워크**: Spring Boot
  - **주요 의존성**:
    - `spring-boot-starter-data-jpa` - JPA 사용
    - `spring-boot-starter-security` - 보안 기능
    - `jjwt` - JWT 토큰 처리
    - `lombok`, `mapstruct` - 코드 간소화 및 객체 매핑
    - `mysql-connector-java` - MySQL 연결

### 프론트엔드
- **언어**: JavaScript, HTML, CSS
- **프레임워크**: React
  - **주요 의존성**:
    - `axios` - HTTP 요청 처리
    - `react-router-dom` - 라우팅 관리

## 실행 방법

1. **백엔드**:
  - Maven을 사용해 의존성 설치 후 애플리케이션 실행
  - MySQL 데이터베이스 설정 필요
2. **프론트엔드**:
  - `npm install` 명령어로 필요한 패키지 설치
  - `npm run dev`로 개발 서버 실행

## 추가로 구현할 기능
- [ ] 일정 수정 기능
- [ ] 유저 탈퇴 기능
- [ ] 관리자 유저 관리 페이지
