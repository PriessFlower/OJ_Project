package com.zt.oj.Event;

import com.zt.oj.Judge.JudgeFactory;
import com.zt.oj.Judge.JudgeStrategy;
import com.zt.oj.exception.BusinessException;
import com.zt.oj.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class JudgeEventListenerImpl implements JudgeEventListener{
    @Override
    public void onApplicationEvent(JudgeEvent event) {
        //工厂模式加策略模式，通过语言来确定使用的策略，然后生成对应的实体，使用其方法
        JudgeFactory judgeFactory = new JudgeFactory();
        String language = event.getLanguage();
        JudgeStrategy judgeStrategy = judgeFactory.getJudgeStrategy(language);
        if (judgeStrategy == null) throw new BusinessException(ErrorCode.PARAMS_ERROR,"没有该语言的代码沙箱");
        try {
            judgeStrategy.execute(event.getCode(), event.getTestCase());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
