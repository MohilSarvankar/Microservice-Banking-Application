package com.viper.account.config;

import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viper.common.dto.ApiResponse;
import com.viper.common.exception.ResourceNotFoundException;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

public class FeignClientErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		try {
            String body = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            
            // Convert JSON to ApiResponse
            ObjectMapper mapper = new ObjectMapper();
            ApiResponse<?> apiResponse = mapper.readValue(body, ApiResponse.class);

            // If customer says not found → throw ResourceNotFound
            if (response.status() == 404) {
                return new ResourceNotFoundException(apiResponse.getMessage());
            }

            // Other errors → generic exception
            return new RuntimeException(apiResponse.getMessage());

        } 
		catch (Exception e) {
            return new RuntimeException("Feign client error occurred");
        }
	}

}
