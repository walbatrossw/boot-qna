package com.doubles.qna.domain;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;    // 기본키

    @Column(nullable = false, length = 20, unique = true)
    private String userId;      // 회원 아이디

    private String password;    // 비밀번호
    private String name;        // 회원 이름
    private String email;       // 회원 이메일

    // Getter Setter 메서드

    // 아이디 일치확인 메서드
    public boolean matchId(Long newId) {
        if ( newId == null ) {
            return false;
        }
        return newId.equals(id);
    }

    //  아이디 일치확인 메서드가 존재하므로 getId 메서드 필요 X
//    public Long getId() {
//      return id;
//    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // 비밀번호 일치확인 매서드
    public boolean matchPassword(String newPassword) {
        if ( newPassword == null ) {
            return false;
        }
        return newPassword.equals(password);
    }

    // 비밀번호 일치확인 메서드가 존재하므로 getPassword 메서드 필요 X
//    public String getPassword() {
//        return password;
//    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Update() 메서드
    public void update(User updatedUser) {
        this.email = updatedUser.email;
        this.name = updatedUser.name;
        this.password = updatedUser.password;
    }

    // toString() 메서드
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
