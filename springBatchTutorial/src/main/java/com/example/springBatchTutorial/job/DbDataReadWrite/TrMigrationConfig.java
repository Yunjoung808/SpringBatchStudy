package com.example.springBatchTutorial.job.DbDataReadWrite;

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
import org.springframework.lang.Nullable;

import com.example.springBatchTutorial.core.domain.accounts.AccountsRepository;
import com.example.springBatchTutorial.core.domain.orders.Orders;
import com.example.springBatchTutorial.core.domain.orders.OrdersRepository;
import com.example.springBatchTutorial.job.HelloWorld.JobLoggerListner;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class TrMigrationConfig {

    @Autowired
    private OrdersRepository ordersRepository;  

    @Autowired
    private AccountsRepository accountsRepository;

    
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job trMigrationJob(){
        return jobBuilderFactory.get("trMigrationJob")
                    .incrementer(new RunIdIncrementer())
                    .listener(new JobLoggerListner())
                    .start(trMigrationStep())
                    .build();
        }

    
    @JobScope
    @Bean
    public Step trMigrationStep(){
        return stepBuilderFactory.get("trMigrationStep")
                    //.tasklet(trMigrationTasklet())  이전에는 tasklet을 사용했지만 이번에는 Item Reader, Processor, Writer를 사용
                    .<Orders,Orders>chunk(5) //어떤 데이터를 읽어와서 어떤 데이터로 쓸건지, 몇개의 단위로 데이터를 처리할 것인지
                    .build();
    }

   
    
}
