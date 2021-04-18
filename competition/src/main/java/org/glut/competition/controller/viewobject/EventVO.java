package org.glut.competition.controller.viewobject;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventVO {
    /**
     * 赛事名称
     */
    private String competitionName;

    /**
     * 赛事内容
     */
    private String competitionContent;

    /**
     * 公告发布时间
     */
    private LocalDateTime competitionStamp;

    /**
     * 附件
     */
    private String fileName;
}
