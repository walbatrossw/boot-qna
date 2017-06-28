package com.doubles.qna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class User extends AbstractEntity {

    @Column(nullable = false, length = 20, unique = true)
    @JsonProperty
    private String userId;      // 회원 아이디

    @JsonIgnore
    private String password;    // 비밀번호

    @JsonProperty
    private String name;        // 회원 이름

    @JsonProperty
    private String email;       // 회원 이메일

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // 아이디 일치확인 메서드
    public boolean matchId(Long newId) {
        if ( newId == null ) {
            return false;
        }
        return newId.equals(getId());
    }

    // 비밀번호 일치확인 매서드
    public boolean matchPassword(String newPassword) {
        if ( newPassword == null ) {
            return false;
        }
        return newPassword.equals(password);
    }

    // Update() 메서드
    public void update(User updatedUser) {
        this.email = updatedUser.email;
        this.name = updatedUser.name;
        this.password = updatedUser.password;
    }

    @Override
    public String toString() {
        return "User{"+ super.toString() +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
