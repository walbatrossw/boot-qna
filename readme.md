# 스프링부트를 기반으로한 QnA 게시판 만들기 연습

## 1. slipp 반복주기 1

### 1-1) 로컬 개발환경 세팅
* spring boot 프로젝트 생성
	* web / mustache / dev-tools 선택
* "Hello World" welcome 페이지 작성 src/main/resources/static
* live reload chrome extension 설치 : 변경되는 코드를 실시간으로 새로고침 없이 바로 확인
* IntelliJ의 경우 `application.properties`에 아래와 같이 설정을 해줘야한다.
    ```
    spring.devtools.livereload.enabled=true
    spring.freemarker.cache=false
    ```
    참고URL : http://jojoldu.tistory.com/48
    
### 1-2) 부트스트랩을 활용한 html 페이지 개발
* bootstrap start html 추가
* bootstrap css 라이브러리 추가
* jQuery, javascript 라이브러리 추가
* index.html => navigation bar 추가
* 회원가입페이지 개발

### 1-3) github에 소스코드 추가
* sourcetree에 저장소 추가
* github에 소스코드 추가
* local => 개발 서버 / 실서버
* local => git/svn(버전관리 시스템) => 개발 서버/실서버
* local => github => 개발 서버 / 실서버 

### 1-4) 원격서버(개발 서버 또는 실서버)에 소스코드 배포하기1
* AWS instance 생성하기
* ssh로 서버 접속
	```
	$ ssh -i [public key 저장 경로] ubuntu@[서버 아이피 주소]
	```
* 일반사용자(ubuntu)를 sudo 명령으로 루트 권한 부여
	```
	$ sudo vi /etc/sudoers
	```
	```
	root ALL=(ALL) ALL 
	ubuntu ALL=(ALL) ALL
	```
* 한글 인코딩 설정
	```
	$ sudo locale-gen ko_KR.EUC-KR ko_KR.UTF-8
	$ sudo dpkg-reconfigure locales
	```
* 프로파일 생성 후 인코딩 설정하기
	* 프로파일 생성
	```
	$ vi .bash_profile
	```
	* 인코딩 설정
	```
	LANG="ko_KR.UTF-8" 
	LANGUAGE="ko_KR:ko:en_US:en“
	```
	* 바로 적용시키기
	```
	$ source .bash_profile
	```
### 1-5) 원격서버(개발 서버 또는 실서버)에 소스코드 배포하기2
* 자바 설치
	* wget으로 jdk 다운로드
	```
	wget --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u131-b11/d54c1d3a095b4ff2b6607d096fa80163/jdk-8u131-linux-x64.tar.gz
	```
	* gzip으로 압축풀기
	```
	$ gunzip jdk-8u131-linux-x64.tar.gz
	```
	* tar파일 압축풀기
	```
	tar -xvf jdk-8u121-linux-x64.tar
	```
	* 자바 환경변수 설정
	```
	$ vi .bash_profile
	JAVA_HOME=/home/ubuntu/java
	PATH=$PATH:$JAVA_HOME/bin
	```
* git 설치
	```
	$ sudo apt-get update
	$ sudo apt-get install git
	```
	
* git clone 후 빌드
	* 저장소를 복제
	```
	$ git clone [git 저장소 주소]
	```
	* 저장소를 복제한 곳으로 이동하여 권한 변경 후 빌드
	```
	$ chmod 775 ./mvnw
	$ ./mvnw clean package
	```
* 서버 시작
	* target 디렉토리로 이동하여 서버시작
	```
	$ java -jar [스프링프로젝트명.jar]
	```

- - -
	
## 2. slipp 반복주기 2
* 동적인 HTML 페이지 개발
* Spring MVC 의 Model, View, Controller 기반 개발

### 2-1) Controller 추가 및 mustache 에 인자 전달
* Controller 작성 (com.doubles.qna.web) : WelcomeController
	* welcome() 메서드 추가 : GetMapping, PostMapping 둘다 연습해보기
* welcome 페이지(templates/welcome.html) 작성 후 mustache 에 값 전달 해보기(get 방식으로)
	
### 2-2) 회원가입 페이지 구현
* Controller 작성 (com.doubles.qna.web) : UserController
	* create() 메서드 추가 : PostMapping
* User 클래스 작성
	
### 2-3) 사용자 목록페이지 구현 : inMemory
* Controller 에 list() 메서드 추가 : GetMapping
* 회원 목록 페이지 (template/list.html) 작성 후 mustache 에 값 받아오기(get 방식으로)
* create() 메서드 리턴 값 변경 : list 로 redirect

### 2-4) 원격 서버에 소스코드 배포
* 원격저장소에서 변경된 소스코드 가져오기
    ```
    $ git pull
    ```
* 빌드
    ```
    $ ./mvnw clean package
    ```
* 서버 시작
    * `&`을 뒤에 붙이면 원격서버에 logout 하더라도 서버는 running 상태를 유지한다.
    ```
    $ java -jar [스프링 프로젝트명.jar] &
    ```
* 서버 종료
    * java 로 실행중인 프로그램 조회
    ```
    $ ps -ef | grep java
    ```
    * 원하는 프로그램 종료
    ```
    $ kill -9 [실행중인 프로그램의 id]
    ```
### 2-5) 이전 상태로 원복 후 반복 구현 

- - -

## 3. slipp 반복주기 3
* 데이터베이스에 사용자 데이터 추가
* 개인정보 수정 기능 구현
* 질문하기, 질문목록 기능 구현

### 3-1) QnA HTML 템플릿, H2 데이터베이스 설치, 설정, 관리툴 확인
* QnA 템플릿 추가
    * 참고 github : https://github.com/walbatrossw/web-application-server
* H2 데이터베이스 설치
    * `pom.xml`에 h2 database engine 의존성 추가
    * `<scope>test</scope>`를 `runtime`으로 변경
        ```xml
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>1.4.193</version>
            <scope>runtime</scope>
        </dependency>
        ```
    * `application.properties`에 H2 데이터베이스 설정 추가
        ```
        spring.datasource.driver-class-name=org.h2.Driver
        spring.datasource.url=jdbc:h2:~/boot-qna;AUTO_SERVER=TRUE
        spring.datasource.username=sa
        spring.datasource.password=
        spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
        ```
