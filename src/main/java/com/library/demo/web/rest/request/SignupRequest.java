package com.library.demo.web.rest.request;

import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;

public class SignupRequest {
    @NotBlank
    private String username;
    private String email;
    @NotBlank
    private String password;
    private Set<String> role;
    public SignupRequest(String username, String email, String password, Set<String> role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignupRequest that = (SignupRequest) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password, role);
    }

    public Set<String> getRole() {
        return role;
    }
    public void setRole(Set<String> role){
        this.role =  role;
    }
}
