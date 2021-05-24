package org.glut.competition.service.Model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserModel {
    /**
     * 学号或职工号
     */
    @NotBlank(message = "学号不能为空")
    @ExcelProperty("学号")
    private String userId;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @ExcelProperty("姓名")
    private String userName;

    /**
     * 学院班级
     */
    @NotBlank(message = "学院不能为空")
    @ExcelProperty("学院")
    private String faculty;

    //班级
    @NotBlank(message = "班级不能为空")
    @ExcelProperty("班级")
    private String classA;

    /**
     * 性别，1-男，2-女
     */
    @NotNull(message = "性别不能不填写")
    @ExcelProperty("性别")
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
    @ExcelProperty("加密密码")
    private String encryptPassword;
}
