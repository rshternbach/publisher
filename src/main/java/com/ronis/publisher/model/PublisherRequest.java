package com.ronis.publisher.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublisherRequest {

    @JsonProperty("publisher-name")
    private String publisherName;

    @JsonProperty("task-name")
    private String taskName;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String toString() {
        return "PublisherRequest [publisherName=" + publisherName + ", taskName=" + taskName + "]";
    }

}
