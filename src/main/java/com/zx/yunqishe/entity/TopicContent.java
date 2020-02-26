package com.zx.yunqishe.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "topic_content")
public class TopicContent {
    /**
     * 自增节id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 作者id
     */
    private Integer uid;

    /**
     * 所属话题的id[冗余，子内容不需要]
     */
    private Integer tid;

    /**
     * 所属话题父内容的id，-1表示顶级
     */
    private Integer pid;

    /**
     * 名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 摘要
     */
    private String introduce;

    /**
     * 浏览次数
     */
    @Column(name = "view_count")
    private Integer viewCount;

    /**
     * 1-精华（pid=-1）/神评, 0-否
     */
    private Byte good;

    /**
     * 1-置顶，0-不置顶
     */
    private Byte top;

    /**
     * 1-被采纳(针对话题为问题)，0-不采纳（冗余）
     */
    private Byte adopt;

    /**
     * 1-可改，0-不可改
     */
    @Column(name = "MODIFY")
    private Byte modify;

    /**
     * 1-可见，0-不可见
     */
    private Byte visible;

    /**
     * 1-收费，0-不收费（只限话题内容，评论冗余该字段）
     */
    private Byte charge;

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
     * 获取作者id
     *
     * @return uid - 作者id
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 设置作者id
     *
     * @param uid 作者id
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 获取所属话题的id[冗余，子内容不需要]
     *
     * @return tid - 所属话题的id[冗余，子内容不需要]
     */
    public Integer getTid() {
        return tid;
    }

    /**
     * 设置所属话题的id[冗余，子内容不需要]
     *
     * @param tid 所属话题的id[冗余，子内容不需要]
     */
    public void setTid(Integer tid) {
        this.tid = tid;
    }

    /**
     * 获取所属话题父内容的id，-1表示顶级
     *
     * @return pid - 所属话题父内容的id，-1表示顶级
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置所属话题父内容的id，-1表示顶级
     *
     * @param pid 所属话题父内容的id，-1表示顶级
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取名称
     *
     * @return NAME - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
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
     * @return view_count - 浏览次数
     */
    public Integer getViewCount() {
        return viewCount;
    }

    /**
     * 设置浏览次数
     *
     * @param viewCount 浏览次数
     */
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * 获取1-精华（pid=-1）/神评, 0-否
     *
     * @return good - 1-精华（pid=-1）/神评, 0-否
     */
    public Byte getGood() {
        return good;
    }

    /**
     * 设置1-精华（pid=-1）/神评, 0-否
     *
     * @param good 1-精华（pid=-1）/神评, 0-否
     */
    public void setGood(Byte good) {
        this.good = good;
    }

    /**
     * 获取1-置顶，0-不置顶
     *
     * @return top - 1-置顶，0-不置顶
     */
    public Byte getTop() {
        return top;
    }

    /**
     * 设置1-置顶，0-不置顶
     *
     * @param top 1-置顶，0-不置顶
     */
    public void setTop(Byte top) {
        this.top = top;
    }

    /**
     * 获取1-被采纳(针对话题为问题)，0-不采纳（冗余）
     *
     * @return adopt - 1-被采纳(针对话题为问题)，0-不采纳（冗余）
     */
    public Byte getAdopt() {
        return adopt;
    }

    /**
     * 设置1-被采纳(针对话题为问题)，0-不采纳（冗余）
     *
     * @param adopt 1-被采纳(针对话题为问题)，0-不采纳（冗余）
     */
    public void setAdopt(Byte adopt) {
        this.adopt = adopt;
    }

    /**
     * 获取1-可改，0-不可改
     *
     * @return MODIFY - 1-可改，0-不可改
     */
    public Byte getModify() {
        return modify;
    }

    /**
     * 设置1-可改，0-不可改
     *
     * @param modify 1-可改，0-不可改
     */
    public void setModify(Byte modify) {
        this.modify = modify;
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
     * 获取1-收费，0-不收费（只限话题内容，评论冗余该字段）
     *
     * @return charge - 1-收费，0-不收费（只限话题内容，评论冗余该字段）
     */
    public Byte getCharge() {
        return charge;
    }

    /**
     * 设置1-收费，0-不收费（只限话题内容，评论冗余该字段）
     *
     * @param charge 1-收费，0-不收费（只限话题内容，评论冗余该字段）
     */
    public void setCharge(Byte charge) {
        this.charge = charge;
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