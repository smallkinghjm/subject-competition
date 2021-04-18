package org.glut.competition.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
public class CompetitionEvent extends Model<CompetitionEvent> {

    private static final long serialVersionUID = 1L;

    /**
     * 赛事编号
     */
    @TableId(value = "competition_id", type = IdType.AUTO)
    private Integer competitionId;

    /**
     * 赛事名称
     */
    private String competitionName;

    /**
     * 赛事内容
     */
    private String competitionContent;

    /**
     * 开始时间
     */
    private LocalDate competitionBegin;

    /**
     * 结束时间
     */
    private LocalDate competitionFinish;




    @Override
    protected Serializable pkVal() {
        return this.competitionId;
    }

}
