package org.glut.competition.service.Model;

import lombok.Data;
import org.glut.competition.entity.Contest;

@Data
public class EnrollModel {
    private long id;

    private String studentId;

    private String studentName;

    private String studentFaculty;

    private String studentClass;

    private String idCard;

    private String instructor;

    private Long contestId;

    private Contest contest;

}
