package com.later.work.service;

public interface IOssService {

    String upload(Long id, String imageBase64, String uri);

    String rollBackRecord(Long id, String imageBase64, String uri, boolean hasRollBackRecord);

    String recover(Long id);
}
