# Phase 1: 멀티 모듈 기초

## 학습 목표
- 멀티 모듈 아키텍처의 기본 개념 이해
- Gradle을 사용한 멀티 프로젝트 설정 방법 학습
- 기본적인 2-tier 모듈 구조 (api, core) 구성

---

## 1.1 멀티 모듈 아키텍처란?

### 정의
멀티 모듈 아키텍처는 하나의 프로젝트를 여러 개의 독립적인 모듈로 분리하여 관리하는 구조입니다. 각 모듈은 특정한 책임과 역할을 가지며, 명확한 인터페이스를 통해 다른 모듈과 통신합니다.

### 모놀리식 vs 멀티 모듈

#### 모놀리식 (Monolithic)
```
single-project/
├── src/
│   └── main/
│       └── java/
│           ├── controller/
│           ├── service/
│           ├── repository/
│           └── domain/
└── build.gradle
```
- **장점**: 단순한 구조, 빠른 초기 개발
- **단점**: 코드 결합도 증가, 빌드 시간 증가, 책임 경계 모호

#### 멀티 모듈 (Multi-Module)
```
multi-module-project/
├── api/                    # 웹 계층
│   ├── src/
│   └── build.gradle
├── core/                   # 비즈니스 로직
│   ├── src/
│   └── build.gradle
├── settings.gradle
└── build.gradle
```
- **장점**: 명확한 책임 분리, 모듈별 재사용 가능, 병렬 빌드 가능
- **단점**: 초기 설정 복잡, 의존성 관리 필요

### 멀티 모듈의 장점

1. **명확한 책임 분리**
   - 각 모듈이 특정 기능에 집중
   - 코드의 의도와 경계가 명확

2. **재사용성 향상**
   - 공통 모듈을 여러 프로젝트에서 사용 가능
   - 라이브러리처럼 모듈 배포 가능

3. **빌드 최적화**
   - 변경된 모듈만 빌드
   - 병렬 빌드로 시간 단축

4. **팀 협업 개선**
   - 모듈별로 담당자 지정 가능
   - 충돌 감소

5. **테스트 용이성**
   - 모듈 단위 독립 테스트
   - 의존성 주입 및 모킹 용이

### 멀티 모듈의 단점

1. **초기 설정 복잡도**
   - Gradle 설정 학습 필요
   - 모듈 구조 설계 필요

2. **의존성 관리**
   - 순환 의존성 방지 필요
   - 버전 충돌 관리

3. **과도한 분리 위험**
   - 너무 많은 모듈은 오히려 복잡도 증가
   - 적절한 균형 필요

---

## 1.2 Gradle 멀티 프로젝트 설정

### settings.gradle의 역할

`settings.gradle`은 Gradle 프로젝트의 진입점으로, 어떤 모듈들이 프로젝트에 포함되는지 정의합니다.

#### 기본 구조
```gradle
rootProject.name = 'multi-module-architecture-study'

// 모듈 포함
include 'api'
include 'core'
```

#### 중첩 모듈 구조
```gradle
// 방법 1: 개별 include
include 'api'
include 'domain:user-domain'
include 'domain:order-domain'

// 방법 2: 콜론 표기법
include 'domain:user-domain', 'domain:order-domain'
```

### build.gradle 계층 구조

#### 루트 build.gradle
모든 서브 프로젝트에 공통으로 적용되는 설정을 정의합니다.

```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '4.0.1' apply false
    id 'io.spring.dependency-management' version '1.1.7' apply false
}

// 모든 서브 프로젝트에 적용
subprojects {
    apply plugin: 'java'
    apply plugin: 'io.spring.dependency-management'

    group = 'com.soundbar91'
    version = '0.0.1-SNAPSHOT'

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        // 모든 모듈의 공통 의존성
        compileOnly 'org.projectlombok:lombok'
        annotationProcessor 'org.projectlombok:lombok'

        testImplementation 'org.springframework.boot:spring-boot-starter-test'
    }
}
```

#### 서브 모듈 build.gradle
각 모듈별 특화된 의존성을 정의합니다.

**api/build.gradle**
```gradle
plugins {
    id 'org.springframework.boot'
}

dependencies {
    implementation project(':core')
    implementation 'org.springframework.boot:spring-boot-starter-web'
}
```

**core/build.gradle**
```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    runtimeOnly 'com.h2database:h2'
}
```

### 플러그인 적용 전략

