package com.jetbrains.test.boot4.server.security;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("app_user")
public class User {
    
    @Id
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    
    public User() {
    }
    
    public User(Long id, String username, String password, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
