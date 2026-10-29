# 키워드 과제

## 1차 과제

### final / static / static final은 각각 어떤 역할을 하며, 차이는 무엇인가요?
- static: 프로그램 실행 시 메모리의 Static 영역에 할당 -> 객체 생성 없이 사용할 수 있고 모든 객체가 값을 공유합니다.
- final: 안 바뀌는 값에 사용
- static final: C로 치면 #define

#### 선택할 때 해야 할 질문
1) 값이 바뀌나요?  
no -> final / static final
2) 값이 어느 시점에 정해지나요?  
final -> 객체가 생성 될 때  
static final -> 프로그램 실행 시

---

### Java의 Generic 타입은 무엇이며 왜 사용할까요?
```Java
public record ApiResponse<T>(boolean isSuccess, String code, String message, T data) { ... }
```
API 응답은 포스트 단건이 될 수도 있고 포스트 리스트가 될 수도 있습니다.
미리 알 수가 없으니 Generic 타입을 이용해서 나중에 정할 수 있도록 합니다.
장점: 타입 안정성, 타입 변환 불필요, 재사용성

---

### primitive type vs wrapper class 차이는 무엇이며 어떤 상황에서 무엇을 사용해야 할까요?
[primitive type vs wrapper class 차이](https://velog.io/@kimdy0915/%EA%B8%B0%EB%B3%B8%ED%98%95primitive-vs.-%EB%9E%98%ED%8D%BC-%ED%81%B4%EB%9E%98%EC%8A%A4wrapper-class)

|      | primitive | wrapper  |
|------|-----------|----------|
| null | 불가        | 가능       |
| 메모리  | stack     | heap     |
| 값 변경 | 가능        | 불가능      |
| 비교   | ==        | equals() |
| Generics 타입 | 불가        | 가능       |

---

### 여러분이 선택한 아키텍처의 각 요소 또는 계층은 어떤 역할과 책임을 가지는지 구체적으로 설명해주세요.
레이어드 아키텍처

#### Controller
HTTP 요청을 서비스로 전달합니다.
#### Service
비즈니스 로직을 처리합니다.
#### Repository
WAS와 DB 사이에서 데이터를 주고 받습니다. ~~쿼리문 적는 곳~~

세미나 자료엔 DTO 변환을 컨트롤러에서 책임진다고 되어있는데 개인적으론 서비스에서 처리하는걸 선호합니다.
사유: 지연로딩 관리하는게 넘 빡셉니다...

---

## 2차 과제

### 자바의 record는 무엇인가요? 일반적인 클래스와는 무엇이 다르고 언제 사용하는 것이 좋을까요?
DTO를 만들 때 주로 사용합니다.

- equals(), hashCode(), toString(), 생성자와 접근자를 자동으로 만들어줍니다.
- private final 로 필드 변경이 불가능합니다.

이미 Record를 상속한거라 일반적인 클래스와 다르게 상속이 안 됩니다.

---

### @controller와 @RestController의 차이는 무엇인가요?
- @Controller: HTML, JSP, Thymeleaf 같은 뷰 파일을 찾아 렌더링한 후 클라이언트에 전달
- @RestController: @Controller + @ResponseBody &rarr; JSON 또는 XML 형태로 변환되어 HTTP 응답 본문에 직접 담겨 전송

[@Controller와 @RestController의 차이](https://velog.io/@dyunge_100/Spring-Controller%EC%99%80-RestController%EC%9D%98-%EC%B0%A8%EC%9D%B4)

---

### DTO, VO, DAO는 무엇인가요? 왜 필요하고 언제 사용해야 할까요?
#### DTO
- 계층 사이에서 데이터를 옮기는 객체
- API 마다 요구하는 형태에 맞게 응답을 주기 위해서 사용
#### VO
- 값 자체를 나타내는 불변 객체로 값이 같으면 같은 것으로 취급한다. 
- ex)좌표 // ~~현준장 아티클 참고~~
#### DAO
- DB에 접근하는 객체
- DB에 서비스가 영향 받지 않도록 하기 위해

---

### RESTful은 결국 무엇을 말하는걸까요? 여러분의 생각을 작성해주세요!
리셋 아니고 레스트  
HTTP 규칙에 맞는 상태 코드와 동작에 맞는 메서드를 사용한다면 RESTful 하다고 볼 수 있지 않을까요?

API 경로가 getPost 가 아니고 GET /posts/{id} 형태로 되어야 한다... 도 있지만 사실 현업에서는 getPost 형태를 어쩔 수 없이 써야하는 경우도 있다고 들었던거 같습니다.  
POST로 조회하는 경우도 있다는데 신기하죠?

---

### Spring Bean의 생명주기란 무엇인가요? 각 생명 주기의 단계별로 어떤 의미와 특징을 가지고 있나요?
말 그대로 스프링 컨테이너에 의해 Bean이 생성 되고 사용 후 삭제 될 때까지를 뜻합니다.

1. 컨테이너 생성
2. 빈 객체 생성
3. 의존성 주입 (DI)
4. 초기화 콜백
5. 사용
6. 소멸 전 콜백
7. 컨테이너 종료