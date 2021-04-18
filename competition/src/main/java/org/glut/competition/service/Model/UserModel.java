package org.glut.competition.service.Model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserModel {
    /**
     * 学号或职工号
     */
    @NotNull(message = "学号或职工号不能为空")
    private String userId;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String userName;

    /**
     * 学院班级
     */
    @NotBlank(message = "学院班级不能为空")
    private String collegeClass;

    /**
     * 角色，0-学生，1-普通教师，2-审核教师，3-管理员
     */
    @NotNull(message = "身份不能不选")
    private Integer role;

    /**
     * 性别，1-男，2-女
     */
    @NotNull(message = "性别不能不填写")
    private Integer gender;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式错误")
    private String email;

    /**
     * 加密密码
     */
    @NotBlank(message = "密码不能为空")
    private String encryptPassword;
}
