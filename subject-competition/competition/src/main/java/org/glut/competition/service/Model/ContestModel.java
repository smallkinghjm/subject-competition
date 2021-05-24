package org.glut.competition.service.Model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Data
public class ContestModel {

    private Long contestId;
    /**
     * 状态
     */
    private String state;

    /**
     * 赛事名称
     */
    @NotNull
    private String contestName;

    /**
     * 开始时间
     */
    @NotNull
    private LocalDate contestStart;

    /**
     * 结束时间
     */
    @NotNull
    private LocalDate contestEnd;

    /*
    * 赛事内容
    * */
    @NotNull
    private String contestContent;

    /**
     * 负责人
     */
    @NotNull(message = "必须指定负责人")
    private String leader;

    private String enclosureName;


}
