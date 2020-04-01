package com.zx.yunqishe.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "doc_article")
public class DocArticle {
    /**
     * 自增节id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 所属章的id
     */
    private Integer pid;

    /**
     * 节名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 第几节
     */
    private Integer section;

    /**
     * 摘要
     */
    private String introduce;

    /**
     * 浏览次数
     */
    @Column(name = "view")
    private Integer view;

    /**
     * 1-可见，0-不可见
     */
    private Byte visible;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 内容
     */
    private String text;

    /**
     * 获取自增节id
     *
     * @return id - 自增节id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增节id
     *
     * @param id 自增节id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取所属章的id
     *
     * @return pid - 所属章的id
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置所属章的id
     *
     * @param pid 所属章的id
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取节名称
     *
     * @return NAME - 节名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置节名称
     *
     * @param name 节名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取第几节
     *
     * @return section - 第几节
     */
    public Integer getSection() {
        return section;
    }

    /**
     * 设置第几节
     *
     * @param section 第几节
     */
    public void setSection(Integer section) {
        this.section = section;
    }

    /**
     * 获取摘要
     *
     * @return introduce - 摘要
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * 设置摘要
     *
     * @param introduce 摘要
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    /**
     * 获取浏览次数
     *
     * @return view - 浏览次数
     */
    public Integer getView() {
        return view;
    }

    /**
     * 设置浏览次数
     *
     * @param view 浏览次数
     */
    public void setView(Integer view) {
        this.view = view;
    }

    /**
     * 获取1-可见，0-不可见
     *
     * @return visible - 1-可见，0-不可见
     */
    public Byte getVisible() {
        return visible;
    }

    /**
     * 设置1-可见，0-不可见
     *
     * @param visible 1-可见，0-不可见
     */
    public void setVisible(Byte visible) {
        this.visible = visible;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取内容
     *
     * @return text - 内容
     */
    public String getText() {
        return text;
    }

    /**
     * 设置内容
     *
     * @param text 内容
     */
    public void setText(String text) {
        this.text = text;
    }
}