package DAO;

import java.sql.*;
import java.util.*;
import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    public List<Message> getAllMasseges() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messeges = new ArrayList<>();
        try {
            String sql = "select * from message";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message msg = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messeges.add(msg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messeges;
    }

    public Message getMessageById(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from message where message_id = " + message_id;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message msg = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return msg;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> getAllMessagesByUserId(int account_id) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messeges = new ArrayList<>();
        try {
            String sql = "select * from message where posted_by = " + account_id;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message msg = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messeges.add(msg);
            }
            return messeges;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Message createNewMassage(Message msg) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into message (posted_by,message_text,time_posted_epoch) values(?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, msg.getPosted_by());
            ps.setString(2, msg.getMessage_text());
            ps.setLong(3, msg.getTime_posted_epoch());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                msg.setMessage_id(rs.getInt(1));
            }
            return msg;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Message deleteMessageById(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            Message msg = getMessageById(message_id);
            if (msg != null) {
                String sql = "delete from message where message_id = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, message_id);
                ps.executeUpdate();
                return msg;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateMessage(int message_id, String message_text) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "update message set message_text = ? where message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, message_text);
            ps.setInt(2, message_id);
            int getRows = ps.executeUpdate();
            return getRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
