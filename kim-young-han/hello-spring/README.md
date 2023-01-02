# 1. 프로젝트 환경 설정
## (1) 프로젝트 생성

## (2) 라이브러리 살펴보기
### 스프링 부트 라이브러리
- spring-boot-starter-web
  - spring-boot-starter-tomcat: 톰캣 (웹서버) 
  - spring-webmvc: 스프링 웹 MVC
- spring-boot-starter-thymeleaf: 타임리프 템플릿 엔진(View) 
- spring-boot-starter(공통): 스프링 부트 + 스프링 코어 + 로깅
  - spring-boot 
    - spring-core
  - spring-boot-starter-logging 
    - logback, slf4j

### 테스트 라이브러리
- spring-boot-starter-test 
  - junit: 테스트 프레임워크 
  - mockito: 목 라이브러리 
  - assertj: 테스트 코드를 좀 더 편하게 작성하게 도와주는 라이브러리 
  - spring-test: 스프링 통합 테스트 지원

## (3) View 환경설정
### Welcome Page 만들기
- 스프링 부트가 제공하는 Welcome Page 기능
  - static/index.html 을 올려두면 Welcome page 기능을 제공한다.
  - https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/html/spring-boot-features.html#boot-features-spring-mvc-welcome-page

### thymeleaf 템플릿 엔진


cf. `spring-boot-devtools` 라이브러리를 추가하면, `html` 파일을 컴파일만 해주면 서버 재시작 없이 View 파일 변경이 가능하다.

## (4) 빌드하고 실행하기
- 터미널에서 /hello-spring 폴더로 이동
- `./gradlew build`
- `cd build/libs`
- `java -jar hello-spring-0.0.1-SNAPSHOT.jar`
- 실행 확인
  - http://localhost:8080/ 실행
  - http://localhost:8080/hello 실행
  

cf. `./gradlew clean` 하면, build 폴더 없어짐
cf. `./gradlew clean build` 하면, build 폴더 삭제되고 다시 빌드함

# 2. 스프링 웹 개발 기초
- 웹 개발에서 3가지 방법은 다음과 같다.
  - (1) 정적 컨텐츠 : 파일을 `그대로` 클라이언트에게 전달함.
  - (2) MVC와 템플릿 엔진 : 서버에서 `html 좀 변형을 해서` 클라이언트에게 전달함.
  - (3) API
    - JSON 포맷으로 모바일 개발자(또는 React 등 개발자)에게  전달함.
    - 서버끼리 통신할 때 사용함.

## (1) 정적 컨텐츠
1. 브라우저가 `http://localhost:8080/hello-static.html`로 요청을 하면, 내장 톰캣 서버가 요청을 받는다.
2. 요청을 받은 내장 톰캣 서버는 이 요청을 스프링에게 넘긴다.
3. 스프링 부트는 hello-static 관련 컨트롤러가 있는지 먼저 확인한다. -> 콘트롤러가 우선순위를 갖는다.
4. 스프링 부트가 확인 후 없으면, `static/hello-static.hmtl`를 찾는다.

## (2) MVC와 템플릿 엔진
- MVC: Model, View, Controller

## (3) API
### @ResponseBody 사용 원리

cf. Getter/Setter 방식 : 자바빈 표준 규약 또는 프로퍼티 접근 방식이라고도 함.
- control + Enter 또는 command + N : Getter/Setter 자동 설정


# 3. 회원 관리 예제 - 백엔드 개발
1. 리포지토리
2. 서비스
- 비즈니스에 가까운 용어를 사용해라

cf. IntelliJ 단축기
- shift + F6 : 똑같은 변수를 찾아 Rename
- command + option + V : 자동으로 담겨질 변수의 타입을 가져옴
- ctrl + T : 리팩토링과 관련된 여러가지 것들이 나옴
- commnad + shfit + T : 테스트 파일 자동 생성
- ctrl + R : 이전에 실행했던거 다시 실행


## 테스트 코드 작성
### `@AfterEach` 
> 테스트는 각각 독립적으로 실행되어야 한다. 테스트 순서에 의존관계가 있는 것은 좋은 테스트가 아니다
- 한번에 여러 테스트를 실행하면 메모리 DB에 직전 테스트의 결과가 남을 수 있다. 
- 이렇게 되면 다음 이전 테스트 때문에 다음 테스트가 실패할 가능성이 있다. 
- @AfterEach 를 사용하면 각 테스트가 종료될 때 마다 이 기능을 실행한다. 
- 여기서는 메모리 DB에 저장된 데이터를 삭제한다.


# 4. 스프링 빈과 의존관계
## 스프링 빈을 등록하는 2가지 방법
### 방법 1. 컴포넌트 스캔과 자동 의존관계 설정 : @Service, @Repository, @Controller 등을 추가해주는 방법
<컴포넌트 스캔 원리>
- @Component 뿐만 아니라 @Component를 포함하는 어노테이션(예 : @Controller, @Service, @Repository 등)이 있으면, 스프링 빈으로 자동 등록된다.
- @Controller 컨트롤러가 스프링 빈으로 자동 등록되는 이유도 컴포넌트 스캔 때문이다.



### 방법 2. 자바 코드로 직접 스프링 빈 등록하기


### @Autowired
