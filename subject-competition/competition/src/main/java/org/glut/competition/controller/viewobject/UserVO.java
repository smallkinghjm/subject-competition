package org.glut.competition.controller.viewobject;

import lombok.Data;

@Data
public class UserVO {
    /**
     * 学号或职工号
     */
    private String userId;

    /**
     * 姓名
     */
    private String userName;

    private String faculty;

    //班级
    private String classA;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 邮箱
     */
    private String email;

}
