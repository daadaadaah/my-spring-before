# 스프링 핵심 원리 - 기본편
## 1. 객체지향 설계와 스프링
## 2. 스프링 핵심 원리 이해 1 - 예제 만들기
## 3. 스프링 핵심 원리 이해 2 - 객체 지향 원리 적용
### DI 컨테이너(= IoC 컨테이너)
- AppConfig 객체를 생성하고 관리하면서, 의존관계를 연결해주는 것을 말한다. 

### 스프링 컨테이너
- 기존에 개발자가 AppConfig를 사용해서 직접 객체를 생성하고 DI를 했다.
- 그런데, 이제부터는 `스프링 컨테이너`를 통해서 사용한다.
- `스프링 컨테이너`는 `@Configuration`이 붙은 `AppConfig`를 설정 정보로 사용한다.
- 그리고, `@Bean`이라 적힌 메서드를 모두 호출해서 반환된 객체를 `스프링 컨테이너`에 등록한다.
- 이렇게 스프링 컨테이너에 등록된 객체를 `스프링 빈`이라고 한다.
            
## 4. 스프링 컨테이너와 스프링 빈
### (1) 스프링 컨테이너 생성

- 스프링 컨테이너 생성 방법에는 크게 2가지 있다.
1) 애노테이션 기반의 자바 설정 클래스로 만드는 방법
   - AppConfig를 사용했던 방식을 말한다.
   - `ApplicationContext`가 스프링 컨테이너이다.
   - `AnnotationConfigApplicationContext`는 `ApplicationContext` 인터페이스의 구현체이다.
    ```java
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    ```
2) XML 기반으로 만든느 방법

- 빈 이름 : 기본적으로 메서드 이름을 사용하는데, `@Bean(name="NewService")`이렇게 직접 부여할 수도 있다.
- cf. 빈 이름은 항상 다른 이름으로 부여해야 한다. 같은 이름으로 부여하면, 다른 빈이 무시되거나 기존 빈을 덮어버리거나 설정에 따라 오류가 발생한다.

### (2) 컨테이너에 등록된 모든 빈 조회
- 스프링 컨테이너에서 스프링 빈을 찾는 가장 기본적인 조회 방법 2가지
  1) `ac.getBean(빈 이름, 타입)`
  2) `ac.getBean(타입)`
- 조회 대상이 스프링 빈에 없으면 예외(`NoSuchBeanDefinitionException: No bean named 'xxxxx' available`)가 발생한다.

### (3) 스프링 빈 조회 - 기본


## 5. 싱글톤 컨테이너
### (1) 스프링 없는 순수한 DI 컨테이너
- 우리가 만들었던 스프링 없는 순수한 DI 컨테이너인 `AppConfig`는 요청을 할 때 마다 객체를 새로 생성한다.
- 이렇게 될 경우, 고객 트래픽이 초당 100이 나오면 초당 100개 객체가 생성되고 소멸되어, 메모리 낭비가 심하다.
- 이를 해결하기 위해서 해당 객체가 딱 1개만 생성되고, 공유하도록 설계하면 되는데, 이를 `싱글톤 패턴` 이라고 한다.

### (2) 싱글톤 패턴

1) 구현 방법
> 싱글톤 패턴을 구하는 방법에는 여러가지가 있지만, 여기서는 객체를 미리 생서해두는 가장 단순하고 안전한 방법을 택했다.
- `static` 영역에 객체 `instance`를 미리 하나 생성해서 올려둔다. 
- 이 객체 인스턴스가 필요하면 오직 `getInstance()` 메서드를 통해서만 조회할 수 있다. 이 메서드를 호출하면 항상 같은 인스턴스를 반환한다. 
- 딱 1개의 객체 인스턴스만 존재해야 하므로, 생성자를 `private`으로 막아서 혹시라도 외부에서 new 키워드로 객체 인스턴스가 생성되는 것을 막는다.
2) 효과
- 싱글톤 패턴을 적용하면, 클라이언트의 요청이 올 때마다 객체를 생성하지 않고, 이미 만들어진 객체를 공유해서 효율적으로 사용할 수 있다.
- 그런데, 다음과 같은 문제점도 있다.
3) 문제점
- 싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
- 의존관계상 클라이언트가 구체 클래스에 의존한다. -> DIP 위반
- 클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높다.
- 테스트하기 어렵다.
- 내부 속성을 변경하거나 초기화하기 어렵다.
- private 생성자로 자식 클래스를 만들기 어렵다.
- 결론적으로 유연성이 떨어진다.
- 안티패턴으로 불리기도 한다.

