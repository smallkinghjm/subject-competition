package org.glut.competition.service.Model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CompetitionEventModel {
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

    //附件名
    private String fileName;

}
