package com.example.springBatchTutorial.job.HelloWorld;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobLoggerListner implements JobExecutionListener{

    private static String BEFORE_MESSAGE = "{} Job is Running";
    private static String AFTER_MESSAGE = "{} Job is Done. (Status: {})";

    //JOb이 실행되기전
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(BEFORE_MESSAGE, jobExecution.getJobInstance().getJobName());
        
    }

    //Job이 실행된 후
    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info(AFTER_MESSAGE, jobExecution.getJobInstance().getJobName(), jobExecution.getStatus());

        if(jobExecution.getStatus() == BatchStatus.FAILED){
            //실패할 경우 Slack message
            log.info("Job is Failed");
        }
        
    }
    
}
