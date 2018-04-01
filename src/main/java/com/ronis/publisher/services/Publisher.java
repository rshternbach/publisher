package com.ronis.publisher.services;

import com.ronis.publisher.executor.commons.model.PublishParameters;

public interface Publisher {
    
    String publish(PublishParameters publishParameters);

}