### 3-2) 자바 객체와 테이블 매핑, 회원가입 기능 구현
* spring-starter-data-jpa 의존성 추가
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    ```
* domain 패키지 추가
    * web 패키지에 있던 User 클래스를 domain 패키지로 이동
    * User 클래스에 annotation 추가
        * `@Entity` : Entity 클래스라는 것을 나타내는 애너테이션 (반드시 필요)
        * `@Table(name="테이블명")` : Entity 클래스에 할당되는 테이블을 지정 (생략가능)
        * `@Id` : primary key 설정 (반드시 필요)
        * `@GenerateValue` : primary key 자동생성
        * `@Column` : 해당 필드에 할당할 칼럼을 지정 (생략가능)
            1.  `name` - 칼럼명 지정 
            2. `length` - 최대 길이를 지정
            3. `nullable` - null 의 허용여부 지정(true, false)

* repository 인터페이스 작성
    * 아래와 같이 작성한다.
        ```java
        public interface UserRepository extends JpaRepository<User, Long> {
        
        }
        ```
* Controller 변경
    * `@Autowired` 애너테이션을 통해 UserRepository 인스턴스를 필드와 연동
        * `@Autowired` : 애플리케이션에 있는 Bean 객체와 연동하기 위한 애너테이션
        ```java
        @Autowired
        private UserRepository userRepository;
        ```
    
    * create() 메서드 : 회원가입
        ```java
        @PostMapping("/create")
        public String create(User user) {
            userRepository.save(user);
            return "redirect:list";
        }
        ```
    * list() 메서드 : 회원목록
        ```java
        @GetMapping("/list")
        public String list(Model model) {
            model.addAttribute("users", userRepository.findAll());
            return "list";
        }
        ```

### 3-3) HTML 정리, URL 정리
* HTML 템플릿 정리
    ```
    └── resources :  
            └── static : 정적 파일
            |       ├── css : css 파일
            |       ├── font :  font 파일
            |       ├── images : image 파일
            |       └── js : js 파일
            └── templates : 동적 파일
                    ├── ex : 기존의 연습한 파일들
                    ├── include : HTML 중복코드 정리 (navigation, header, footer)
                    ├── qna : QnA 게시판 관련 HTML
                    └── user : 회원가입 관련 HTML
    ```
    * html 중복코드를 정리하기 위해 include 폴더에 navigation, header, footer html파일 작성
        * mustache 템플릿의 include 방법
            ```
            {{> /include/header}}
            {{> /include/navigation}}
            {{> /include/footer}}
            ```
* URL 정리
    * UserController 
        * 회원관련 모든 URL 에 `/users`가 추가될 수 있도록`@Controller` 애너테이션 하단에 `@RequestMapping("/users")` 추가
        * 회원가입 처리 후 리스트로 redirect 하기 위해서 `redierct:/users/list` 로 변경

### 3-4) 개인정보 수정 기능 구현
* UserController 에 회원정보 수정화면, 수정처리 메서드 추가
    * 회원정보 수정화면
        ```java
        @GetMapping("/{id}/form")
        public String updateForm(@PathVariable Long id, Model model) {
            User user = userRepository.findOne(id);
            model.addAttribute("user", user);
            return "/user/updateForm";
        }
        ```
    * 회원정보 수정처리
        ```java
        @PutMapping("/{id}")
        public String update(@PathVariable Long id, User updatedUser) {
            User user = userRepository.findOne(id); // 기존의 아이디 정보를 조회
            user.update(updatedUser);   // 아이디의 정보 변경
            userRepository.save(user);  // 변경된 정보를 저장
            return "redirect:/users/list";
        }
        ```
* User 클래스에 update() 메서드 추가 : email, name, password 만 변경
    ```java
    public void update(User updatedUser) {
            this.email = updatedUser.email;
            this.name = updatedUser.name;
            this.password = updatedUser.password;
        }
    ```
* updateForm.html 작성 : 회원정보 수정 화면
    
    ```xml
    <div class="container" id="main">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-default content-main">
                {{#user}}
                <form name="question" method="post" action="/users/{{id}}">
                    <input type="hidden" name="_method" value="put"> <!--@PutMapping 을 사용하기 위한 방법-->
                    <div class="form-group">
                        <label for="userId">사용자 아이디</label>
                        <input class="form-control" id="userId" name="userId" value="{{userId}}" placeholder="User ID" readonly>
                    </div>
                    <div class="form-group">
                        <label for="password">비밀번호</label>
                        <input type="password" class="form-control" id="password" name="password" value="{{password}}" placeholder="Password">
                    </div>
                    <div class="form-group">
                        <label for="password">비밀번호 확인</label>
                        <input type="password" class="form-control" id="password2" name="password" placeholder="Password">
                    </div>
                    <div class="form-group">
                        <label for="name">이름</label>
                        <input class="form-control" id="name" name="name" value="{{name}}" placeholder="Name">
                    </div>
                    <div class="form-group">
                        <label for="email">이메일</label>
                        <input type="email" class="form-control" id="email" name="email" value="{{email}}" placeholder="Email">
                    </div>
                    <button type="submit" class="btn btn-success clearfix pull-right">개인정보 수정</button>
                    <div class="clearfix"/>
                </form>
                {{/user}}
            </div>
        </div>
    </div>
    ```
    
    * html 에서는 get, post 만 사용할 수 있는데 put 을 사용하기 위한 방법
        * `<input type="hidden" name="_method" value="put">`

### 3-5) 원격 서버에 소스코드 배포
* 원격 서버에 배포한 뒤 include 한 파일을 찾지 못하는 문제가 발생
    * 원인 : mustache 템플릿 엔진 문제로 추정
    * 해결 방법
        * jar 파일이 아닌 maven 에서 spring-boot 프로젝트 실행하기
            ```
            $ ./mvnw spring-boot:run &
            ```

- - -

## 4. slipp 반복주기 4
* 로그인 기능 구현, 쿠키와 세션에 대한 이해
* 로그인 사용자에 대한 접근 제한

### 4-1) 로그인 기능 구현
* UserController : 로그인 관련 메서드 추가
    * 로그인 화면 매핑
    ```java
    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }
    ```
    * 로그인 처리
    ```java
    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);

        if ( user == null ) {
            System.out.println("login failure");
            return "redirect:/users/loginForm";
        }

        if ( !password.equals(user.getPassword()) ) {
            System.out.println("login failure");
            return "redirect:/users/loginForm";
        }

        session.setAttribute("user", user);
        System.out.println("login success");
        return "redirect:/";
    }
    ```
* UserRepository : userId 로 하나의 회원을 조회하는 메서드 작성
    ```java
    @Repository
    public interface UserRepository extends JpaRepository<User, Long>{
        // User 타입의 userId로 조회
        User findByUserId(String userId);
    }
    ```

### 4-2) 로그인 상태에 따른 메뉴 처리 및 로그아웃
* 로그인 상태에서 메뉴 처리
    * mustache 템플릿 엔진 if else 문으로 처리하기
        * session 에 user 가 없으면 로그인, 회원가입 메뉴
        * session 에 user 가 있으면 로그아웃, 개인정보수정 메뉴
            ```xml
            <div class="collapse navbar-collapse" id="navbar-collapse2">
                <ul class="nav navbar-nav navbar-right">
                    <li class="active"><a href="/">Posts</a></li>
                    {{^user}}
                    <li><a href="/users/loginForm" role="button">로그인</a></li>
                    <li><a href="/users/form" role="button">회원가입</a></li>
                    {{/user}}
                    {{#user}}
                    <li><a href="/users/logout" role="button">로그아웃</a></li>
                    <li><a href="/users/update" role="button">개인정보수정</a></li>
                    {{/user}}
                </ul>
            </div>
            ```
        참고 URL : https://mustache.github.io/mustache.5.html, Inverted Sections 를 참고
        
    * 로그인시 메뉴 처리가 되지않는 문제 발생
        * `application.properties` 에 mustache 템플릿 session 관련 설정하기
             ```
             spring.mustache.expose-session-attributes=true   
             ```
            참고 URL : https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html, MUSTACHE TEMPLATES 를 참고

* 로그아웃 처리
    * UserController : logout() 메서드 추가, 세션에 담긴 user 를 제거
        ```java
        @GetMapping("/logout")
        public String logout(HttpSession session) {
            session.removeAttribute("user");
            System.out.println("logout success");
            return "redirect:/";
        }
        ```
    
### 4-3) 로그인 사용자에 한해 자신의 정보를 수정하도록 처리
* 로그인 -> 자신의 정보 수정 페이지로 이동
    * `navigation.html` 상단 메뉴바의 개인정보수정 `a` 태그 수정
        ```xml
        <li><a href="/users/{{id}}/form" role="button">개인정보수정</a></li>
        ```
    * 500 에러 발생 : Cannot expose session attribute 'user' because of an existing model object of the same name
        * 원인 : session 의 key 값이 user 이고, model 의 key 값도 user 라서 충돌이 발생
        * 해결 : session 의 key 값을 sessionUser 로 변경
            * navigation.html
                ```xml
                {{^sessionUser}}
                <li><a href="/users/loginForm" role="button">로그인</a></li>
                <li><a href="/users/form" role="button">회원가입</a></li>
                {{/sessionUser}}
                {{#sessionUser}}
                <li><a href="/users/logout" role="button">로그아웃</a></li>
                <li><a href="/users/update" role="button">개인정보수정</a></li>
                {{/sessionUser}}
                ```
            * UserController
                ```java
                @PostMapping("/login")
                public String login(String userId, String password, HttpSession session) {
                    User user = userRepository.findByUserId(userId);
            
                    if ( user == null ) {
                        return "redirect:/users/loginForm";
                    }
            
                    if ( !password.equals(user.getPassword()) ) {
                        System.out.println("login failure");
                        return "redirect:/users/loginForm";
                    }
                    // key값을 sessionUser로 변경
                    session.setAttribute("sessionUser", user);
                    System.out.println("login success");
                    return "redirect:/";
                }
            
                @GetMapping("/logout")
                public String logout(HttpSession session) {
                    // key값을 sessionUser로 변경
                    session.removeAttribute("sessionUser");
                    System.out.println("logout success");
                    return "redirect:/";
                }
                ```
    * 개인정보 수정화면 및 수정처리
        * 본인의 개인정보만 접근이 가능하도록 구현
            ```java
            // 회원 정보수정 화면
            @GetMapping("/{id}/form")
            public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        
                // session 에서 값을 꺼내면 Object 타입으로 리턴하게 되므로 User 타입이 아닌 Object 타입으로 변수선언
                Object tempUser = session.getAttribute("sessionUser");
                // session 이 null 이면 로그인페이지로 리다이렉트
                if ( tempUser == null ) {
                    return "redirect:/users/loginForm";
                }
        
                // session 에 저장된 id와 일치하지 않는 회원정보 수정화면으로 접근 금지
                User sessionUser = (User)tempUser;
                if ( !id.equals(sessionUser.getId()) ) {
                    throw new IllegalStateException("Can't modify other's information");
                }
        
                // session 에 저장된 자신의 정보만 조회할 수 있도록 처리
               User user = userRepository.findOne(sessionUser.getId());
                model.addAttribute("user", user);
                return "/user/updateForm";
            }
            ```
        
            ```java
            // 회원 정보수정 처리
            @PutMapping("/{id}")
            public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
        
                // session 에서 값을 꺼내면 Object 타입으로 리턴하게 되므로 User 타입이 아닌 Object 타입으로 변수선언
                Object tempUser = session.getAttribute("sessionUser");
                // session 이 null 이면 로그인페이지로 리다이렉트
                if ( tempUser == null ) {
                    return "redirect:/users/loginForm";
                }
        
                // session 에 저장된 id와 일치하지 않는 회원정보 수정처리 금지
                User sessionUser = (User)tempUser;
                if ( !id.equals(sessionUser.getId()) ) {
                    throw new IllegalStateException("Can't modify other's information");
                }
        
                User user = userRepository.findOne(sessionUser.getId()); // 기존의 아이디 정보를 조회
                user.update(updatedUser);   // 아이디의 정보 변경
                userRepository.save(user);  // 변경된 정보를 저장
                return "redirect:/users/list";
            }
            ```

### 4-4) 중복 제거 및 읽기 좋은 코드를 위한 리팩토링
* UserController 중복 코드 제거
    * session 처리를 담당하는 Util 클래스 작성 : HttpSessionUtils (com.doubles.qna.web)
        ```java
        public class HttpSessionUtils {
        
            // session 의 key 값을 상수로 변경
            public static final String USER_SESSION_KEY = "sessionUser";
        
            // session 에 로그인된 유저의 존재 여부 판별하는 메서드
            public static boolean isLoginUser(HttpSession session) {
                // session 에서 값을 꺼내면 Object 타입으로 리턴하게 되므로 User 타입이 아닌 Object 타입으로 변수선언
                Object sessionUser = session.getAttribute(USER_SESSION_KEY);
                // session 값이 null 이면 false 리턴
                if ( sessionUser == null ) {
                    return false;
                }
          
                return true;
            }
            
            // session 에 저장된 값을 가져오는 메서드
            public static User getUserFromSession(HttpSession session) {
                if ( !isLoginUser(session) ) {
                    return null;
                }
        
                return (User)session.getAttribute(USER_SESSION_KEY);
            }
        }
        ```
    * UserController 수정
        * 로그인 처리
            ```java
            @PostMapping("/login")
            public String login(String userId, String password, HttpSession session) {
        
                User user = userRepository.findByUserId(userId);
        
                if ( user == null ) {
                    System.out.println("login failure");
                    return "redirect:/users/loginForm";
                }
        
                // 로그인 시 password 값과 조회하려는 password 값 비교 <-- 변경
                if ( !user.matchPassword(password) ) {
                    System.out.println("login failure");
                    return "redirect:/users/loginForm";
                }
                
                session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user); // <-- 변경
                System.out.println("login success");
                return "redirect:/";
            }
            ```
        * 로그아웃 처리
            ```java
            @GetMapping("/logout")
            public String logout(HttpSession session) {
                // 세션에 담기 user 를 제거 <-- 변경
                session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
                System.out.println("logout success");
                return "redirect:/";
            }
            ```
            
        * 회원 정보수정 화면
            ```java
            @GetMapping("/{id}/form")
            public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        
                // session 값이 null 이면 로그인 페이지로 redirect <-- 변경
                if ( !HttpSessionUtils.isLoginUser(session) ) {
                    return "redirect:/users/loginForm";
                }
        
                // session 에 저장된 값을 sessionUser 에 복사 <-- 변경
                User sessionUser = HttpSessionUtils.getUserFromSession(session);
                // session 에 저장된 id 값과 조회하려는 id값 비교 <-- 변경
                if ( !sessionUser.matchId(id) ) {
                    throw new IllegalStateException("Can't modify other's information");
                }
        
                // session 에 저장된 자신의 정보만 조회할 수 있도록 처리
                User user = userRepository.findOne(id);
                model.addAttribute("user", user);
                return "/user/updateForm";
            }
            ```
            
        * 회원 정보수정 처리
            ```java
            @PutMapping("/{id}")
            public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
        
                // session 값이 null 이면 로그인 페이지로 redirect
                if ( !HttpSessionUtils.isLoginUser(session) ) {
                    return "redirect:/users/loginForm";
                }
        
                // session 에 저장된 값을 sessionUser 에 복사  <-- 변경
                User sessionUser = HttpSessionUtils.getUserFromSession(session);
                // session 에 저장된 id 값과 조회하려는 id값 비교 <-- 변경
                if ( !sessionUser.matchId(id) ) {
                    throw new IllegalStateException("Can't modify other's information");
                }
        
                User user = userRepository.findOne(id); // 기존의 아이디 정보를 조회
                user.update(updatedUser);   // 아이디의 정보 변경
                userRepository.save(user);  // 변경된 정보를 저장
                return "redirect:/users/list";
            }  
            ```
    
    
* User 클래스 수정 : id, password get() 메서드 제거하고 아래의 메서드로 변경
    * User 가 가지고 있는 변수 값을 get() 메서드로 값을 꺼내서 Controller 에서 비교하는 것보다는 
    객체 클래스에서 직접 값을 비교하고 결과값을 리턴하도록 만드는 것이 좋다. 
    객체지향적으로 코드를 작성하려면 private 으로 캡슐화 되어 있는 변수의 값을 외부에 노출시키는 것은 바람직 하지 않기 때문이다.
    
    * 아이디 일치확인 메서드
        ```java
        public boolean matchId(Long newId) {
            if ( newId == null ) {
                return false;
            }
            return newId.equals(id);
        }
        ```
    * 비밀번호 일치확인 매서드
        ```java
        public boolean matchPassword(String newPassword) {
            if ( newPassword == null ) {
                return false;
            }
            return newPassword.equals(password);
        }
        ```


* JPA Console 창에 SQL Query 보기 설정
    * `application.properties` 에 아래와 같이 설정
        ```
        spring.jpa.show-sql=true
        spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
        spring.jpa.properties.hibernate.format_sql=true
        ```
### 4-5) 질문하기, 질문 목록 기능 구현
* 질문하기
    * QuestionController 작성 : (com.doubles.qna.web)
        ```java
        @Controller
        @RequestMapping("/questions")
        public class QuestionController {
        
            @Autowired
            private QuestionRepository questionRepository;
        
            // 게시글 작성 화면
            @GetMapping("/form")
            public String question(HttpSession session) {
                // 로그인되어 있지 않으면 로그인 페이지로
                if ( !HttpSessionUtils.isLoginUser(session)) {
                    return "redirect:/users/loginForm";
                }
                return "/qna/form";
            }
        
            // 게시글 작성 처리
            @PostMapping
            public String create(String title, String contents, HttpSession session) {
                // 로그인되어 있지 않으면 로그인 페이지로
                if ( !HttpSessionUtils.isLoginUser(session) ) {
                    return "redirect:/users/loginForm";
                }
                // 현재 로그인되어 있는 회원의 정보를 sessionUser 에 복사
                User sessionUser = HttpSessionUtils.getUserFromSession(session);
        
                Question newQuestion = new Question(sessionUser.getUserId(), title, contents);
                questionRepository.save(newQuestion);
                return "redirect:/";
            }
        }
        ```
    * Question 클래스 작성 : (com.doubles.qna.domain)
        ```java
        @Entity
        public class Question {
            @Id
            @GeneratedValue
            private Long id;
        
            private String writer;
            private String title;
            private String contents;
        
            public Question() {
            }
        
            public Question(String writer, String title, String contents) {
                this.writer = writer;
                this.title = title;
                this.contents = contents;
            }
        }
        ```
    * QuestionRepository 인터페이스 작성 : (com.doubles.qna.domain)
        ```java
        @Repository
        public interface QuestionRepository extends JpaRepository<Question, Long>{
        
        }
        ```
    
    * form.html : 질문하기 작성 화면 (resources/template/qna)
        ```xml
        <div class="container" id="main">
            <div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
                <div class="panel panel-default content-main">
                    <form name="question" method="post" action="/questions">
                        <div class="form-group">
                            <label for="title">제목</label>
                            <input type="text" class="form-control" id="title" name="title" placeholder="제목"/>
                        </div>
                        <div class="form-group">
                            <label for="contents">내용</label>
                            <textarea name="contents" id="contents" rows="5" class="form-control"></textarea>
                        </div>
                        <button type="submit" class="btn btn-success clearfix pull-right">질문하기</button>
                        <div class="clearfix"/>
                    </form>
                </div>
            </div>
        </div>
        ```

* 질문 목록
    * HomeController : home() 메서드 수정
        ```java
        @Controller
        public class HomeController {
        
            @Autowired
            private QuestionRepository questionRepository;
        
            @GetMapping("/")
            public String home(Model model) {
                model.addAttribute("questions", questionRepository.findAll());
                return "index";
            }
        
        }
        ```
    * index.html 수정 : 글작성 시간 및 댓글 갯수는 추후 구현 예정
        ```xml
        {{#questions}}
        <li>
            <div class="wrap">
                <div class="main">
                    <strong class="subject">
                        <a href="../static/qna/show.html">{{title}}</a>
                    </strong>
                    <div class="auth-info">
                        <i class="icon-add-comment"></i>
                        <span class="time">2016-01-15 18:47</span>
                        <a href="../static/user/profile.html" class="author">{{contents}}</a>
                    </div>
                    <div class="reply" title="댓글">
                        <i class="icon-reply"></i>
                        <span class="point">8</span>
                    </div>
                </div>
            </div>
        </li>
        {{/questions}}
        ```

### 4-5) 원격 서버에 소스코드 배포

* war 파일로 배포
    * `pom.xml` 설정
        * war packaging 으로 변경
            ```xml
            <groupId>com.doubles.qna</groupId>
            <artifactId>boot-qna</artifactId>
            <version>0.0.1-SNAPSHOT</version>
            <packaging>war</packaging>
            ```
        * 내장형 tomcat 을 사용하지 않고 별도의 tomcat 을 사용하기 위해 tomcat 의존성 추가
            ```xml
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-tomcat</artifactId>
                <scope>provided</scope>
            </dependency>
            ```
    * `BootQnaWebInitializer` 클래스 작성 : 외장 tomcat 을 사용하려면 초기화를 위한 클래스를 따로 작성해줘야 한다.
        ```java
        public class BootQnaWebInitializer extends SpringBootServletInitializer {
            @Override
            protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
                return builder.sources(BootQnaApplication.class);
            }
        }
        ```
        
        * `SpringBootServletInitializer` 클래스를 상속
        * `configure()` 메서드를 오버라이딩
            * 기존에 초기화 작업를 담당했던 `BootQnaApplication` 클래스를 등록 설정하여 초기화 작업을 수행할 수 있도록 해준다.
        
* 배포 서버에 tomcat 설치
    * http://tomcat.apache.org/download-80.cgi 로 접속
    * tar.gz 파일을 링크주소 복사 ex) http://apache.tt.co.kr/tomcat/tomcat-8/v8.5.15/bin/apache-tomcat-8.5.15.tar.gz
    * 배포 서버로 tomcat 다운로드
        ```
        $ wget http://apache.tt.co.kr/tomcat/tomcat-8/v8.5.15/bin/apache-tomcat-8.5.15.tar.gz
        ```
    * 다운로드 받은 tomcat 압축풀기
        ```
        $ tar -xvf apache-tomcat-8.5.15.tar.gz
        ```
    * tomcat 폴더의 실제 물리적 경로를 tomcat 심볼릭 링크로 설정
        ```
        $  ln -s apache-tomcat-8.5.15 tomcat
        ```
    * tomcat 서버 구동하기 : tomcat/bin 으로 이동하여 startup.sh 파일을 실행
        ```
        $ ./startup.sh
        ```
    * tomcat 서버 정지하기 : tomcat/bin 으로 이동하여 shutdown.sh 파일을 실행    
        
    * tomcat 서버로 접속하기 : tomcat 서버의 경우 default 로 8080포트로 되어 있다. 주소:8080 으로 접속하여 고양이 그림이 있는 웹페이지가 뜨면 성공
    
    * github 저장소에서 지금까지 구현한 프로젝트를 pull 하고 build 하기
        ```
        $ git pull
        $ ./mvnw clean package
        ```
    * tomcat 서버에 springboot 프로젝트 배포하기
        * tomcat 이 설치된 디렉토리에서 `webapps/ROOT` 에 기존의 내용을 삭제 
            ```
            $ rm -rf ROOT/
            ```
        * 빌드한 프로젝트를 ROOT 디렉토리로 이동한 뒤 ROOT 디렉토리로 이름을 변경
            ```
            $ mv [해당프로젝트명] ~/tomcat/webapps
            $ mv [해당프로젝트명]/ ROOT
            ```
    * tomcat 서버 구동 
    * tomcat 서버 로그 확인하기 : `tomcat/logs` 로 이동하여 `catalina.out` 실행
        ```
        $ tail -500f catalina.out    
        ```
> 프로젝트 기능 구현을 한 뒤에 항상 실 서버나 테스트 서버에 배포 작업을 하자. 그렇지 않고 프로젝트를 완성한 뒤 배포하는 시점에 문제가 발생하게 되면 문제를 해결하는데 상당한 시간과 노력이 필요할 수 있다.     


### war 배포 설정 이후 로컬에서 오류발생
* SpringBoot 프로젝트를 로컬에서 실행시 오류가 IntelliJ 에서 계속 발생, STS 에서는 이상없음.
    * 원인 : `pom.xml` 에서 tomcat 설정 scope 에서 문제가 발생
    * 해결 : `pom.xml` 에서 scope 설정 제거, default 설정(compiled)
    * 참고 URL : http://krespo.net/166
    
- - -

## 5. slipp 반복주기 5
* 객체 간의 관계 설정(@OneToMany, @ManyToOne 등)

### 5-1) 회원과 질문 간의 관계 매핑 및 리팩토링
* 회원과 질문 간의 관계
    * 회원 입장에서의 관계 : 한명의 회원과 다수의 질문이 존재할 수 있다. OneToMany
        * User 가 Question 에 대한 목록을 가질수 있도록 관계를 맺을 수 있다.
    * 질문 입장에서의 관계 : 다수의 질문과 한명의 회원이 존재할 수 있다. ManyToOne
        * Question 이 User 객체를 가질 수 있도록 관계를 맺을 수 있다.

* Question 클래스 리팩토링 : 회원과 질문간의 관계 설정 및 생성일 추가
    * Question 객체에서 User 객체를 필드 변수로 선언하고 `@ManyToOne` 애너태이션을 통해 N:1의 관계로 설정하게 되면 User 객체의 기본키가 Question 객체의 foreign key 로 설정된다.
    * `@JoinColumn` : 제약조건의 이름을 지정
    ```java
    @Entity
    public class Question {
        @Id
        @GeneratedValue
        private Long id;
          
        @ManyToOne
        @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
        private User writer;
        // private String writer;
    
        private String title;
    
        private String contents;
        
        // 날짜와 시간을 나타내기 위해 java8 부터 추가된 타입
        private LocalDateTime createDate;
    
        public Question() {
        }
    
        public Question(User writer, String title, String contents) {
            this.writer = writer;
            this.title = title;
            this.contents = contents;
            // 현재시간 생성 할당 : 현재 시간이 생성되었지만 알수 없는 글자들로 표현되기 때문에 formatting 을 해줘야한다.
            this.createDate =  LocalDateTime.now();
        }
        // 시간 포맷변경 메서드
        public String getFormattedCreateDate() {
            if (createDate == null) {
                return "";
            }
            return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        }
    
    }
    ```
* index.html 에서 질문이 생성된 날짜와 시간 출력
    * mustache 템플릿은 기본적으로 get() 메서드를 지원하기 때문에 Question 클래스에서 작성한 `getFormattedCreateDate()` 메서드에서 get 을 제거하고 대문자 F를 소문자 f로 변경하여 {{}}에 넣어주면 날짜가 화면에 출력된다.
    ```xml
    <span class="time">{{formattedCreateDate}}</span>
    ```
    
### 5-2) 질문 상세보기 기능 구현
* QuestionController : 질문 상세 보기 매핑
    ```java
    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findOne(id));
        return "/qna/show";
    }
    ```
* 질문 상세 보기 페이지 작성 : template/qna/show.html
    ```xml
    {{#question}}
    <div class="panel panel-default">
        <header class="qna-header">
            <h2 class="qna-title">{{title}}</h2>
        </header>
        <div class="content-main">
            <article class="article">
                <div class="article-header">
                    <div class="article-header-thumb">
                        <img src="https://graph.facebook.com/v2.3/100000059371774/picture" class="article-author-thumb" alt="">
                    </div>
                    <div class="article-header-text">
                        <a href="/users/92/kimmunsu" class="article-author-name">{{writer.userId}}</a>
                        <a href="/questions/413" class="article-header-time" title="퍼머링크">
                            {{formattedCreateDate}}
                            <i class="icon-link"></i>
                        </a>
                    </div>
                </div>
                <div class="article-doc">
                    {{contents}}
                </div>
                <div class="article-util">
                    <ul class="article-util-list">
                        <li>
                            <a class="link-modify-article" href="/questions/{{id}}/form">수정</a>
                        </li>
                        <li>
                            <form class="form-delete" action="/questions/{{id}}" method="POST">
                                <input type="hidden" name="_method" value="DELETE">
                                <button class="link-delete-article" type="submit">삭제</button>
                            </form>
                        </li>
                        <li>
                            <a class="link-modify-article" href="/">목록</a>
                        </li>
                    </ul>
                </div>
            </article>
        </div>
    </div>
    {{/question}}
    ```

### 5-3) 질문 수정, 삭제 기능 구현
* 표준 HTML 에서는 POST, GET 요청만 처리 할 수 있기 때문에 PUT, DELETE 요청을 처리하기 위해서는 아래와 같은 코드를 작성해주어야 한다.
    ```xml
    <input type="hidden" name="_method" value="put">
    <input type="hidden" name="_method" value="delete">
    ```
* 질문 수정 기능 
    * QuestionController : 질문 수정 화면, 수정처리 매핑
        ```java
        // 질문 수정 화면
        @GetMapping("/{id}/form")
        public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
            model.addAttribute("question", questionRepository.findOne(id));
            return "/qna/updateForm";
        }
  
        // 질문 수정 처리
        @PutMapping("/{id}")
        public String update(@PathVariable Long id, String title, String contents, HttpSession session) {
            Question question = questionRepository.findOne(id);
            question.update(title, contents);
            questionRepository.save(question);
            return String.format("redirect:/questions/%d", id);
        }
        ```
        
    * show.html : 질문 수정 처리 페이지로 이동할 수 있도록 URL 세팅
        ```java
        <a class="link-modify-article" href="/questions/{{id}}/form">수정</a>
        ```
    
    * updateForm.html : 질문 수정 처리
        ```xml
        <div class="container" id="main">
            <div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
                <div class="panel panel-default content-main">
                    {{#question}}
                    <form name="question" method="post" action="/questions/{{id}}">
                        <input type="hidden" name="_method" value="put">
                        <div class="form-group">
                            <label for="title">제목</label>
                            <input type="text" class="form-control" id="title" name="title" value="{{title}}" placeholder="제목"/>
                        </div>
                        <div class="form-group">
                            <label for="contents">내용</label>
                            <textarea name="contents" id="contents" rows="5" class="form-control">{{contents}}</textarea>
                        </div>
                        <button type="submit" class="btn btn-success clearfix pull-right">질문 수정</button>
                        <div class="clearfix"/>
                    </form>
                    {{/question}}
                </div>
            </div>
        </div>
        ```
    
* 질문 삭제 기능
    * QuestionController : 삭제처리 매핑
        ```java
        // 질문 삭제 처리
        @DeleteMapping("/{id}")
        public String delete(@PathVariable Long id) {
            questionRepository.delete(id);
            return "redirect:/";
        }
        ```
    
    * show.html : 질문 삭제 처리할 수 있도록 URL 세팅
        ```xml
        <li>
            <form class="form-delete" action="/questions/{{id}}" method="POST">
                <input type="hidden" name="_method" value="DELETE">
                <button class="link-delete-article" type="submit">삭제</button>
            </form>
        </li>
        ```

### 5-4) 수정/삭제 기능에 대한 보안 처리 및 LocalDateTime 설정    
* 수정/삭제 기능 보안처리
    * QuestionController 클래스 보안처리
        * `updateForm()` 메서드 : 수정화면 매핑
            ```java
            @GetMapping("/{id}/form")
            public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
                // 로그인 여부 체크
                if ( !HttpSessionUtils.isLoginUser(session) ) {
                    return "redirect:/users/loginForm";
                }
                // 로그인한 유저와 질문작성자 비교
                User loginUser = HttpSessionUtils.getUserFromSession(session);
                Question question = questionRepository.findOne(id);
                if ( !question.isSameWriter(loginUser) ) {
                    return "redirect:/users/loginForm";
                }
        
                model.addAttribute("question", question);
                return "/qna/updateForm";
            }
            ```
        * `udpdate()` 메서드 : 수정처리 매핑
            ```java
            @PutMapping("/{id}")
            public String update(@PathVariable Long id, String title, String contents, HttpSession session) {
                // 로그인 여부 체크
                if ( !HttpSessionUtils.isLoginUser(session) ) {
                    return "redirect:/users/loginForm";
                }
                // 로그인한 유저와 질문작성자 비교
                User loginUser = HttpSessionUtils.getUserFromSession(session);
                Question question = questionRepository.findOne(id);
                if ( !question.isSameWriter(loginUser) ) {
                    return "redirect:/users/loginForm";
                }
        
                question.update(title, contents);
                questionRepository.save(question);
                return String.format("redirect:/questions/%d", id);
            }
            ```
            * `String.format()` 메서드 
                * 수정처리가 완료되고 나서 수정된 질문페이지로 이동하기 위해서는 id 값이 필요하다.
                * 리턴값이 String 인데 특정 변수의 값을 String 에 포함시키기 위해서는 아래와 같이 작성하면 된다.
                    ```java
                    String.format("redirect:/questions/%d", id);
                    ```
            
        * `delete()` 메서드 : 삭제처리 매핑
            ```java
            @DeleteMapping("/{id}")
            public String delete(@PathVariable Long id, HttpSession session) {
                // 로그인 여부 체크
                if ( !HttpSessionUtils.isLoginUser(session) ) {
                    return "redirect:/users/loginForm";
                }
                // 로그인한 유저와 질문작성자 비교
                User loginUser = HttpSessionUtils.getUserFromSession(session);
                Question question = questionRepository.findOne(id);
                if ( !question.isSameWriter(loginUser) ) {
                    return "redirect:/users/loginForm";
                }
        
                questionRepository.delete(id);
                return "redirect:/";
            }
            ```
    * Question 클래스
        * 질문 작성자와 로그인유저가 같은지 비교하는 메서드 
            ```java
            public boolean isSameWriter(User loginUser) {
                    return this.writer.equals(loginUser);
                }
            ```
        * `equals()` 메서드 오버라이딩
            * 위의 코드를 작성하고 테스트 해보면 계속 false 가 리턴 되어 로그인페이지로 리다이렉트가 된다. 
            * 그 이유는 `equals()` 메서드는 참조변수에 저장된 주소 값이 같은지만를 판단하는 때문이다.
            * `equals()` 메서드로 writer 인스턴스와 loginUser 인스턴스가 가지고 있는 id 값을 비교하는 방법은 `equals()` 메서드를 오버라이딩하여 객체에 저장된 내용을 비교하도록 변경해줘야 한다. 
                ```java
                // equals() 메서드 오버라이딩
                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (o == null || getClass() != o.getClass()) return false;
            
                    User user = (User) o;
            
                    return id != null ? id.equals(user.id) : user.id == null;
                }
                // hashCode() 메서드 오버라이딩
                @Override
                public int hashCode() {
                    return id != null ? id.hashCode() : 0;
                }
                ```
* LocalDateTime 설정
    * DateTimeConverter 클래스 작성 : LocalDateTime 타입을 Timestamp 타입으로 변환 시켜주는 역할 수행
        ```java
        @Converter(autoApply = true)
        public class DateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
        
            @Override
            public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
                return localDateTime != null ? Timestamp.valueOf(localDateTime) : null;
            }
        
            @Override
            public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
                return timestamp != null ? timestamp.toLocalDateTime() : null;
            }
        }
        ```

### 5-5) 답변 추가 및 답변 목록 기능 구현
* Answer 클래스 작성
    ```java
    @Entity
    public class Answer {
    
        @Id
        @GeneratedValue
        private Long id;
    
        @ManyToOne
        @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
        private User writer;
    
        @Lob // 255자가 넘는 String 타입일 경우 @Lob 애노테이션 추가
        private String contents;
    
        private LocalDateTime createDate;
    
        @ManyToOne
        @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
        private Question question;
    
        // 기본 생성자
        public Answer() {
    
        }
    
        // 생성자
        public Answer(User writer, Question question, String contents) {
            this.writer = writer;
            this.contents = contents;
            this.question = question;
            this.createDate =  LocalDateTime.now();
        }
    
        // 시간 포맷변경 메서드
        public String getFormattedCreateDate() {
            if (createDate == null) {
                return "";
            }
            return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        }
    
        // equals() 메서드 오버라이딩
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
    
            Answer answer = (Answer) o;
    
            return id != null ? id.equals(answer.id) : answer.id == null;
        }
    
        // hashCode() 메서드 오버라이딩
        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }
    
        // toString() 메서드
        @Override
        public String toString() {
            return "Answer{" +
                    "id=" + id +
                    ", writer=" + writer +
                    ", contents='" + contents + '\'' +
                    ", createDate=" + createDate +
                    '}';
        }
    }
    ```
* AnswerRepository 클래스 작성
    ```java
    @Repository
    public interface AnswerRepository extends JpaRepository<Answer, Long>{
    
    }
    ```
* AnswerController 클래스 작성
    ```java
    @Controller
    @RequestMapping("/questions/{questionId}/answers")
    public class AnswerController {
    
        @Autowired
        private AnswerRepository answerRepository;
    
        @Autowired
        private QuestionRepository questionRepository;
    
        // 답변 하기
        @PostMapping
        public  String create(@PathVariable Long questionId, String contents, HttpSession session) {
            // 로그인되어 있지 않으면 로그인 페이지로
            if ( !HttpSessionUtils.isLoginUser(session) ) {
                return "/users/loginForm";
            }
            // 로그인된 회원의 정보 가져오기
            User loginUser = HttpSessionUtils.getUserFromSession(session);
            Question question = questionRepository.findOne(questionId);
            
            Answer answer = new Answer(loginUser, question, contents);
            answerRepository.save(answer);
            return  String.format("redirect:/questions/%d", questionId);
        }
    
    }
    ```

* Question 클래스 : 질문과 답변 간의 관계 매핑 - 1:N, 하나의 질문에 다수의 답변이 존재가 가능하다.
    * `@OneToMany` : 1:N 관계
    * `mapppedBy` : 관계를 설정할 해당 필드명을 입력
    * `@OrderBy` : 정렬, 속성(ASC, DESC) 
    ```java
    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")  // 오른차순 정렬
    private List<Answer> answers;
    ```

* show.html : 답변 목록 및 입력 코드 작성 
    ```xml
    {{#answers}}
    <article class="article" id="answer-1405">
        <div class="article-header">
            <div class="article-header-thumb">
                <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
            </div>
            <div class="article-header-text">
                <a href="/users/1/자바지기" class="article-author-name">{{writer.userId}}</a>
                <a href="#answer-1434" class="article-header-time" title="퍼머링크">
                    {{formattedCreateDate}}
                </a>
            </div>
        </div>
        <div class="article-doc comment-doc">
            {{contents}}
        </div>
        <div class="article-util">
            <ul class="article-util-list">
                <li>
                    <a class="link-modify-article" href="" >수정</a>
                </li>
                <li>
                    <form class="delete-answer-form" action="" method="POST">
                        <input type="hidden" name="_method" value="DELETE">
                        <button type="submit" class="delete-answer-button">삭제</button>
                    </form>
                </li>
            </ul>
        </div>
    </article>
    {{/answers}}
    <form class="submit-write" method="post" action="/questions/{{id}}/answers">
        <div class="form-group" style="padding:14px;">
            <textarea class="form-control" placeholder="Update your status" name="contents"></textarea>
        </div>
        <input type="submit" class="btn btn-success pull-right" value="답변하기">
        <div class="clearfix"/>
    </form>
    ```

### 5-6) QuestionController 중복 제거 리팩토링
* 중복코드 제거 방법 1 : Exception 을 이용
    * QuestionController 
        * 권한 체크 메서드 작성 : 수정화면, 수정처리, 삭제시 중복되는 코드를 메서드화하고, 예외처리
            ```java
            // 권한체크 메서드
            private boolean hasPermission(HttpSession session, Question question) {
                // 로그인 여부 체크
                if ( !HttpSessionUtils.isLoginUser(session) ) {
                    throw new IllegalStateException("로그인이 필요합니다.");
                }
                // 본인여부 체크
                User loginUser = HttpSessionUtils.getUserFromSession(session);
                if ( !question.isSameWriter(loginUser) ) {
                    throw new IllegalStateException("자신이 쓴 글만 수정, 삭제가 가능합니다.");
                }
                return true;
            }
            ```
        * 수정 화면
            ```java
            @GetMapping("/{id}/form")
            public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
                try {
                    // 현재 질문 조회
                    Question question = questionRepository.findOne(id);
                    // 권한 체크
                    hasPermission(session, question);
                    // 질문 수정화면으로 이동
                    model.addAttribute("question", question);
                    return "/qna/updateForm";
                } catch (IllegalStateException e) {
                    // 에러 메시지 전달
                    model.addAttribute("errorMsg", e.getMessage());
                    // 로그인 페이지로 이동
                    return "/user/login";
                }
            }
            ```
        * 수정 처리
            ```java
            @PutMapping("/{id}")
            public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
                try {
                    // 현재 질문 조회
                    Question question = questionRepository.findOne(id);
                    // 권한 체크
                    hasPermission(session, question);
                    // 업데이트 처리
                    question.update(title, contents);
                    questionRepository.save(question);
                    return String.format("redirect:/questions/%d", id);
                } catch (IllegalStateException e) {
                    // 에러 메시지 전달
                    model.addAttribute("errorMsg", e.getMessage());
                    // 로그인 페이지로 이동
                    return "/user/login";
                }
            }
            ```
        * 삭제 처리
            ```java
            @DeleteMapping("/{id}")
            public String delete(@PathVariable Long id, HttpSession session, Model model) {
                try {
                    // 현재 질문 조회
                    Question question = questionRepository.findOne(id);
                    // 권한 체크
                    hasPermission(session, question);
                    // 삭제 처리
                    questionRepository.delete(id);
                    return "redirect:/";
                } catch (IllegalStateException e) {
                    // 에러 메시지 전달
                    model.addAttribute("errorMsg", e.getMessage());
                    // 로그인 페이지로 이동
                    return "/user/login";
                }
            }
            ```
    * login.html : 로그인 `form` 태그 상단에 에러메시지 출력할 `div`태그 작성
        ```xml
        {{#errorMsg}}
        <div class="alert alert-danger" role="alert">{{this}}</div>
        {{/errorMsg}}
        ```
        
* 중복코드 제거방법 2 Result 클래스를 작성
    * Result 클래스
        ```java
        public class Result {
            // 권한 유효성 판단
            private boolean valid;
            // 에러메시지
            private String errorMsg;
        
            private Result(boolean valid, String errorMsg) {
                this.valid = valid;
                this.errorMsg = errorMsg;
            }
        
            public boolean isValid() {
                return valid;
            }
        
            public String getErrorMsg() {
                return errorMsg;
            }
        
            public static Result ok() {
                return new Result(true, null);
            }
        
            public static Result fail(String errorMsg) {
                return new Result(false, errorMsg);
            }
        }
        ```

    * QuestionController 클래스
        * 권한 체크 메서드
            ```java
            private Result valid(HttpSession session, Question question) {
                // 로그인 여부 체크
                if ( !HttpSessionUtils.isLoginUser(session) ) {
                    return Result.fail("로그인이 필요합니다.");
                }
                // 본인여부 체크
                User loginUser = HttpSessionUtils.getUserFromSession(session);
                if ( !question.isSameWriter(loginUser) ) {
                    return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
                }
                return Result.ok();
            }
            ```
        
        * 질문 수정 화면
            ```java
            @GetMapping("/{id}/form")
            public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
                // 현재 질문 조회
                Question question = questionRepository.findOne(id);
                Result result = valid(session, question);
                if ( !result.isValid() ) {
                    // 에러 메시지 저장
                    model.addAttribute("errorMsg", result.getErrorMsg());
                    // 로그인 페이지로 이동
                    return "/user/login";
                }
                // 질문 수정화면으로 이동
                model.addAttribute("question", question);
                return "/qna/updateForm";
            }
            ```
        * 질문 수정 처리
            ```java
            @PutMapping("/{id}")
            public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
                // 현재 질문 조회
                Question question = questionRepository.findOne(id);
                Result result = valid(session, question);
                if ( !result.isValid() ) {
                    // 에러 메시지 저장
                    model.addAttribute("errorMsg", result.getErrorMsg());
                    // 로그인 페이지로 이동
                    return "/user/login";
                }
                // 업데이트 처리
                question.update(title, contents);
                questionRepository.save(question);
                return String.format("redirect:/questions/%d", id);
            }
            ```   
        * 질문 삭제 처리
            ```java
            @DeleteMapping("/{id}")
            public String delete(@PathVariable Long id, HttpSession session, Model model) {
                // 현재 질문 조회
                Question question = questionRepository.findOne(id);
                Result result = valid(session, question);
                if ( !result.isValid() ) {
                    // 에러 메시지 저장
                    model.addAttribute("errorMsg", result.getErrorMsg());
                    // 로그인 페이지로 이동
                    return "/user/login";
                }
                // 삭제 처리
                questionRepository.delete(id);
                return "redirect:/";
            }
            ```
### 5-7) 원격 서버에 소스코드 배포

## 6. slipp 반복주기 6
* JSON API 및 AJAX 를 활용해 답변 추가/삭제 구현

### 6-1 AJAX 를 활용해 답변 추가 기능 구현
* 답변관련 AnswerController => ApiAnswerController 변경
    * `@Controller` => `@RestController` 애너테이션 변경
    * `@RequstMapping` URL 변경 : `("/questions/{questionId}/answers")` => `("/api/questions/{questionId}/answers")`
    * create() 메서드 리턴타입 변경 : `String` => `Answer`
    ```java
    @RestController
    @RequestMapping("/api/questions/{questionId}/answers")
    public class ApiAnswerController {
    
        @Autowired
        private AnswerRepository answerRepository;
    
        @Autowired
        private QuestionRepository questionRepository;
    
        // 답변 하기
        @PostMapping
        public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
            // 로그인되어 있지 않으면 로그인 페이지로
            if ( !HttpSessionUtils.isLoginUser(session) ) {
                return null;
            }
            // 로그인된 회원의 정보 가져오기
            User loginUser = HttpSessionUtils.getUserFromSession(session);
            Question question = questionRepository.findOne(questionId);
            Answer answer = new Answer(loginUser, question, contents);
            return answerRepository.save(answer);
        }
    }
    ```
* Json 사용을 위해 각 클래스의 필드마다`@JsonProperty` 애노테이션 추가
    * Answer 클래스 변경
        ```java
        @Id
        @GeneratedValue
        @JsonProperty // <- 애너테이션 추가
        private Long id;
    
        @ManyToOne
        @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
        @JsonProperty // <- 애너테이션 추가
        private User writer;
    
        @Lob 
        @JsonProperty // <- 애너테이션 추가
        private String contents;
    
        private LocalDateTime createDate;
    
        @ManyToOne
        @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
        @JsonProperty // <- 애너테이션 추가
        private Question question;
        ```
        
    * Question 클래스 변경
        ```java
        @Id
        @GeneratedValue
        @JsonProperty // <- 애너테이션 추가
        private Long id;
    
        @ManyToOne
        @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
        @JsonProperty // <- 애너테이션 추가
        private User writer;
        
    
        @JsonProperty // <- 애너테이션 추가
        private String title;
    
        @Lob
        @JsonProperty // <- 애너테이션 추가
        private String contents;
    
        
        private LocalDateTime createDate;
    
        @OneToMany(mappedBy = "question")
        @OrderBy("id DESC")  // 내림차순 정렬
        private List<Answer> answers;
        ```
        
    * User 클래스 변경
        ```java
        @Id
        @GeneratedValue
        @JsonProperty // <- 애너테이션 추가
        private Long id;    
    
        @Column(nullable = false, length = 20, unique = true)
        @JsonProperty // <- 애너테이션 추가
        private String userId;
    
        @JsonIgnore // <- 애너테이션 추가
        private String password;
    
        @JsonProperty // <- 애너테이션 추가
        private String name;
    
        @JsonProperty // <- 애너테이션 추가
        private String email;
        ```
        
* script.js 변경
    ```js
    // 답변하기 버튼 클릭 이벤트 발생시 addAnswer 함수 호출
    $(".answer-write input[type=submit]").click(addAnswer);
    
    // 답변하기 추가 AJAX 요청 처리를 위한 함수
    function addAnswer(e) {
        console.log("clicked");
        e.preventDefault(); // submit 기본 효과 방지
        var queryString = $(".answer-write").serialize(); // JSON 타입으로 객체 직렬화
        console.log("queryString : "+queryString);
        var url = $(".answer-write").attr("action"); // 요청 URL 저장
        console.log("url : "+url);
        // AJAX POST 요청 처리
        $.ajax({
            type : 'post',
            url : url,
            data : queryString,
            dataType : 'json',
            error : onError,
            success : onSuccess
        });
    }
    
    // AJAX 요청 처리 실패시
    function onError(status) {
        console.log("error" + status);
    }
    
    // AJAX 요청 처리 성공시
    function onSuccess (data, status) {
        console.log(data);
        var answerTemplate = $("#answerTemplate").html();
        var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.id);
        $(".qna-comment-slipp-articles").prepend(template); //  변수 template 에 담긴 답변을 append
        $(".answer-write textarea").val("");    // textarea 에 남아있는 내용 지우기
    }
    
    // 답변 템플릿 : replace() 함수와 정규표현식을 통해 변환
    String.prototype.format = function() {
        var args = arguments;
        return this.replace(/{(\d+)}/g, function(match, number) {
            return typeof args[number] != 'undefined'
                ? args[number]
                : match
                ;
        });
    };
    ```
* show.html 변경 : `form` 태그의 action 속성 url 변경
    ```xml
    <form class="answer-write" method="post" action="/api/questions/{{id}}/answers">
        <div class="form-group" style="padding:14px;">
            <textarea class="form-control" placeholder="Update your status" name="contents"></textarea>
        </div>
        <input type="submit" class="btn btn-success pull-right" value="답변하기">
        <div class="clearfix"/>
    </form>
    ```
### 6-2 AJAX 를 활용해 답변 삭제 기능 구현
* ApiAnswerController : 답변 삭제 매핑 메서드 작성
    ```java
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        // 로그인 상태가 아닐경우 로그인 유도
        if ( !HttpSessionUtils.isLoginUser(session) ) {
            return Result.fail("로그인해야 합니다.");
        }
        // 로그인 유저와 답변작성자가 일치하는지 비교
        Answer answer = answerRepository.findOne(id);
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        // 불일치할 경우 경고문 알림
        if ( !answer.isSameWriter(loginUser) ) {
            return Result.fail("자신의 글만 삭제할 수 있습니다.");
        }
        // 일치할 경우 답변 삭제
        answerRepository.delete(id);
        return Result.ok();
    }
    ```
* Answer 클래스 `isSameWriter()` 메서드 작성 : 로그인 유저와 답변 작성자 일치하는지 비교
    ```java
    public boolean isSameWriter(User loginUser) {
        return loginUser.equals(this.writer);
    }
    ``` 
* show.html
    * 답변 영역의 삭제 버튼 url 설정
        ```xml
        <div class="article-util">
            <ul class="article-util-list">
                <li>
                    <a class="link-modify-article" href="">수정</a>
                </li>
                <li>
                    <a class="link-delete-article" href="/api/questions/{{question.id}}/answers/{{id}}">삭제</a>
                </li>
            </ul>
        </div>
        ```
    * 답변 추가 템플릿 영역의 삭제 버튼 url 설정
        ```xml
        <div class="article-util">
            <ul class="article-util-list">
                <li>
                    <a class="link-modify-article" href="">수정</a>
                </li>
                <li>
                    <a class="link-delete-article" href="/api/questions/{3}/answers/{4}">삭제</a>
                </li>
            </ul>
        </div>
        ```
        
* script.js
    ```js
    // 답변삭제 클릭 이벤트 발생시 deleteAnswer 함수 호출
    $(".qna-comment").on('click', '.link-delete-article', deleteAnswer);
    // 답변삭제 AJAX 요청 처리를 위한 함수
    function deleteAnswer(e) {
        e.preventDefault(); // a태그 기본효과 방지
        var deleteBtn = $(this);
        var url = deleteBtn.attr("href");
        $.ajax({
            type : 'delete',
            url : url,
            dataType : 'json',
            error : function (xhr, status) {
                console.log("error");
            },
            success : function (data, status) {
                console.log(data);
                if (data.valid) {
                    deleteBtn.closest("article").remove();
                } else {
                    alert(data.errorMsg);
                }
            }
        });
    }
    ```

### 6-3 질문 목록에 답변 수 보여주기 기능 추가
### 6-4 중복 제거 및 리팩토링
### 6-5 JSON API 추가 및 테스트
### 6-6 쉘 스크립트를 활용해 배포 자동화


























