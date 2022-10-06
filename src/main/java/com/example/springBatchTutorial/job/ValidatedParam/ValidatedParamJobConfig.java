package com.example.springBatchTutorial.job.ValidatedParam;

import java.util.Arrays;

import org.hibernate.mapping.Array;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.springBatchTutorial.job.ValidatedParam.Validator.FileParamValidator;

import lombok.RequiredArgsConstructor;

/**
 * desc : 파일 이름 파라미터 전달 그리고 검증
 * run : --spring.batch.job.names=validatedParamJob -fileName=test.csv
 */
@Configuration
@RequiredArgsConstructor
public class ValidatedParamJobConfig {
    

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job validatedParamJob(Step validatedParamStep){
        return jobBuilderFactory.get("validatedParamJob")
                    .incrementer(new RunIdIncrementer())
                    .validator(multipleValidator())  //csv파일 형식 맞는지 확인
                    .start(validatedParamStep)
                    .build();
        }
    

    //여러개의 validator를 등록하는 경우
    private CompositeJobParametersValidator multipleValidator(){
        CompositeJobParametersValidator validator = new CompositeJobParametersValidator();
        validator.setValidators(Arrays.asList(new FileParamValidator()));

        return validator;
    }

    
    @JobScope
    @Bean
    public Step validatedParamStep(Tasklet validatedParamTasklet){
        return stepBuilderFactory.get("validatedParamStep")
                    .tasklet(validatedParamTasklet)
                    .build();
    }

    @StepScope
    @Bean
    public Tasklet validatedParamTasklet(@Value("#{jobParameters['fileName']}") String fileName){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("fileName::" +  fileName);
                System.out.println("validatedParam");
                return RepeatStatus.FINISHED;
            }
            
        };
    }

}
