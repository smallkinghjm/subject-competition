package org.glut.competition.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Works extends Model<Works> {

    private static final long serialVersionUID = 1L;

    /**
     * 作品编号
     */
    @TableId(value = "works_id", type = IdType.AUTO)
    private Integer worksId;

    /**
     * 作品名称
     */
    private String worksName;

    /**
     * 作品内容
     */
    private String worksContent;

    /**
     * 学生学号
     */
    private String studentId;

    /**
     * 赛事编号
     */
    private Integer competitionId;


    @Override
    protected Serializable pkVal() {
        return this.worksId;
    }

}
