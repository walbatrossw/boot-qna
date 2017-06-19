# 스프링부트를 기반으로한 QnA 게시판 만들기 연습

## slipp 반복주기 1

### 1-1 로컬 개발환경 세팅
* spring boot 프로젝트 생성
	* web / mustache / dev-tools 선택
* "Hello World" welcome 페이지 작성 src/main/resources/static
* live reload chrome extension 설치 : 변경되는 코드를 실시간으로 새로고침없이 바로 확인

### 1-2 부트스트랩을 활용한 html 페이지 개발
* bootstrap start html 추가
* bootstrap css 라이브러리 추가
* jQuery, javascript 라이브러리 추가
* index.html => navigation bar 추가
* 회원가입페이지 개발

### 1-3 github에 소스코드 추가
* sourcetree에 저장소 추가
* github에 소스코드 추가
* local => 개발 서버 / 실서버
* local => git/svn(버전관리 시스템) => 개발 서버/실서버
* local => github => 개발 서버 / 실서버 

### 1-4 원격서버(개발 서버 또는 실서버)에 소스코드 배포하기1
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
### 1-5 원격서버(개발 서버 또는 실서버)에 소스코드 배포하기2
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
	$ git clone git [저장소 주소]
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