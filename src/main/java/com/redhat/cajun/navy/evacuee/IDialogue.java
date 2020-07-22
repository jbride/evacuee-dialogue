package com.redhat.cajun.navy.evacuee;

import java.util.Map;

public interface IDialogue {

    public String nextMessage(Map<String, String> requestParams);
}
