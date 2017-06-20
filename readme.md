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
    
### 4-3) 로그인 사용자에 한해 자신의 정보를 수정하도록 수정
### 4-4) 질문하기, 질문 목록 기능 구현
### 4-5) 원격 서버에 소스코드 배포
