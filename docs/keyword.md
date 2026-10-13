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