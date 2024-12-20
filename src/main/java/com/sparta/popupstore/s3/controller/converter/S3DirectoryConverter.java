package com.sparta.popupstore.s3.controller.converter;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.s3.enums.Directory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class S3DirectoryConverter implements Converter<String, Directory> {

    @Override
    public Directory convert(String directory) {
        try{
            log.info(directory);
            return Directory.valueOf(directory.toUpperCase().replace("-","_"));
        }
        catch (IllegalArgumentException e){
            throw new CustomApiException(ErrorCode.S3_NOT_DIRECTORY);
        }
    }
}
