package com.zx.bs.model;

import lombok.Data;


@Data
public class Answer {

    private int answer_id;
    private int question_id;
    private String answer_content;
    private User user;

}
