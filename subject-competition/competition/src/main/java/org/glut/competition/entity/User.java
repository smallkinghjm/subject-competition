package org.glut.competition.entity;

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
 * @since 2021-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    /**
     * 学号
     */

    private String userId;

    /**
     * 姓名
     */

    private String userName;

    /**
     * 学院
     */

    private String faculty;

    //班级

    private String classA;

    /**
     * 性别，1-男，2-女
     */

    private Integer gender;

    /**
     * 邮箱
     */
    private String email;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
