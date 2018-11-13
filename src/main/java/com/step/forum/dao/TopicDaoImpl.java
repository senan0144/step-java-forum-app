package com.step.forum.dao;

import com.step.forum.model.Comment;
import com.step.forum.model.Topic;
import com.step.forum.model.User;
import com.step.forum.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TopicDaoImpl implements TopicDao {

    private final String GET_ALL_TOPIC_SQL = "select t.id_topic, t.title, t.description as topic_description, t.share_date, t.view_count, u.id_user, u.email, u.first_name, u.last_name, u.image, c.id_comment, c.description as comment_description, c.write_date from topic t inner join user u on t.id_user = u.id_user left join comment c on t.id_topic = c.id_topic order by t.share_date desc";
    private final String GET_TOPIC_BY_ID_SQL = "select t.id_topic, t.title, t.description as topic_description, t.share_date, t.view_count, u.id_user, u.email, u.first_name, u.last_name, u.image as 't.image', c.id_comment, c.description as comment_description, c.write_date, us.id_user as id_user_comment, us.first_name as first_name_comment, us.last_name as last_name_comment, us.image as 'c.image' from topic t inner join user u on t.id_user = u.id_user left join comment c on t.id_topic = c.id_topic left join user us on us.id_user = c.id_user where t.id_topic = ?";
    private final static String INCREMENT_TOPIC_VIEW_COUNT_SQL = "update topic set view_count = view_count + 1 where id_topic = ?";
    private final static String ADD_TOPIC_SQL = "insert into topic(title, description, share_date, view_count, id_user) values(?,?,?,?,?);";
    private final static String GET_POPULAR_TOPICS_SQL = "select t.id_topic, t.title, count(c.id_comment) as comments from topic t left join comment c on t.id_topic = c.id_topic group by t.title having comments > ? order by comments desc limit ?";
    private final static String GET_ALL_ACTIVE_POPULAR_TOPIC_SQL ="select * from topic where id_user = ?";
    private final static String GET_SIMILAR_TOPICS_SQL = "select t.id_topic, t.title, t.description as topic_description, t.share_date, t.view_count, u.id_user, u.email, u.first_name, u.last_name, u.image, c.id_comment, c.description as comment_description, c.write_date from topic t inner join user u on t.id_user = u.id_user left join comment c on t.id_topic = c.id_topic where";

    @Override
    public List<Topic> getAllTopic() {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Topic> list = new ArrayList<>();

        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(GET_ALL_TOPIC_SQL);
            rs = ps.executeQuery();
            Map<Integer, Topic> map = new LinkedHashMap<>(); //topik-lerin map-da tekrarlanmamasi ucun yaradilir

            while (rs.next()) {
                Topic topic = map.get(rs.getInt("id_topic")); //ya topic gelecek yada null

                if (topic == null) { //eger null-dursa yeni topik yaradilir
                    topic = new Topic();
                    topic.setId(rs.getInt("id_topic"));
                    topic.setTitle(rs.getString("title"));
                    topic.setDescription(rs.getString("topic_description"));
                    topic.setShareDate(rs.getTimestamp("share_date").toLocalDateTime());
                    topic.setViewCount(rs.getInt("view_count"));

                    User user = new User();
                    user.setId(rs.getInt("id_user"));
                    user.setEmail(rs.getString("email"));
                    user.setFirstname(rs.getString("first_name"));
                    user.setLastname(rs.getString("last_name"));
                    user.setImagePath(rs.getString("image"));

                    topic.setUser(user); //topik-in user-i set edilir
                    map.put(topic.getId(), topic); //map-a topik elave edilir [id_topik, topik]
                }

                if (rs.getInt("id_comment") != 0) {
                    Comment comment = new Comment();
                    comment.setId(rs.getInt("id_comment"));
                    comment.setDescription(rs.getString("comment_description"));
                    comment.setWriteDate(rs.getTimestamp("write_date").toLocalDateTime());
                    topic.addComment(comment);
                }
            }

            list = new ArrayList<>(map.values()); //topik-ler liste cevirilir

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            DbUtil.closeAll(con, ps, rs);
        }

        return list;
    }

    @Override
    public Topic getTopicById(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Topic topic = null;

        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(GET_TOPIC_BY_ID_SQL);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            Map<Integer, Topic> map = new LinkedHashMap<>();

            while (rs.next()) {
                topic = map.get(rs.getInt("id_topic"));

                if (topic == null) {
                    topic = new Topic();
                    topic.setId(rs.getInt("id_topic"));
                    topic.setTitle(rs.getString("title"));
                    topic.setDescription(rs.getString("topic_description"));
                    topic.setShareDate(rs.getTimestamp("share_date").toLocalDateTime());
                    topic.setViewCount(rs.getInt("view_count"));

                    User user = new User();
                    user.setId(rs.getInt("id_user"));
                    user.setEmail(rs.getString("email"));
                    user.setFirstname(rs.getString("first_name"));
                    user.setLastname(rs.getString("last_name"));
                    user.setImagePath(rs.getString("t.image"));

                    topic.setUser(user);
                    map.put(topic.getId(), topic);
                }

                if (rs.getInt("id_comment") != 0) {
                    Comment comment = new Comment();
                    comment.setId(rs.getInt("id_comment"));
                    comment.setDescription(rs.getString("comment_description"));
                    comment.setWriteDate(rs.getTimestamp("write_date").toLocalDateTime());

                    User user = new User();
                    user.setId(rs.getInt("id_user_comment"));
                    user.setFirstname(rs.getString("first_name_comment"));
                    user.setLastname(rs.getString("last_name_comment"));
                    user.setImagePath(rs.getString("c.image"));
                    comment.setUser(user);

                    topic.addComment(comment);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            DbUtil.closeAll(con, ps, rs);
        }

        return topic;
    }

    @Override
    public boolean incrementTopicViewCount(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean result = false;

        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(INCREMENT_TOPIC_VIEW_COUNT_SQL);
            ps.setInt(1, id);
            ps.executeUpdate();

            result = true;

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            DbUtil.closeAll(con, ps);
        }

        return result;
    }

    @Override
    public boolean addTopic(Topic topic) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean result = false;

        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(ADD_TOPIC_SQL);
            ps.setString(1, topic.getTitle());
            ps.setString(2, topic.getDescription());
            ps.setString(3, topic.getShareDate().toString());
            ps.setInt(4, topic.getViewCount());
            ps.setInt(5, topic.getUser().getId());
            ps.executeUpdate();

            result = true;

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            DbUtil.closeAll(con, ps);
        }

        return result;
    }

    @Override
    public List<Topic> getPopularTopics() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Topic> list = new LinkedList<>();

        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(GET_POPULAR_TOPICS_SQL);
            ps.setInt(1, 0);
            ps.setInt(2, 7);
            rs = ps.executeQuery();

            while (rs.next()) {
                Topic topic = new Topic();
                topic.setId(rs.getInt("id_topic"));
                topic.setTitle(rs.getString("title"));
                topic.setCommentsCount(rs.getInt("comments"));

                list.add(topic);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            DbUtil.closeAll(con, ps, rs);
        }

        return list;
    }

    @Override
    public List<Topic> getAllTopicsByUserId(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Topic> list = new LinkedList<>();

        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(GET_ALL_ACTIVE_POPULAR_TOPIC_SQL);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                Topic topic = new Topic();
                topic.setId(rs.getInt("id_topic"));
                topic.setTitle(rs.getString("title"));
                topic.setDescription(rs.getString("description"));
                topic.setShareDate(rs.getTimestamp("share_date").toLocalDateTime());
                topic.setViewCount(rs.getInt("view_count"));

                list.add(topic);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            DbUtil.closeAll(con, ps, rs);
        }

        return list;
    }

    @Override
    public List<Topic> getSimilarTopics(String[] keywords) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Topic> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder(GET_SIMILAR_TOPICS_SQL);

        for (int i = 0; i < keywords.length; i++) { //[maven, spring] 2 (0 ,1)
            stringBuilder.append(" t.title like ?");
            if (i < keywords.length-1) {
                stringBuilder.append(" or");
            }
        }
        stringBuilder.append(" order by t.share_date desc");

        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(stringBuilder.toString());

            for (int i = 0; i < keywords.length; i++) { //(0, 1) 2
                ps.setString(i+1, "%" + keywords[i] + "%");
            }

            rs = ps.executeQuery();
            Map<Integer, Topic> map = new LinkedHashMap<>();

            while (rs.next()) {
                Topic topic = map.get(rs.getInt("id_topic"));

                if (topic == null) {
                    topic = new Topic();
                    topic.setId(rs.getInt("id_topic"));
                    topic.setTitle(rs.getString("title"));
                    topic.setDescription(rs.getString("topic_description"));
                    topic.setShareDate(rs.getTimestamp("share_date").toLocalDateTime());
                    topic.setViewCount(rs.getInt("view_count"));

                    User user = new User();
                    user.setId(rs.getInt("id_user"));
                    user.setEmail(rs.getString("email"));
                    user.setFirstname(rs.getString("first_name"));
                    user.setLastname(rs.getString("last_name"));
                    user.setImagePath(rs.getString("image"));

                    topic.setUser(user);
                    map.put(topic.getId(), topic);
                }

                if (rs.getInt("id_comment") != 0) {
                    Comment comment = new Comment();
                    comment.setId(rs.getInt("id_comment"));
                    comment.setDescription(rs.getString("comment_description"));
                    comment.setWriteDate(rs.getTimestamp("write_date").toLocalDateTime());
                    topic.addComment(comment);
                }
            }

            list = new ArrayList<>(map.values());

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            DbUtil.closeAll(con, ps, rs);
        }

        return list;
    }





}
