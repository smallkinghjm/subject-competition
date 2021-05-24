package org.glut.competition.entity;

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
public class Admin extends Model<Admin> {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员账号
     */
    private String name;

    /**
     * 管理员密码
     */
    private String encryptPassword;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
