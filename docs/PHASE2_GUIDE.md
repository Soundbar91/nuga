# Phase 2: 계층형 멀티 모듈 구조 (Layered Architecture)

## 학습 목표
- 3-tier 계층형 아키텍처 이해 및 구현
- 모듈 간 의존성 관리 방법 학습
- 공통 모듈 분리를 통한 재사용성 향상
- core 모듈에서 domain/infrastructure 분리의 필요성 이해

---

## 2.1 3-tier 모듈 구조 구성

### Phase 1에서 Phase 2로의 진화

#### Phase 1: 2-tier 구조의 한계
```
multi-module-study/
├── api/            # REST API 계층
└── core/           # 비즈니스 로직 + 데이터 접근 혼재
```

**문제점**:
- 비즈니스 로직과 기술 구현(DB 접근)이 같은 모듈에 존재
- 도메인 로직의 독립성 부족
- 테스트 시 기술 의존성을 모킹해야 함
- 향후 DDD, 클린 아키텍처로 확장 어려움

#### Phase 2: 3-tier 구조의 장점
```
multi-module-study/
├── api/                    # Presentation Layer
├── domain/                 # Business Logic Layer
└── infrastructure/         # Data Access Layer
```

**개선점**:
- 관심사의 명확한 분리 (Separation of Concerns)
- 도메인 로직의 독립성 확보
- 의존성 역전 원칙(DIP) 적용 가능
- 테스트 용이성 향상

### 계층별 역할과 책임

#### 1. Presentation Layer (api)
```
api/
├── src/
│   └── main/
│       └── java/
│           └── com/soundbar91/project/api/
│               ├── controller/          # REST API 엔드포인트
│               │   └── HelloController.java
│               ├── dto/                 # 데이터 전송 객체
│               │   ├── request/
│               │   └── response/
│               ├── exception/           # 전역 예외 처리
│               ├── config/              # 웹 설정
│               └── ApiApplication.java  # 메인 애플리케이션
└── build.gradle
```

**책임**:
- HTTP 요청/응답 처리
- 입력 유효성 검증 (Validation)
- DTO ↔ Domain 변환
- API 문서화 (Swagger/OpenAPI)
- 인증/인가 (Controller 레벨)

**의존성**: common, domain, infrastructure

**예시 코드**:
```java
@RestController
@RequestMapping("/api/hello")
@RequiredArgsConstructor
public class HelloController {

    private final HelloService helloService;

    @GetMapping
    public String hello(@RequestParam(required = false) String name) {
        return helloService.getGreeting(name);
    }
}
```

#### 2. Business Logic Layer (domain)
```
domain/
├── src/
│   └── main/
│       └── java/
│           └── com/soundbar91/domain/
│               ├── entity/              # 도메인 엔티티
│               ├── service/             # 비즈니스 로직
│               │   └── HelloService.java
│               ├── repository/          # Repository 인터페이스
│               └── vo/                  # Value Object
└── build.gradle
```

**책임**:
- 핵심 비즈니스 로직
- 도메인 모델 정의 (Entity, VO)
- Repository 인터페이스 정의 (구현은 infrastructure)
- 도메인 규칙 및 제약사항 관리

**의존성**: common만 의존 (기술 독립성 유지)

**예시 코드**:
```java
@Service
public class HelloService {

    public String getGreeting(String name) {
        if (name == null || name.isBlank()) {
            return "Hello, Guest!";
        }
        return "Hello, " + name + "!";
    }
}
```

#### 3. Data Access Layer (infrastructure)
```
infrastructure/
├── src/
│   └── main/
│       └── java/
│           └── com/soundbar91/infrastructure/
│               ├── persistence/         # JPA Repository 구현
│               │   ├── entity/         # JPA Entity (선택)
│               │   └── repository/     # Repository 구현체
│               ├── external/           # 외부 API 연동
│               └── config/             # DB, 외부 서비스 설정
└── build.gradle
```

**책임**:
- Repository 인터페이스 구현
- JPA, MyBatis 등 데이터 접근 기술
- 외부 API 호출
- 메시징 시스템 연동 (Kafka, RabbitMQ)
- 캐싱 구현 (Redis)

**의존성**: common, domain

**예시 Repository 인터페이스** (domain에 정의):
```java
// domain/repository/UserRepository.java
public interface UserRepository {
    User findById(Long id);
    User save(User user);
}
```

**예시 Repository 구현** (infrastructure에 구현):
```java
// infrastructure/persistence/repository/UserJpaRepository.java
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    public User findById(Long id) {
        return jpaRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User save(User user) {
        return jpaRepository.save(user);
    }
}
```

### 계층 간 의존성 방향

```
┌─────────────────┐
│   api (표현)    │
└────────┬────────┘
         │ depends on
         ▼
┌─────────────────┐
│ infrastructure  │
│   (기술 구현)   │
└────────┬────────┘
         │ depends on
         ▼
┌─────────────────┐
│   domain        │
│  (비즈니스 로직) │
└────────┬────────┘
         │ depends on
         ▼
┌─────────────────┐
│    common       │
│  (공통 유틸)    │
└─────────────────┘
```

