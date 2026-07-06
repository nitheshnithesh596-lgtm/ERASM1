package com.erasm.dto;

public class UserDTO {

    private Long userId;
    private String username;
    private String email;
    private String password;
    private Long roleId;

   
    public UserDTO() {
    }

    
    public UserDTO(Long userId, String username, String email, String password, Long roleId) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }

    

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}