package classes.model.behavior.storages.impl;

import classes.db.DBConnection;
import classes.model.User;
import classes.model.UserParams;
import classes.model.behavior.storages.UserStorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUserStorage implements UserStorage {

    private Connection connection = null;

    @Override
    public User getUser(String login, String password) {
        User user = null;
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            String sql = "SELECT *FROM USER_ WHERE LOGIN=? AND PASSWORD=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, password);
            user = getUserByConditions(ps);
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Exception occured!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Exception occured!");
                StackTraceElement[] stackTraceElements = ex.getStackTrace();
                for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                    System.out.println(stackTraceElements[i].toString());
                }
            }
        }
        return user;
    }

    @Override
    public void storeUser(User user) {
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            String mergeSql = "MERGE INTO USER_ U USING("
                    + "SELECT *FROM dual)"
                    + " ON((select count(*) from user_ where user_id=?)!=0)"
                    + " WHEN MATCHED THEN "
                    + " UPDATE SET USER_NAME = ?, USER_SURNAME=?, EMAIL=?, PHONE=?,"
                    + " ADDRESS=?,LOGIN=?, PASSWORD=?, VERSION=?, PRIVILEGE=? WHERE USER_ID=? "
                    + " WHEN NOT MATCHED THEN "
                    + "INSERT VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(mergeSql);
            ps.setInt(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getSurname());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getLogin());
            ps.setString(8, user.getPassword());
            ps.setInt(9, user.getVersion());
            ps.setString(10, user.getPrivilege());
            ps.setInt(11, user.getId());
            ps.setInt(12, user.getId());
            ps.setString(13, user.getName());
            ps.setString(14, user.getSurname());
            ps.setString(15, user.getEmail());
            ps.setString(16, user.getPhone());
            ps.setString(17, user.getAddress());
            ps.setString(18, user.getLogin());
            ps.setString(19, user.getPassword());
            ps.setInt(20, user.getVersion());
            ps.setString(21, user.getPrivilege());
            ps.executeQuery();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Exception occured!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Exception occured!");
                StackTraceElement[] stackTraceElements = ex.getStackTrace();
                for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                    System.out.println(stackTraceElements[i].toString());
                }
            }
        }

    }

    @Override
    public User getUserById(int id) {
        User user = null;
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            String sql = "SELECT *FROM USER_ WHERE USER_ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            user = getUserByConditions(ps);
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Exception occured!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Exception occured!");
                StackTraceElement[] stackTraceElements = ex.getStackTrace();
                for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                    System.out.println(stackTraceElements[i].toString());
                }
            }
        }
        return user;
    }

    @Override
    public User getUserByLogin(String login) {
        User user = null;
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            String sql = "SELECT *FROM USER_ WHERE login=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, login);
            user = getUserByConditions(ps);
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Exception occured!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Exception occured!");
                StackTraceElement[] stackTraceElements = ex.getStackTrace();
                for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                    System.out.println(stackTraceElements[i].toString());
                }
            }
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = null;
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            usersList = new ArrayList<User>();
            String sql = "SELECT *FROM USER_ ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("USER_ID"));
                user.setName(rs.getString("USER_NAME"));
                user.setSurname(rs.getString("USER_SURNAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setPhone(rs.getString("PHONE"));
                user.setAddress(rs.getString("ADDRESS"));
                user.setLogin(rs.getString("LOGIN"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setVersion(rs.getInt("VERSION"));
                user.setPrivilege(rs.getString("PRIVILEGE"));
                usersList.add(user);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Exception occured!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Exception occured!");
                StackTraceElement[] stackTraceElements = ex.getStackTrace();
                for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                    System.out.println(stackTraceElements[i].toString());
                }
            }
        }
        return usersList;
    }


    @Override
    public void deleteUser(int id) {
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            String sql = "DELETE FROM USER_ WHERE USER_ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeQuery();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Exception occured!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Exception occured!");
                StackTraceElement[] stackTraceElements = ex.getStackTrace();
                for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                    System.out.println(stackTraceElements[i].toString());
                }
            }
        }
    }

    private User getUserByConditions(PreparedStatement ps) {
        User user = null;
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("USER_ID"));
                user.setName(rs.getString("USER_NAME"));
                user.setSurname(rs.getString("USER_SURNAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setPhone(rs.getString("PHONE"));
                user.setAddress(rs.getString("ADDRESS"));
                user.setLogin(rs.getString("LOGIN"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setVersion(rs.getInt("VERSION"));
                user.setPrivilege(rs.getString("PRIVILEGE"));
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Exception occured!");
                StackTraceElement[] stackTraceElements = ex.getStackTrace();
                for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                    System.out.println(stackTraceElements[i].toString());
                }
            }
        }
        return user;
    }

    public List<User> searchUsersByParams(UserParams userParams) {
        List<User> usersList = null;
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            StringBuilder sql = new StringBuilder("SELECT * FROM USER_ WHERE ");
            boolean hasPrev=false;
            if (!userParams.getAddress().equals("")) {
                sql.append("ADDRESS='"+userParams.getAddress()+"'");
                hasPrev=true;
            }
            if (!userParams.getLogin().equals("")) {
                if(hasPrev)
                    sql.append(" AND ");
                sql.append("LOGIN='"+userParams.getLogin()+"'");
                hasPrev=true;
            }
            if (!userParams.getName().equals("")) {
                if(hasPrev)
                    sql.append(" AND ");
                sql.append("USER_NAME='"+userParams.getName()+"'");
                hasPrev=true;
            }
            if (!userParams.getSurname().equals("")) {
                if(hasPrev)
                    sql.append(" AND ");
                sql.append("USER_SURNAME='"+userParams.getSurname()+"'");
                hasPrev=true;
            }
            if (!userParams.getEmail().equals("")) {
                if(hasPrev)
                    sql.append(" AND ");
                sql.append("EMAIL='"+userParams.getEmail()+"'");
                hasPrev=true;
            }
            if (!userParams.getPhone().equals("")) {
                if(hasPrev)
                    sql.append(" AND ");
                sql.append("PHONE="+userParams.getPhone()+"'");
            }
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql.toString());
            usersList = new ArrayList<User>();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("USER_ID"));
                user.setName(rs.getString("USER_NAME"));
                user.setSurname(rs.getString("USER_SURNAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setPhone(rs.getString("PHONE"));
                user.setAddress(rs.getString("ADDRESS"));
                user.setLogin(rs.getString("LOGIN"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setVersion(rs.getInt("VERSION"));
                user.setPrivilege(rs.getString("PRIVILEGE"));
                usersList.add(user);
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Exception occured!");
                StackTraceElement[] stackTraceElements = ex.getStackTrace();
                for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                    System.out.println(stackTraceElements[i].toString());
                }
            }
        }
        return usersList;
    }

    @Override
    public List<User> searchUsersBySubparams(UserParams userParams) {
        List<User> usersList = null;
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            StringBuilder sql = new StringBuilder("SELECT * FROM USER_ WHERE ");
            boolean hasPrev=false;
            if (!userParams.getAddress().equals("")) {
                sql.append("ADDRESS LIKE '%"+userParams.getAddress()+"%'");
                hasPrev=true;
            }
            if (!userParams.getLogin().equals("")) {
                if(hasPrev)
                    sql.append(" AND ");
                sql.append("LOGIN LIKE '%"+userParams.getLogin()+"%'");
                hasPrev=true;
            }
            if (!userParams.getName().equals("")) {
                if(hasPrev)
                    sql.append(" AND ");
                sql.append("USER_NAME LIKE '%"+userParams.getName()+"%'");
                hasPrev=true;
            }
            if (!userParams.getSurname().equals("")) {
                if(hasPrev)
                    sql.append(" AND ");
                sql.append("USER_SURNAME LIKE '%"+userParams.getSurname()+"%'");
                hasPrev=true;
            }
            if (!userParams.getEmail().equals("")) {
                if(hasPrev)
                    sql.append(" AND ");
                sql.append("EMAIL LIKE '%"+userParams.getEmail()+"%'");
                hasPrev=true;
            }
            if (!userParams.getPhone().equals("")) {
                if(hasPrev)
                    sql.append(" AND ");
                sql.append("PHONE LIKE '%"+userParams.getPhone()+"%'");
            }
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql.toString());
            usersList = new ArrayList<User>();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("USER_ID"));
                user.setName(rs.getString("USER_NAME"));
                user.setSurname(rs.getString("USER_SURNAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setPhone(rs.getString("PHONE"));
                user.setAddress(rs.getString("ADDRESS"));
                user.setLogin(rs.getString("LOGIN"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setVersion(rs.getInt("VERSION"));
                user.setPrivilege(rs.getString("PRIVILEGE"));
                usersList.add(user);
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Exception occured!");
                StackTraceElement[] stackTraceElements = ex.getStackTrace();
                for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                    System.out.println(stackTraceElements[i].toString());
                }
            }
        }
        return usersList;
    }
}
