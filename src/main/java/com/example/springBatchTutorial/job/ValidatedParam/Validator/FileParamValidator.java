package com.example.springBatchTutorial.job.ValidatedParam.Validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * file 이름이 유효한지 검증 validate() 제공해줌
 */
public class FileParamValidator implements JobParametersValidator{

    @Override
    public void validate(@Nullable JobParameters parameters) throws JobParametersInvalidException {
        String fileName = parameters.getString("fileName");

        if(!StringUtils.endsWithIgnoreCase(fileName, "csv")){
            throw new JobParametersInvalidException("This is not csv file");
        }
        
    }
    
}
