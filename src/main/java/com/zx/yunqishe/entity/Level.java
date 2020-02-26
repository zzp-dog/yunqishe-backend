package com.zx.yunqishe.entity;

import javax.persistence.*;

public class Level {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    /**
     * 等级
     */
    @Column(name = "LEVEL")
    private Byte level;

    /**
     * 所需经验
     */
    private Integer experience;

    /**
     * 等级名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 获取自增id
     *
     * @return id - 自增id
     */
    public Byte getId() {
        return id;
    }

    /**
     * 设置自增id
     *
     * @param id 自增id
     */
    public void setId(Byte id) {
        this.id = id;
    }

    /**
     * 获取等级
     *
     * @return LEVEL - 等级
     */
    public Byte getLevel() {
        return level;
    }

    /**
     * 设置等级
     *
     * @param level 等级
     */
    public void setLevel(Byte level) {
        this.level = level;
    }

    /**
     * 获取所需经验
     *
     * @return experience - 所需经验
     */
    public Integer getExperience() {
        return experience;
    }

    /**
     * 设置所需经验
     *
     * @param experience 所需经验
     */
    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    /**
     * 获取等级名称
     *
     * @return NAME - 等级名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置等级名称
     *
     * @param name 等级名称
     */
    public void setName(String name) {
        this.name = name;
    }
}