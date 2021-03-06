package com.forum.main.dao;

import com.forum.main.model.Topic;

import java.sql.SQLException;
import java.util.List;

public interface TopicDao {

    List<Topic> getAllTopic() throws SQLException;

    Topic getTopicById(int id) throws SQLException;

    void incrementTopicViewCount(int id) throws SQLException;

    void addTopic(Topic topic) throws SQLException;

    List<Topic> getPopularTopics() throws SQLException;

    List<Topic> getAllTopicsByUserId(int id) throws SQLException;

    List<Topic> getSimilarTopics(String[] keywords) throws SQLException;

}
