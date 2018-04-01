package com.ronis.publisher.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublisherResponse {

    @JsonProperty("publisher-response")
    private String publisherResponse;

    public PublisherResponse() {
    }

    public PublisherResponse(String publisherResponse) {
        this.publisherResponse = publisherResponse;
    }

    public String getPublisherResponse() {
        return publisherResponse;
    }

    public void setPublisherResponse(String publisherResponse) {
        this.publisherResponse = publisherResponse;
    }

    public String toString() {
        return "PublisherResponse [publisherResponse=" + publisherResponse + "]";
    }
}
