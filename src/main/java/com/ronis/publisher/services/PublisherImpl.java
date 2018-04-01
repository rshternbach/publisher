package com.ronis.publisher.services;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.ronis.publisher.executor.commons.model.PublishCallable;
import com.ronis.publisher.executor.commons.model.PublishParameters;

@Service
public class PublisherImpl implements Publisher {

    private static final String HAZELCAST_PROPERTIES_FILE_NANE = "hazelcast-client-publisher.xml";

    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherImpl.class);

    private IExecutorService executorService;

    private HazelcastInstance instance;

    @PostConstruct
    public void init() throws Exception {
        LOGGER.debug("Init Hazlecast client");
        instance = HazelcastClient.newHazelcastClient(loadHazelCastProperties());
        this.executorService = instance.getExecutorService("executorService");
    }

    public String publish(PublishParameters publishParameters) {
        LOGGER.info("Publishing parameters: {}", publishParameters);
        String executorResponse= null;
        Future<String> task = executorService.submit(new PublishCallable(publishParameters));
        try {
            executorResponse = task.get();
            LOGGER.info("this is the executor response: " + executorResponse);
        } catch (InterruptedException e) {
            LOGGER.error("Task has been interupted: {}", e);
        } catch (ExecutionException e) {
            LOGGER.error("Error publishing task: {}", e);
        }
        return executorResponse;
    }

    private static ClientConfig loadHazelCastProperties() throws Exception {
        ClientConfig hazelcastConfig = null;
        try {
            hazelcastConfig = new XmlClientConfigBuilder(HAZELCAST_PROPERTIES_FILE_NANE).build();
            LOGGER.info(
                    "Hazelcast client configuration file loaded succesefully from path");
        } catch (IllegalArgumentException | IOException e) {
            LOGGER.error("Error hazelcast client loading configuration {}", e);
            throw new Exception("hazelcast client configuration is not found, cannot start publisher application");
        }
        return hazelcastConfig;
    }

    @PreDestroy
    public void destroy() {
        LOGGER.debug("Destroy Hazlecast client");
        if (instance != null)
            instance.shutdown();
    }

}