#### apply false 패턴
```gradle
// 루트 build.gradle
plugins {
    id 'org.springframework.boot' version '4.0.1' apply false
}
```
- 플러그인 버전을 정의하지만 루트 프로젝트에는 적용하지 않음
- 서브 모듈에서 버전 없이 사용 가능

#### bootJar vs jar
```gradle
// 실행 가능한 모듈 (api)
bootJar {
    enabled = true
}
jar {
    enabled = false
}

// 라이브러리 모듈 (core)
bootJar {
    enabled = false
}
jar {
    enabled = true
}
```

---

## 1.3 기본 모듈 구조 구성

### 2-tier 모듈 구조

```
multi-module-study/
├── api/                           # Presentation Layer
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/soundbar91/project/api/
│   │   │   │       ├── controller/
│   │   │   │       ├── dto/
│   │   │   │       └── ApiApplication.java
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   └── build.gradle
├── core/                          # Business Logic Layer
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       └── com/soundbar91/project/core/
│   │   │           ├── domain/
│   │   │           ├── service/
│   │   │           └── repository/
│   │   └── test/
│   └── build.gradle
├── settings.gradle
└── build.gradle
```

### 모듈별 역할

#### api 모듈
- **책임**: HTTP 요청/응답 처리
- **포함 요소**:
  - Controller: REST API 엔드포인트
  - DTO: 데이터 전송 객체
  - Exception Handler: 예외 처리
  - Configuration: 웹 설정
- **의존성**: core 모듈에 의존

#### core 모듈
- **책임**: 비즈니스 로직 및 데이터 접근
- **포함 요소**:
  - Domain: 엔티티, VO
  - Service: 비즈니스 로직
  - Repository: 데이터 접근 인터페이스 및 구현
- **의존성**: 다른 모듈에 의존하지 않음 (독립적)

### 모듈 네이밍 컨벤션

1. **소문자와 하이픈 사용**
   ```
   user-api
   user-core
   common-utils
   ```

2. **도메인 접두사 사용 (선택)**
   ```
   user-domain
   order-domain
   product-domain
   ```

3. **명확한 역할 표시**
   ```
   payment-api
   payment-service
   payment-infrastructure
   ```

### 패키지 구조 설계

#### 계층형 (Layer-based)
```
com.soundbar91.project.api/
├── controller/
├── dto/
│   ├── request/
│   └── response/
├── exception/
└── config/
```

#### 도메인형 (Domain-based)
```
com.soundbar91.project.core/
├── user/
│   ├── domain/
│   ├── service/
│   └── repository/
└── order/
    ├── domain/
    ├── service/
    └── repository/
```

---

## 실습 과제

### 과제 1: 기본 모듈 생성
1. `api`, `core` 모듈 디렉토리 생성
2. `settings.gradle`에 모듈 등록
3. 각 모듈의 `build.gradle` 작성

### 과제 2: 코드 이동
1. 기존 Application 클래스를 `api` 모듈로 이동
2. 간단한 Controller 작성
3. core 모듈에 Service 작성
4. api에서 core의 Service 사용

### 과제 3: 빌드 확인
```bash
# 전체 빌드
./gradlew build

# 모듈별 빌드
./gradlew :api:build
./gradlew :core:build

# 실행
./gradlew :api:bootRun
```

---

## 체크리스트

- [ ] 멀티 모듈의 개념과 장단점을 이해했는가?
- [ ] settings.gradle의 역할을 알고 있는가?
- [ ] 루트와 서브 모듈의 build.gradle 차이를 이해했는가?
- [ ] api와 core 모듈이 생성되었는가?
- [ ] api가 core에 의존하도록 설정했는가?
- [ ] 빌드가 정상적으로 수행되는가?
- [ ] 애플리케이션이 정상적으로 실행되는가?

---

## 다음 단계

Phase 1을 완료하면 다음 내용을 학습합니다:
- **Phase 2**: 3-tier 모듈 구조 (infrastructure 추가)
- 모듈 간 의존성 관리 심화
- 공통 모듈 분리

---

## 참고 자료

- [Gradle Multi-Project Builds](https://docs.gradle.org/current/userguide/multi_project_builds.html)
- [Spring Boot Multi-Module Project](https://spring.io/guides/gs/multi-module/)
- [Effective Multi-Module Gradle Projects](https://www.baeldung.com/gradle-multi-module)