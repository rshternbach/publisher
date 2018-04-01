package com.ronis.publisher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ronis.publisher.executor.commons.model.PublishParameters;
import com.ronis.publisher.model.PublisherRequest;
import com.ronis.publisher.model.PublisherResponse;
import com.ronis.publisher.services.Publisher;

@Controller
public class PublisherController {
    
    @Autowired
    private Publisher publisherService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherController.class);

    @RequestMapping(value = "/api/publisher/publish", method = RequestMethod.POST)
    public ResponseEntity<PublisherResponse> publisherResponse(
            @RequestBody PublisherRequest publisherRequest) {
        LOGGER.info("Publisher request has been recieved {}", publisherRequest);
        try {
        String publisherResponse = publisherService.publish(new PublishParameters(publisherRequest.getTaskName(), publisherRequest.getPublisherName()));
            return new ResponseEntity<PublisherResponse>(
                    new PublisherResponse(publisherResponse == null ? "No response from executor" : publisherResponse),
                    HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Unexpected error: {}", e);
            return new ResponseEntity<PublisherResponse>(new PublisherResponse("Unexpected Error"),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
