package org.glut.competition.entity;

import com.alibaba.excel.annotation.ExcelProperty;
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
public class Password extends Model<Password> {

    private static final long serialVersionUID = 1L;

    /**
     * 加密密码
     */
    private String encryptPassword;

    /**
     * 学号
     */
    private String userId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
