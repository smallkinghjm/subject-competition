package org.glut.competition.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

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
public class Contest extends Model<Contest> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "contest_id", type = IdType.AUTO)
    private Long contestId;

    /**
     * 赛事名称
     */
    private String contestName;

    /**
     * 开始时间
     */
    private LocalDate contestStart;

    /**
     * 结束时间
     */
    private LocalDate contestEnd;

    /*
     * 赛事内容
     * */
    @NotNull
    private String contestContent;

    /**
     * 负责人
     */
    private String leader;

    private String enclosureName;
    /**
     * 状态
     */
    @TableField(exist = false)
    private String state;


    @Override
    protected Serializable pkVal() {
        return this.contestId;
    }

}
