# 멀티 모듈 아키텍처 학습 커리큘럼

## 학습 목표
Spring Boot 프로젝트를 멀티 모듈 구조로 설계하고 구현하는 방법을 학습합니다. 각 모듈의 책임을 명확히 분리하고, 의존성을 관리하며, 유지보수가 용이한 아키텍처를 구축하는 능력을 기릅니다.

---

## 📚 커리큘럼 구성

### Phase 1: 멀티 모듈 기초 (기본 개념 및 설정)

#### 1.1 멀티 모듈 아키텍처 이해
- **학습 내용**
  - 멀티 모듈이란 무엇인가?
  - 모놀리식 vs 멀티 모듈 비교
  - 멀티 모듈의 장단점
  - 실무에서의 활용 사례

- **실습**
  - 현재 프로젝트 구조 분석
  - 모듈 분리 필요성 파악

#### 1.2 Gradle 멀티 프로젝트 설정
- **학습 내용**
  - `settings.gradle`의 역할과 모듈 등록
  - 루트 프로젝트와 서브 모듈의 관계
  - `build.gradle` 계층 구조
  - `allprojects`, `subprojects` 설정

- **실습**
  - 첫 번째 서브 모듈 생성
  - `settings.gradle`에 모듈 추가
  - 모듈별 `build.gradle` 구성

#### 1.3 기본 모듈 구조 구성
- **학습 내용**
  - 2-tier 모듈 구조 (api, core)
  - 모듈 네이밍 컨벤션
  - 패키지 구조 설계

- **실습**
  ```
  multi-module-study/
  ├── api/            # REST API 계층
  └── core/           # 비즈니스 로직 계층
  ```

---

### Phase 2: 계층형 멀티 모듈 구조 (Layered Architecture)

#### 2.1 3-tier 모듈 구조 구성
- **학습 내용**
  - Presentation Layer (api)
  - Business Logic Layer (domain/service)
  - Data Access Layer (infrastructure)
  - 계층 간 의존성 방향

- **실습**
  ```
  multi-module-study/
  ├── api/                    # Controller, DTO
  ├── domain/                 # Entity, Service, Repository Interface
  └── infrastructure/         # Repository 구현, 외부 연동
  ```

#### 2.2 모듈 간 의존성 관리
- **학습 내용**
  - `implementation` vs `api` (Gradle)
  - 순환 의존성 방지
  - 의존성 방향 규칙 (상위 → 하위)
  - transitive dependency 관리

- **실습**
  - 각 모듈의 `build.gradle`에 의존성 설정
  - 의존성 그래프 분석
  - 순환 의존성 발생 시 해결 방법 실습

#### 2.3 공통 모듈 분리
- **학습 내용**
  - common 모듈의 역할
  - 공통 유틸리티, 예외, 상수 관리
  - 공통 모듈의 의존성 최소화

- **실습**
  ```
  multi-module-study/
  ├── common/                 # 공통 유틸리티, 예외, 상수
  ├── api/
  ├── domain/
  └── infrastructure/
  ```

---

### Phase 3: 도메인 중심 멀티 모듈 (Domain-Driven Design)

#### 3.1 도메인별 모듈 분리
- **학습 내용**
  - DDD의 Bounded Context 개념
  - 도메인별 독립 모듈 구성
  - 도메인 간 통신 방법

- **실습**
  ```
  multi-module-study/
  ├── common/
  ├── user-domain/            # 사용자 도메인
  │   ├── user-api/
  │   ├── user-service/
  │   └── user-infrastructure/
  ├── order-domain/           # 주문 도메인
  │   ├── order-api/
  │   ├── order-service/
  │   └── order-infrastructure/
  └── product-domain/         # 상품 도메인
      ├── product-api/
      ├── product-service/
      └── product-infrastructure/
  ```

#### 3.2 모듈 간 통신 패턴
- **학습 내용**
  - 직접 의존 vs 이벤트 기반 통신
  - 내부 API 설계
  - 도메인 이벤트 활용

- **실습**
  - 도메인 간 직접 호출 구현
  - Spring Event를 활용한 느슨한 결합 구현
  - 통신 패턴별 장단점 비교

---

### Phase 4: 고급 멀티 모듈 패턴

#### 4.1 헥사고날 아키텍처 (Ports & Adapters)
- **학습 내용**
  - 헥사고날 아키텍처 개념
  - Port와 Adapter 패턴
  - 내부 도메인과 외부 어댑터 분리

- **실습**
  ```
  user-domain/
  ├── user-application/       # 유스케이스, Port 인터페이스
  ├── user-domain/           # 순수 도메인 로직
  ├── user-adapter-in-web/   # REST API Adapter
  ├── user-adapter-out-persistence/ # DB Adapter
  └── user-adapter-out-messaging/   # 메시징 Adapter
  ```

#### 4.2 클린 아키텍처 적용
- **학습 내용**
  - 클린 아키텍처의 의존성 규칙
  - Entity, Use Case, Interface Adapter, Framework 계층
  - 의존성 역전 원칙 (DIP)

- **실습**
  - 클린 아키텍처 구조로 모듈 재구성
  - 의존성 방향 검증
  - 테스트 용이성 확인

#### 4.3 모듈별 독립 빌드 및 배포
- **학습 내용**
  - 모듈별 버전 관리
  - 독립 실행 가능한 모듈 구성 (bootJar vs jar)
  - 멀티 모듈 환경에서의 통합 테스트

- **실습**
  - api 모듈을 실행 가능한 jar로 빌드
  - 공통 모듈을 라이브러리로 배포
  - 전체 빌드 및 개별 모듈 빌드 실습