### (3) 싱글톤 컨테이너
- 상기 싱글톤 패턴의 모든 문제를 해결하면서, 객체 인스턴스를 싱글톤(1개만 생성)으로 관리하는 것이 `스프링 컨테이너`이다.
- 지금까지 우리가 학습한 `스프링 빈`이 바로 싱글톤으로 관리되는 빈이다.


### (4) 싱글톤 방식의 주의점


### (5) @Configuration과 싱글톤
- @Configuration는 사실 싱글톤을 위해 존재하는 것이다.

## 6. 컴포넌트 스캔
### (1) 컴포넌트 스캔과 의존관계 자동 주입 시작하기
- 스프링은 설정 정보가 없어도 자동으로 스프링 빈을 등록하는 `컴포넌트 스캔`이라는 기능을 제공하고, 의존관계도 자동으로 주입하는 `@Autowird`라는 기능도 제공한다.
- 
### (2) 탐색 위치와 기본 스캔 대상
- 모든 자바 클래스를 다 컴포넌트 스캔하면 시간이 오래 걸린다.
- 그래서, 꼭 필요한 위치부터 탐색하도록 시작 위치를 지정할 수 있다.
```java
@ComponentScan(
        basicPackages = "hello.core"
)
```
- `basicePackages`
- `basePackageClasses`
- `@Component`
- `@Controller`
- `@Service`
- `@Repository`
- `@Configuration`

### (3) 필터
- `includeFilters` : 컴포넌트 스캔 대상을 추가로 지정한다. 
- `excludeFilters` : 컴포넌트 스캔에서 제외할 대상을 지정한다.



### (4) 중복 등록과 충돌

- `/resources/application.properties`에 `spring.main.allow-bean-definition-overriding=true` 설정 추가

## 7. 의존관계 자동 주입
### (1) 다양한 의존관계 주입 방법
- 의존관계 주입은 크게 4가지 방법이 있다.
  1) 생성자 주입
  2) 수정자 주입 (setter) 주입
  3) 필드 주입
  4) 일반 메서드 주입

#### 1) 생성자 주입 
```java
    @Component 
    public class OrderServiceImpl implements OrderService {
        private final MemberRepository memberRepository;
        private final DiscountPolicy discountPolicy;

        @Autowired
        public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
            this.memberRepository = memberRepository;
            this.discountPolicy = discountPolicy;
        }
    }
```
- 생성자를 통해 의존관계를 주입받는 방법이다. 
- 생성자 주입의 경우, 빈을 등록하면서 의존관계 주입도 같이 일어난다.
- 특징은 a) 생성자 호출시점에 딱 1번만 호출되는 것이 보장된다는 것과 b) "불변(수정되지 않게!), 필수" 의존관계에 사용된다는 것이다.
- 중요! 생성자가 1개만 있으면, `@Autowired`를 생략해도 자동 주입 된다. 물론 스프링 빈에만 해당한다.

#### 2) 수정자 주입 (setter) 주입
```java
    @Component 
    public class OrderServiceImpl implements OrderService {
        private final MemberRepository memberRepository;
        private final DiscountPolicy discountPolicy;

        @Autowired
        public void setMemberRepository(MemberRepository memberRepository) {
            this.memberRepository = memberRepository;
        }
        @Autowired
        public void setDiscountPolicy(DiscountPolicy discountPolicy) {
            this.discountPolicy = discountPolicy;
        }
    }
```
- setter라 불리는 필드의 값을 변경하는 수정자 메서드를 통해서 의존관계를 주입하는 방법이다.
- 생성자 주입이 일어난 다음, 일어난다.
- 특징은 a) 선택(필수 아니다! required = false), 변경 가능성이 있는 의존관계에 사용된다는 것과 b) 자바빈 프로퍼티 규약의 수정자 메서드 방식을 사용하는 방법이다.
- `@Autowired` 의 기본 동작은 주입할 대상이 없으면 오류가 발생하는데, 주입할 대상이 없어도 동작하게 하려면 `@Autowired(required = false)` 로 지정하면 된다.

cf. 자바빈 프로퍼티
- 자바에서는 과거부터 필드의 값을 직접 변경하지 않고, `setXXX`, `getXXX` 라는 메서드를 통해서 값을 읽거나 수정하는 규칙을 만들었다.
- 그것이 `자바 프로퍼티 규약`이다.

