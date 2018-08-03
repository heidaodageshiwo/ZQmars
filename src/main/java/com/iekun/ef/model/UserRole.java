package com.iekun.ef.model;

public class UserRole {
    private Long id;

    /**
    * 角色ID
    */
    private Long roleId;

    /**
    * 用户ID
    */
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}