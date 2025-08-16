package com.zt.oj.Judge;

import com.zt.oj.Judge.entity.CaseResult;
import com.zt.oj.Judge.entity.JudgeResult;
import com.zt.oj.Judge.entity.TestCase;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JavaJudgeStrategy implements JudgeStrategy{


    @Override
    public JudgeResult execute(String userCode, List<TestCase> testCaseList) {
//        Judge0Client judge0Client = new Judge0Client();
//        List<CaseResult> execute = judge0Client.execute(userCode, testCaseList);
//        log.info(execute.toString());
////        log.info(execute.toString());
////        return null;
//        return null;
        //使用docker容器运行代码
        //使用关联表查询出测试用例
        //共四个步骤 1. 临时存储代码 2.编译代码 3，执行代码 4. 收集输出或者错误信息
        //关于临时存储代码，就存储在服务器上
        String rootPath = System.getProperty("user.dir");
        File rootFile = new File(rootPath);
        //todo
        return null;
    }
}
