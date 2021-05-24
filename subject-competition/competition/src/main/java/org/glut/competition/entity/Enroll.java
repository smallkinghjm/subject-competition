package org.glut.competition.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Enroll extends Model<Enroll> {

    private static final long serialVersionUID = 1L;

    /**
     * 学号
     */
    @ExcelProperty("学号")
    private String studentId;

    /**
     * 姓名
     */
    @ExcelProperty("姓名")
    private String studentName;

    @ExcelProperty("学院")
    private String studentFaculty;

    /**
     * 班级
     */
    @ExcelProperty("班级")
    private String studentClass;

    /**
     * 身份证号
     */
    @ExcelProperty("身份证号")
    private String idCard;

    /**
     * 指导教师
     */
    @ExcelProperty("指导教师")
    private String instructor;

    /**
     * 赛事编号
     */
    @ExcelIgnore
    private Long contestId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