---

### Phase 5: 프로덕션 수준 멀티 모듈 구성

#### 5.1 설정 관리 모듈
- **학습 내용**
  - 환경별 설정 관리 (dev, stage, prod)
  - 외부 설정 주입
  - Spring Cloud Config 연동 (선택)

- **실습**
  ```
  multi-module-study/
  ├── config/                # 설정 관리 모듈
  │   ├── application-dev.yml
  │   ├── application-prod.yml
  │   └── logback-spring.xml
  └── ...
  ```

#### 5.2 보안 및 인증 모듈
- **학습 내용**
  - 공통 보안 설정 모듈화
  - JWT 인증 모듈 분리
  - Spring Security 설정 공유

- **실습**
  - `security-common` 모듈 생성
  - JWT 토큰 처리 로직 모듈화
  - 각 도메인에서 보안 모듈 활용

#### 5.3 테스트 전략
- **학습 내용**
  - 모듈별 단위 테스트
  - 통합 테스트 모듈 구성
  - 테스트 공통 설정 및 픽스처 관리

- **실습**
  ```
  multi-module-study/
  ├── test-common/           # 테스트 공통 유틸리티
  ├── integration-test/      # 통합 테스트 모듈
  └── ...
  ```

#### 5.4 모니터링 및 로깅
- **학습 내용**
  - 공통 로깅 설정 모듈화
  - 분산 추적 (Distributed Tracing)
  - 모듈별 메트릭 수집

- **실습**
  - `monitoring-common` 모듈 생성
  - Logback 설정 공유
  - Actuator 엔드포인트 구성

---

### Phase 6: 실전 프로젝트

#### 6.1 실전 프로젝트: E-Commerce 시스템
- **목표**: 학습한 내용을 종합하여 실제 서비스 구조 설계

- **구조**
  ```
  ecommerce-system/
  ├── common/
  │   ├── common-domain/
  │   └── common-utils/
  ├── user-module/
  │   ├── user-api/
  │   ├── user-application/
  │   ├── user-domain/
  │   └── user-infrastructure/
  ├── product-module/
  │   ├── product-api/
  │   ├── product-application/
  │   ├── product-domain/
  │   └── product-infrastructure/
  ├── order-module/
  │   ├── order-api/
  │   ├── order-application/
  │   ├── order-domain/
  │   └── order-infrastructure/
  ├── payment-module/
  │   ├── payment-api/
  │   ├── payment-application/
  │   ├── payment-domain/
  │   └── payment-infrastructure/
  ├── notification-module/
  ├── security-module/
  ├── config-module/
  └── api-gateway/           # 통합 API Gateway
  ```

#### 6.2 성능 최적화
- **학습 내용**
  - 모듈 간 통신 최적화
  - 캐싱 전략 (모듈별 캐시 관리)
  - 데이터베이스 커넥션 풀 관리

#### 6.3 배포 자동화
- **학습 내용**
  - Docker 멀티 스테이지 빌드
  - 모듈별 컨테이너 구성
  - CI/CD 파이프라인 구축

---

## 🎯 학습 로드맵

### 초급 (1-2주)
- Phase 1: 멀티 모듈 기초
- Phase 2: 계층형 멀티 모듈 구조

### 중급 (2-3주)
- Phase 3: 도메인 중심 멀티 모듈
- Phase 4: 고급 멀티 모듈 패턴

### 고급 (3-4주)
- Phase 5: 프로덕션 수준 멀티 모듈 구성
- Phase 6: 실전 프로젝트

---

## 📖 추천 학습 자료

### 도서
- "도메인 주도 설계" - Eric Evans
- "클린 아키텍처" - Robert C. Martin
- "만들면서 배우는 클린 아키텍처" - Tom Hombergs

### 온라인 자료
- Spring Boot 공식 문서: Multi-Module Projects
- Gradle 공식 문서: Multi-Project Builds
- Baeldung: Multi-Module Project with Spring Boot

### 실습 레퍼런스
- Netflix OSS 프로젝트
- Spring Petclinic Microservices
- jhipster 프로젝트 구조

---

## ✅ 체크리스트

각 Phase별로 다음 항목을 체크하며 학습을 진행하세요:

- [ ] 개념을 정확히 이해했는가?
- [ ] 직접 코드를 작성해 보았는가?
- [ ] 의존성 방향이 올바른가?
- [ ] 순환 의존성은 없는가?
- [ ] 테스트는 작성했는가?
- [ ] 빌드가 정상적으로 되는가?
- [ ] 각 모듈의 책임이 명확한가?

---

## 🔧 실습 환경

- **Java**: 17
- **Spring Boot**: 4.0.1
- **Gradle**: 9.2.1
- **Database**: H2 (학습용), PostgreSQL (프로덕션)
- **IDE**: IntelliJ IDEA

---

## 💡 학습 팁

1. **작은 단위로 시작하기**: 한 번에 많은 모듈을 만들지 말고, 2-3개 모듈로 시작
2. **의존성 그래프 그리기**: 모듈 간 의존성을 시각화하여 관리
3. **리팩토링 반복**: 구조가 마음에 들지 않으면 과감히 재구성
4. **실제 예제 분석**: 오픈소스 프로젝트의 멀티 모듈 구조 분석
5. **문서화**: 각 모듈의 역할과 책임을 README에 명확히 작성

---

## 다음 단계

1. Phase 1부터 순차적으로 학습 시작
2. 각 Phase의 실습 코드를 git commit으로 관리
3. 학습 일지 작성 (LEARNING_LOG.md)
4. 어려운 부분은 반복 학습 및 추가 자료 검색