#### 3) 필드 주입
```java
    @Component
    public class OrderServiceImpl implements OrderService {
        @Autowired private MemberRepository memberRepository;
        @Autowired private DiscountPolicy discountPolicy;
    }
```
- 필드에 바로 주입하는 방법이다.
- 특징은 a) 코드가 간결해서 많은 개발자들을 유횩하지만 외부에서 변경이 불가능해서 테스트하기 힘들다는 단점이 있는 것과 b) DI 프레임워크가 없으면 아무것도 할 수 없다라는 것이다.
- 따라서, 다음 2가지 경우를 제외하고는 되도록 사용하지 말자!
  - 경우 1) 애플리케이션의 실제 코드와 관계 없는 테스트 코드
    - 테스트를 스프링 컨테이너에서 하는 경우도 있다.
    ```java
        @SpringBootTest
        class CoreApplicationTests {
            @Autowired
            OrderService orderService;
            
            @Test
            void contextLoads() {
                // ...
                assertThat(orderService.getId()).isEqualTo(1);
            }
        
        }
    ```
  - 경우 2) 스프링 설정을 목적으로 하는 `@Configuration` 같은 곳에서만 특별한 용도로 사용

#### 4) 일반 메서드 주입
```java
    @Component
    public class OrderServiceImpl implements OrderService {
        private MemberRepository memberRepository;
        private DiscountPolicy discountPolicy;
        
        @Autowired
        public void init(MemberRepository memberRepository, DiscountPolicy  discountPolicy) {
            this.memberRepository = memberRepository;
            this.discountPolicy = discountPolicy;
        }
    }
```
- 일반 메서드를 통해 주입받는 방법이다.
- 특징은 a) 한번에 여러 필드를 주입받을 수 있다는 것과 b) 잘 사용하지 않는 것이다.



### (2) 옵션 처리
#### 1) @Autowired(required=false)
- 자동 주입 대상이 없으면, 수정자 메서드 자체가 호출이 안됨
#### 2) org.springframework.lang.@Nullable
- 자동 주입 대상이 없으면, null이 입력됨
#### 3) Optional<>
- 자동 주입 대상이 없으면, Optional.empty가 입력됨

### (3) 생성자 주입을 선택하라
- 과거에는 수정자 주입과 필드 주비을 많이 사용했지만, 최근에는 스프링을 포함한 DI 프레임워크 대부분이 생성자 주입을 권장한다.
- 그 이유는 다음과 같다.
- [불변]
  - 대부분의 의존관계 주입은 한번 일어나면 애플리케이션 종
- [누락] : 컴파일 단계에서 누락 정보를 알 수 있다?
  - 프레임워크 없이 순수한 자바 코드를 단위 테스트 하는 경우를 생각해보자.
  - 먼저, 다음과 같이 수정자 의존관계인 경우, `@Autowired`가 프레임워크 안에서 동작할 때는 의존관계가 없으면 오류가 발생한다.
  - 그런데, 지금은 프레임워크 없이 순수한 자바 코드로만 단위 테스트를 수행하고 있다.
  - 이렇게 테스트를 수행하면, 실행은 되는데, 그 결과는 NPE(Null Point Exception)이 발생한다.
  - 왜냐하면, memberRepository, discountPolicy 모두 의존관계 주입이 누락되었기 때문이다.
  - 
- [final 키워드] final 키워드를 사용할 수 있다.
  - 생성자 주입을 사용하면 필드에 `final` 키워드를 사용할 수 있다.
  - 이렇게 `final` 키워드를 사용하게 되면, 생성장에서 혹시라도 실수로 개발자가 값을 설정하지 않았을 경우, 컴파일 시점에서 알려주는 장점이 있다.
  - cf. 수정자 주입을 포함한 나머지 주입 방식은 모두 생성자 이후에 호출되므로, 필드에 `final` 키워드를 사용할 수 없고, 오직 생성자 주입 방식만 `final` 키워드를 사용할 수 있다.
<정리>
  - 생성자 주입 방식을 선택하는 이유는 여러가지가 있지만, 프레임워크에 의존하지 않고, 순수한 자바 언어의 특징을 잘 살리는 방법 때문이다.
  - 기본으로 생성자 주입을 사용하고, 필수값이 아닌 경우에는 수정자 주입 방식을 옵션으로 부여하면 된다.
  - 즉, 생성자 주입과 수정자 주입을 동시에 사용할 수 있다.
  - 따라서, 항상 생성자 주입을 선택하고, 가끔 옵션이 필요하면 그때 수정자 주입을 선택해라. 필드 주입은 사용하지 않는게 좋다.

