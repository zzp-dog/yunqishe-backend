package com.zx.yunqishe.entity;

import javax.persistence.*;
import java.util.List;

@Table(name = "roel_power")
public class RoelPower {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 权限id
     */
    @Column(name = "power_id")
    private Integer powerId;

    @Transient
    private List<Power> powers;

    public List<Power> getPowers() {
        return powers;
    }

    public void setPowers(List<Power> powers) {
        this.powers = powers;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Transient
    private List<Role> roles;
    /**
     * 获取自增id
     *
     * @return id - 自增id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增id
     *
     * @param id 自增id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取角色id
     *
     * @return role_id - 角色id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色id
     *
     * @param roleId 角色id
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取权限id
     *
     * @return power_id - 权限id
     */
    public Integer getPowerId() {
        return powerId;
    }

    /**
     * 设置权限id
     *
     * @param powerId 权限id
     */
    public void setPowerId(Integer powerId) {
        this.powerId = powerId;
    }
}