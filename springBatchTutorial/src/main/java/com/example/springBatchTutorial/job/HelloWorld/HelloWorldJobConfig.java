package com.example.springBatchTutorial.job.HelloWorld;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class HelloWorldJobConfig {
    

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job helloworldJOb(){
        return jobBuilderFactory.get("helloWorldJob")
                    .incrementer(new RunIdIncrementer())
                    .listener(new JobLoggerListner())
                    .start(helloworldStep())
                    .build();
        }

    
    @JobScope
    @Bean
    public Step helloworldStep(){
        return stepBuilderFactory.get("helloWorldStep")
                    .tasklet(helloWorldTasklet())
                    .build();
    }

    @StepScope
    @Bean
    public Tasklet helloWorldTasklet(){
        return new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("helloWorld");
                return RepeatStatus.FINISHED;
            }
            
        };
    }

}
