## :pushpin: The Ai Fitness
**Problem : 기존 project에서는 "일명 악취가 난다."라는 프로젝트였기에 Refactoring을 도입하였습니다.**

**What : 무엇이 문제였는가?**
- 실시간 분석을 해주는 페이지에서는 심각한 속도 문제
- 중복된 코드


**How : 기존에 문제점을 수정한 뒤 Spring의 많은 API를 다 활용했다고는 할 수 없기에 JA 와 Securoty ,Junit 도입**
<br>

## 1. What skill changed ?
#### `Back-end`    
  - Java 11 / Spring Data Jpa / Spring Security / Junit5 / 
#### `Front-end`
  - Thymleaf
<br>

## 2. What changed?

### 2-1 중복된 코드
![image](https://user-images.githubusercontent.com/48014869/192233788-1e34a887-3e97-407d-84e6-402f2ba28fde.png)

### **css , javascript에서 중복된 코드가 무분별하게 있었습니다.**

**✅ Solution**
- **Thymleaf**를 통해서 css 중복성을 제거하고, **Footer, Main, Header**구간으로 나눠서 설계했습니다. **더 나아가 Javascript 또한 중복성을 제거했습니다.**

**⭕ How**
  - 무분별한 코드가 난무하고 있기에 기본적인 Html Css를 분리
  - Thymleaf 문법을 통한 [Layout 구성](https://github.com/dkwktm45/project/blob/373a5044dce3378e7d5f3a6ade19485cf550e77b/src/main/resources/templates/main.html#L68-L76) 
  - Javascript 또한 [중복성을 제거하고자](https://github.com/dkwktm45/project/blob/373a5044dce3378e7d5f3a6ade19485cf550e77b/src/main/resources/templates/main.html#L81-L105), 노력했습니다.

#### :pushpin: **What I learn** 
**전반적인 중복성을 제거함으로써, 전반적인 코드의 3000 line을 줄일 수 있었고, 해당 작업을 통해 Clean 코드의 중요성을 인지하고 있습니다.**
<br>

<br>

### 2-2 Jpa

#### **기존에는 Xml형태의 쿼리를 작성한 개발 형태였습니다. 이는 쿼리를 직접 설계해야 했기에 생산성 및 유지보수의 문제가 있기에 이를 개선하기 위한 JPA 도입**


**✅ What**
  - JPA 도입시 N+1이슈가 민감한 부분이기에 전반적인 연관관계 설계를 [Lazy Loading](https://github.com/dkwktm45/project/blob/373a5044dce3378e7d5f3a6ade19485cf550e77b/src/main/java/com/example/project_2th/entity/User.java#L44-L48)으로 맞춰줬습니다.
  - 또한 필요한 부분만을 나가도록 하기 위한 [@EntityGraph](https://github.com/dkwktm45/project/blob/373a5044dce3378e7d5f3a6ade19485cf550e77b/src/main/java/com/example/project_2th/repository/UserRepository.java#L24-L25) 설정
  - 기본적으로 가져오기 어려운 코드는 [@Query](https://github.com/dkwktm45/project/blob/373a5044dce3378e7d5f3a6ade19485cf550e77b/src/main/java/com/example/project_2th/repository/ExinfoRepository.java#L12-L29)를 사용했습니다.

#### :pushpin: **What I learn** 
**해당 JPA를 통해서 기본적인 내용을 숙지할 수 있었으며, 가독성 및 유지보수를 겸비한 인재로 거듭날 수 있었습니다.**

<br>

### 2-3 Junit

#### **매번 내가 설계한 코드를 테스트 하기 번거로움을 해결하기 위한 Junit 도입**


**✅ What**
  - TDD원칙인 단위 테스트 원칙을 준수하여 ,[Controller Test](https://github.com/dkwktm45/project/blob/373a5044dce3378e7d5f3a6ade19485cf550e77b/src/test/java/com/example/project_2th/controller/MainControllerTest.java#L48) , [Service Test](https://github.com/dkwktm45/project/blob/373a5044dce3378e7d5f3a6ade19485cf550e77b/src/test/java/com/example/project_2th/service/ExerciesServiceTest.java#L23) , [Jpa Test](https://github.com/dkwktm45/project/blob/373a5044dce3378e7d5f3a6ade19485cf550e77b/src/test/java/com/example/project_2th/repository/UserRepositoryTest.java#L28) 나눠서 설계를 하였습니다.
  - 내가 원하는 결과를 얻기 위한 Mockito를 사용한 다음 [given , when , then](https://github.com/dkwktm45/project/blob/373a5044dce3378e7d5f3a6ade19485cf550e77b/src/test/java/com/example/project_2th/controller/RestControllerApiTest.java#L80-L107) 규칙을 통한 Test
  - 중복으로 사용되는 구간 [한 그룹으로](https://github.com/dkwktm45/project/blob/373a5044dce3378e7d5f3a6ade19485cf550e77b/src/test/java/com/example/project_2th/service/UserServiceTest.java#L113-L163) 묶어서 Test를 진행하였습니다.

#### :pushpin: **What I learn** 
 **Junit을 통해서 작은 단위의 테스트까지 진행해볼 수 있었으며, 40개의 Test를 나의 의도의 맞게 설계를 할 수 있었습니다.**

<br>

### 2-4 Security

#### **해당 서비스는 유저 페이지와 관리자 페이지가 나뉘어 있기에 Security를 도입**

**✅ What**
  - 회원가입을 통해서 권한이 부여됩니다. 로그인 시 검증을 위한 [provider](https://github.com/dkwktm45/project/blob/373a5044dce3378e7d5f3a6ade19485cf550e77b/src/main/java/com/example/project_2th/security/provider/CustomAuthenticationProvider.java) 및 [handler](https://github.com/dkwktm45/project/blob/373a5044dce3378e7d5f3a6ade19485cf550e77b/src/main/java/com/example/project_2th/security/handler/LoginSuccessHandler.java)를 구현했습니다.


#### :pushpin: **What I learn** 
 **해당 로그인을 통해서 Security를 사용한 간단한 로그인은 구현이 가능합니다.**

<br>

## 3. Troubleshooting
**Jpa를 양방향으로 인한 [무한 참조 문제가](https://github.com/dkwktm45/project/blob/373a5044dce3378e7d5f3a6ade19485cf550e77b/src/main/java/com/example/project_2th/entity/User.java#L44) 발생 이를 직렬화 방향 설정을 통해 해결**

**repository를 통해 객체를 가져온다면 해당 객체는 즉시 로딩으로 인한 N+1 이슈 발생, 기본적인 Lazy 설정을 바꾼 뒤 필요할 때만 발생하도록 했습니다.**

**Junit4 @BeforeClass 주석과 Junit5 @WebMvcTest 을 동시에 사용하고 있었기에 버전이 맞지 않아 Bean 주입 문제 직면, 이를 성공한 class(@WebMvcTest)와 비교해서 4,5를 차이를 알고 수정했습니다.***

**Junit Test 중 @WithMockUser 만으로 해결되지 않는 사태를 직면하였고, 이를 저의 의도의 맞는 [Custom](https://github.com/dkwktm45/project/blob/de869f2a0d4e9f7dfff21de8aa40f220e2ae78f8/src/test/java/com/example/project_2th/security/mock/WithMockCustomUser.java) 주석을 생성하여 이를 해결했습니다.**

**Refactoring은 아무래도 남이 한 코드를 봐야 했기에 전반적인 HTML, CSS, Javascript를 수정하기 위한 코드 파악이 필요했습니다.**

<br>

## 4. 느낀 점
기존에 있던 두 번째 프로젝트를 끝내고, 내놓기에는 부족하다고 생각했기에 Refactoring을 통해 조금이나마 미흡한 부분을 해소할 수 있었던 프로젝트입니다.

준비 기간으로 강의를 마친 뒤, 프로젝트에서 적용을 하려 했지만, 이는 하늘과 땅 차이라는 것을 알게 됐습니다.

기존 서비스를 개발할 때는 내가 생각해낸 코드와 구글링한 코드를 사용하기 때문에
훨씬 많은 문제와 마주해야 했습니다.

그리고 그 문제들을 해결하는 과정에서 제대로 이해하지 못하고 있던 개념들을 발견할 수 있었고,
구글링 솔루션으로는 단순히 문제 해결이 아닌 나 자신에게 Why? 을 많이 던졌습니다.
또한 이러한 과정에 개발의 참 재미를 알게 됐습니다.

지속적으로 실제 서비스를 개발하면서 다양한 문제를 직면하겠지만,
끊임없이 학습하고, 이를 위한 더 나은 솔루션이 있는지 고민한다면,
결국 어떠한 서비스라도 솔루션을 제시해 나갈 자신이 있습니다.