### (4) 룸복과 최신 트렌드
#### Lombok 이란?
- Lombok은 객체의 `getter/setter` 등 같이 귀찮게 반복적으로 만들어야하는 것을 쉽게 만들 수 있도록 도와주는 라이브러리이다.
#### Lombok 라이브러리 셋팅

#### Lombok 기능 
> 다른 기능들을 알고 싶으면, https://projectlombok.org/features/ 방문하기
1) @Getter/@Setter
2) @RequiredArgsConstructor

### (5) 조회 빈이 2개 이상 - 문제

### (6) 조회 빈이 2개 이상 - 해결방법 : @Autowired 필드면, @Qualifier, @Primary
- 조회 대상 빈이 2개 이상일 때, 해결방법 3가지
1) `@Autowired` 필드명 매칭
- 타입 매칭을 먼저 시도한다.
- 타입 매칭의 결과가 2개 이상일 경우, 필드명, 파라미터 명으로 빈 이름을 매칭한다.
2) `@Quilifier` -> `@Quilifier` 끼리 매칭 -> 빈 이름 매칭
- 
3) `@Primary` 사용

4) `@Quilifier` vs `@Primary` : 우선순위

### (7) 애노테이션 직접 만들기


### (8) 조회한 빈이 모두 필요할 때, List, Map

### (9) 자동, 수동의 올바른 실무 운영 기준₩

## 8. 빈 생명주기 콜백
### (1) 빈 생명주기 콜백 시작
- 스프링 빈의 이벤트 (싱글턴 패턴) 라이프사이클 : 스프링컨테이너생성 -> 스프링빈생성 -> 의존관계주입 -> 초기화콜백 -> 사용 -> 소멸전콜백 -> 스프링 종료


- 스프링은 크게 3가지 방법으로 빈 생명주기 콜백을 지원한다.
방법 1. 인터페이스(InitializingBean, DisposableBean)
방법 2. 설정 정보에 초기화 메서드, 종료 메서드 지정
방법 3. `@PostConstruct`, `@PreDestory` 애노테이션 지원

### (2) 인터페이스 InitializingBean, DisposableBean
### (3) 빈 등록 초기화, 소멸 메서드
### (4) 애노테이션 @PostConstruct, @PreDestory
- 유일한 단점은 외부 라이브러리에는 적용하지 못한다는 것이다. 외부 라이브러리를 초기화, 종료 해야 하면 ` @Bean(initMethod = "init", destroyMethod = "close")`의  기능을 사용하자.



## 9. 빈 스코프
### (1) 빈 스코프란?
- 스프링 Bean은 스프링 컨테이너가 만들어질 때, 함께 생성되었다가 스프링 컨테이너가 종료될 때까지 유지된다.
- 이것은 기본적으로 스프링 Bean이 싱글톤 스코프로 생성되기 때문이다.
- 스코프는 빈이 존재할 수 있는 범위를 말한다.

cf. 스프링은 다음과 같은 다양한 스코프(* : 이정돈 알고 있어야 함)를 지원한다.
1) (*) 싱글톤 스코프
- 기본 스코프이다.
- 스프링 컨테이너의 시작과 종료까지 유지되는 가장 넓은 범위의 스코프다.
2) (*) 프로토타입 스코프
- 스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입까지만 관여하고 더이상 관리하지 않는 매우 짧은 범위의 스코프다.
3) 웹 관련 스코프
- (*) request : 웹 요청이 들어오고 나갈 때까지 유지되는 스코프이다.
- session : 웹 세션이 생성되고 종료될 때까지 유지되는 스코프이다.
- application : 웹의 서블릿 컨텍스와 같은 범위로 유지되는 스코프다.



### (2) 프로토타입 스코프


### (3) 프로토타입 스코프 - 싱글톤 빈과 함꼐 사용시 문제점



### (4) 프로토타입 스코프 - 싱글톤 빈과 함께 사용시 Provider로 문제
### (5) 웹 스코프
### (6) request 스코프 예제 만들기
### (7) request 스코프 예제 문제 해결방법 1 : 스코프와 Provider
### (8) request 스코프 예제 문제 해결방법 2 : 스코프와 프록시