**핵심 원칙**:
- 상위 계층이 하위 계층에 의존
- 하위 계층은 상위 계층을 모름 (의존하지 않음)
- domain은 기술 독립적 (순수 비즈니스 로직)

---

## 2.2 모듈 간 의존성 관리

### Gradle 의존성 설정

#### api/build.gradle
```gradle
plugins {
    id 'org.springframework.boot'
}

dependencies {
    implementation project(':common')
    implementation project(':domain')
    implementation project(':infrastructure')
    implementation 'org.springframework.boot:spring-boot-starter-web'
}

bootJar {
    enabled = true  // 실행 가능한 JAR
}

jar {
    enabled = false
}
```

#### infrastructure/build.gradle
```gradle
plugins {
    id 'org.springframework.boot'
}

dependencies {
    implementation project(':common')
    implementation project(':domain')
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    runtimeOnly 'com.h2database:h2'
}

bootJar {
    enabled = false  // 라이브러리로 사용
}

jar {
    enabled = true
}
```

#### domain/build.gradle
```gradle
plugins {
    id 'org.springframework.boot'
}

dependencies {
    implementation project(':common')
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
}

bootJar {
    enabled = false
}

jar {
    enabled = true
}
```

#### common/build.gradle
```gradle
plugins {
    id 'org.springframework.boot'
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter'
}

bootJar {
    enabled = false
}

jar {
    enabled = true
}
```

### implementation vs api

#### implementation (권장)
```gradle
dependencies {
    implementation project(':domain')
}
```
- 의존성이 현재 모듈 내부에서만 사용됨
- 컴파일 속도 향상 (변경 시 현재 모듈만 재컴파일)
- 의존성 노출 방지

#### api (필요 시에만)
```gradle
dependencies {
    api project(':common')
}
```
- 의존성이 외부로 노출됨 (전이 의존성)
- 사용 사례: common 모듈처럼 여러 모듈에서 공통으로 사용하는 경우

### 순환 의존성 방지

#### 잘못된 예시 (순환 의존성 발생)
```
api → domain → infrastructure → api  ❌
```

#### 올바른 예시 (단방향 의존성)
```
api → infrastructure → domain → common  ✅
```

**순환 의존성 발생 시 해결 방법**:
1. **인터페이스 분리**: 공통 인터페이스를 하위 모듈로 이동
2. **이벤트 기반 통신**: Spring Event 활용
3. **모듈 재구성**: 의존성 방향 재설계

### transitive dependency 관리

#### 문제 상황
```
api → domain (v1.0)
api → infrastructure → domain (v2.0)
```
서로 다른 버전의 domain이 전이되어 충돌 발생

#### 해결 방법
```gradle
// 루트 build.gradle
subprojects {
    configurations.all {
        resolutionStrategy {
            // 버전 충돌 시 최신 버전 사용
            preferProjectModules()

            // 특정 버전 강제
            force 'com.example:some-library:1.0.0'
        }
    }
}
```

---

## 2.3 공통 모듈 분리

### common 모듈의 필요성

**왜 common 모듈이 필요한가?**
- 여러 모듈에서 공통으로 사용하는 코드 중복 제거
- 공통 유틸리티, 예외, 상수 등 관리
- 의존성 최소화 (다른 모듈에 의존하지 않음)

### common 모듈 구조

```
common/
├── src/
│   └── main/
│       └── java/
│           └── com/soundbar91/common/
│               ├── exception/          # 공통 예외
│               │   ├── BusinessException.java
│               │   ├── ErrorCode.java
│               │   └── NotFoundException.java
│               ├── util/               # 유틸리티 클래스
│               │   ├── DateUtil.java
│               │   ├── StringUtil.java
│               │   └── ValidationUtil.java
│               ├── constant/           # 공통 상수
│               │   └── AppConstants.java
│               └── dto/                # 공통 DTO
│                   ├── PageRequest.java
│                   └── PageResponse.java
└── build.gradle
```

### common 모듈 설계 원칙

#### 1. 의존성 최소화
```gradle
dependencies {
    // Spring Boot starter만 의존
    implementation 'org.springframework.boot:spring-boot-starter'

    // 특정 도메인 모듈에 의존하지 않음 ❌
    // implementation project(':domain')
}
```

#### 2. 공통 예외 정의
```java
// common/exception/BusinessException.java
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}

// common/exception/ErrorCode.java
public enum ErrorCode {
    INVALID_INPUT("E001", "잘못된 입력입니다."),
    NOT_FOUND("E002", "리소스를 찾을 수 없습니다."),
    UNAUTHORIZED("E003", "인증이 필요합니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // getters...
}
```

#### 3. 유틸리티 클래스
```java
// common/util/StringUtil.java
public final class StringUtil {

    private StringUtil() {
        throw new AssertionError("Utility class");
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String maskEmail(String email) {
        // 이메일 마스킹 로직
    }
}
```

#### 4. 공통 상수 관리
```java
// common/constant/AppConstants.java
public final class AppConstants {

    private AppConstants() {
        throw new AssertionError("Constant class");
    }

    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
```

