package com.zx.yunqishe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "doc_content")
@Getter
@Setter
public class DocContent {

    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 父id,-1-总分类无父id
     */
    private Integer pid;

    /**
     * 排序id
     */
    private Integer sid;

    /**
     * 名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 1-可见，0-不可见
     */
    private Byte visible;

    /**
     * 1-收费，0-不收费
     */
    private Byte charge;

    /**
     * 文档分类创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(
            locale = "zh",
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date createTime;

    /** 封面 */
    private String cover;

    /**
     * 浏览次数
     */
    @Column(name = "view_count")
    private Integer view;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @JsonFormat(
            locale = "zh",
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date updateTime;

    /**
     * 摘要
     */
    private String introduce;

    /**
     * 内容
     */
    private String text;

    public static void main(String[] args) {
        DocContent doc = new DocContent();
        Cat cat = doc.new Cat();
        System.out.println(((Animal) cat).getName());
    }

    class Animal {
        private String name = "tiger";

        public String getName() {
            System.out.println("animal");
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Cat extends Animal {
        private String name = "tiger2";

        public String getName() {
            System.out.println("cat");
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
