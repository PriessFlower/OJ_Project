package com.zt.oj.Event;

import org.springframework.context.ApplicationListener;

public interface JudgeEventListener extends ApplicationListener<JudgeEvent> {
    void onApplicationEvent(JudgeEvent event);
}
