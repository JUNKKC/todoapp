# Todo List

## 개요

간단한 일정 관리를 위한 웹사이트로, 유저가 개인 일정을 작성하고 관리할 수 있습니다.

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