### common 모듈 사용 예시

#### domain에서 common 사용
```java
// domain/service/UserService.java
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }
}
```

#### api에서 common 사용
```java
// api/exception/GlobalExceptionHandler.java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        ErrorResponse response = new ErrorResponse(
            e.getErrorCode().getCode(),
            e.getMessage()
        );
        return ResponseEntity.badRequest().body(response);
    }
}
```

---

## 실습 과제

### 과제 1: core 모듈을 domain과 infrastructure로 분리

1. **domain 모듈 생성**
   ```bash
   mkdir -p domain/src/main/java/com/soundbar91/domain
   ```

2. **infrastructure 모듈 생성**
   ```bash
   mkdir -p infrastructure/src/main/java/com/soundbar91/infrastructure
   ```

3. **settings.gradle 수정**
   ```gradle
   include 'common'
   include 'domain'
   include 'infrastructure'
   include 'api'
   ```

4. **core의 Service를 domain으로 이동**
   - Service, Entity는 domain으로
   - Repository 구현체는 infrastructure로

### 과제 2: common 모듈 생성 및 공통 코드 이동

1. **common 모듈 생성**
   ```bash
   mkdir -p common/src/main/java/com/soundbar91/common
   ```

2. **공통 예외 클래스 작성**
   - BusinessException
   - ErrorCode enum

3. **유틸리티 클래스 작성**
   - StringUtil
   - DateUtil

### 과제 3: 의존성 설정 및 빌드 확인

1. **각 모듈의 build.gradle 작성**
   - 의존성 방향 확인: api → infrastructure → domain → common

2. **빌드 테스트**
   ```bash
   # 전체 빌드
   ./gradlew clean build

   # 의존성 트리 확인
   ./gradlew :api:dependencies

   # 순환 의존성 체크
   ./gradlew :api:checkDependencies
   ```

3. **애플리케이션 실행**
   ```bash
   ./gradlew :api:bootRun

   # 테스트
   curl http://localhost:8080/api/hello?name=World
   ```

### 과제 4: 컴포넌트 스캔 설정

**api 모듈의 Application 클래스에 컴포넌트 스캔 범위 설정**:
```java
@SpringBootApplication(scanBasePackages = {
    "com.soundbar91.project.api",
    "com.soundbar91.domain",
    "com.soundbar91.infrastructure",
    "com.soundbar91.common"
})
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
```

---

## 체크리스트

- [ ] core 모듈을 domain과 infrastructure로 분리했는가?
- [ ] common 모듈을 생성하고 공통 코드를 이동했는가?
- [ ] 의존성 방향이 올바른가? (api → infrastructure → domain → common)
- [ ] 순환 의존성이 없는가?
- [ ] 각 모듈의 build.gradle이 올바르게 설정되었는가?
- [ ] bootJar/jar 설정이 적절한가? (api만 bootJar, 나머지는 jar)
- [ ] 컴포넌트 스캔 범위가 올바르게 설정되었는가?
- [ ] 빌드가 정상적으로 수행되는가?
- [ ] 애플리케이션이 정상적으로 실행되는가?
- [ ] 각 계층의 책임이 명확하게 분리되었는가?

---

## 핵심 개념 정리

### 1. 왜 core를 분리하는가?

| 측면 | core (2-tier) | domain + infrastructure (3-tier) |
|------|---------------|----------------------------------|
| **관심사 분리** | 비즈니스 로직과 기술 구현 혼재 | 명확히 분리 |
| **테스트** | DB 의존성 필요 | 순수 도메인은 독립 테스트 가능 |
| **확장성** | DDD, 헥사고날 적용 어려움 | 쉽게 확장 가능 |
| **의존성** | 기술 의존적 | domain은 기술 독립적 |

### 2. 의존성 역전 원칙 (DIP)

```
Before (core):
Service → Repository 구현체 (직접 의존)

After (domain + infrastructure):
Service → Repository 인터페이스 ← Repository 구현체
(domain)   (domain)              (infrastructure)
```

domain은 인터페이스만 정의하고, infrastructure가 구현을 제공함으로써 의존성을 역전시킵니다.

### 3. 모듈별 패키지 네이밍

- **api**: `com.soundbar91.project.api`
- **domain**: `com.soundbar91.domain`
- **infrastructure**: `com.soundbar91.infrastructure`
- **common**: `com.soundbar91.common`

---

## 다음 단계

Phase 2를 완료하면 다음 내용을 학습합니다:
- **Phase 3**: 도메인 중심 멀티 모듈 (Domain-Driven Design)
- Bounded Context 개념
- 도메인별 모듈 분리
- 도메인 간 통신 패턴

---

## 참고 자료

- [Clean Architecture - Robert C. Martin](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Domain-Driven Design - Eric Evans](https://www.domainlanguage.com/ddd/)
- [Spring Boot Multi-Module Best Practices](https://spring.io/guides/gs/multi-module/)
- [Gradle Dependency Management](https://docs.gradle.org/current/userguide/dependency_management.html)
- [Package by Feature vs Package by Layer](https://phauer.com/2020/package-by-feature/)
