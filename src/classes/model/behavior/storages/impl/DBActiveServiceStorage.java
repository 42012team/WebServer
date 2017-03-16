package classes.model.behavior.storages.impl;

import classes.db.DBConnection;
import classes.model.ActiveService;
import classes.model.ActiveServiceState;
import classes.model.ActiveServiceStatus;
import classes.model.behavior.storages.ActiveServiceStorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBActiveServiceStorage implements ActiveServiceStorage {

    private Connection connection = null;

    @Override
    public List<ActiveService> getActiveServicesByUserId(int userId) {
        List<ActiveService> activeServiceList = null;
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            String sql = "SELECT *FROM ACTIVESERVICE WHERE (USER_ID=?) and (STATE!='CANCELLED') AND ((NEXTACTIVESERVICEID IS NULL)or ((SECOND_STATUS='DISCONNECTED')and (TDATE>CURRENT_TIMESTAMP))) ORDER BY ACTIVESERVICE_ID";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            activeServiceList=getActiveServiceListByParams(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activeServiceList;
    }

    @Override
    public void deleteActiveService(int activeServiceId) {
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            String sql = "DELETE FROM ACTIVESERVICE where ACTIVESERVICE_ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, activeServiceId);
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

    public void deleteActiveServicesWithTheSameType(int activeServiceId) {
        try {

            connection = DBConnection.getInstance().getDataSourse().getConnection();
            String sql = "DELETE FROM ACTIVESERVICE WHERE ACTIVESERVICE_ID=" +
                    "(SELECT NEXTACTIVESERVICEID FROM ACTIVESERVICE WHERE ACTIVESERVICE_ID=(SELECT NEXTACTIVESERVICEID FROM ACTIVESERVICE WHERE ACTIVESERVICE_ID=?))";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, activeServiceId);
            ps.executeQuery();
            ps.close();
            sql = "UPDATE ACTIVESERVICE set NEXTACTIVESERVICEID=NULL WHERE ACTIVESERVICE_ID=" +
                    "(SELECT NEXTACTIVESERVICEID FROM ACTIVESERVICE WHERE ACTIVESERVICE_ID=?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, activeServiceId);
            ps.executeQuery();
            ps.close();
            sql = "UPDATE ACTIVESERVICE SET TDATE=CURRENT_TIMESTAMP WHERE ACTIVESERVICE_ID=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, activeServiceId);
            ps.executeUpdate();
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
    public void cancelChangingTariff(int activeServiceId) {
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            String sql = "DELETE  FROM ACTIVESERVICE WHERE (ACTIVESERVICE_ID=" +
                    "(SELECT NEXTACTIVESERVICEID FROM ACTIVESERVICE WHERE ACTIVESERVICE_ID=?))or (ACTIVESERVICE_ID=?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, activeServiceId);
            ps.setInt(2,activeServiceId);
            ps.executeQuery();
            ps.close();
            deleteNextActiveServiceId(activeServiceId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ActiveService> getAllActiveServices() {
        List<ActiveService> activeServiceList = null;

        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            String sql = "SELECT *FROM ACTIVESERVICE WHERE (STATE!='CANCELLED') AND ((NEXTACTIVESERVICEID IS NULL)or ((SECOND_STATUS='DISCONNECTED')and (TDATE>CURRENT_TIMESTAMP))) ORDER BY ACTIVESERVICE_ID";
            PreparedStatement ps = connection.prepareStatement(sql);
            activeServiceList =getActiveServiceListByParams(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activeServiceList;
    }

    @Override
    public ActiveService getActiveServiceById(int activeServiceId) {
        ActiveService activeService = null;
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            String sql = "SELECT *FROM ACTIVESERVICE WHERE ACTIVESERVICE_ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, activeServiceId);
            List<ActiveService> activeServiceList=getActiveServiceListByParams(ps);
            if(activeServiceList.size()>0) {
                activeService = activeServiceList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activeService;
    }

    @Override
    public void storeActiveServices(List<ActiveService> activeServicesList) throws Exception {
        try {
            System.out.println(activeServicesList.get(0).getId()+" "+activeServicesList.get(0).getServiceId()+" "+activeServicesList.get(0).getUserId()+" "+activeServicesList.get(0).getState());
            System.out.println("in store");
            PreparedStatement ps = null;
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            for (int i = 0; i < activeServicesList.size(); i++) {
                String mergeSql = "MERGE INTO ACTIVESERVICE A USING("
                        + "SELECT *FROM dual)"
                        + " ON((select count(*) from ACTIVESERVICE where ACTIVESERVICE_ID=?)!=0)"
                        + " WHEN MATCHED THEN "
                        + " UPDATE SET USER_ID=?, SERVICE_ID=?, FIRST_STATUS=?, "
                        + "SECOND_STATUS=?, TDATE=?,VERSION=?,state=? WHERE ACTIVESERVICE_ID = ? "
                        + " WHEN NOT MATCHED THEN "
                        + "INSERT (activeservice_id,user_id,service_id,FIRST_status,SECOND_status,tdate,version,state) " +
                        " VALUES(?,?,?,?,?,?,?,?)";
                ps = connection.prepareStatement(mergeSql);
                ps.setInt(1, activeServicesList.get(i).getId());
                ps.setInt(2, activeServicesList.get(i).getUserId());
                ps.setInt(3, activeServicesList.get(i).getServiceId());
                ps.setString(4, activeServicesList.get(i).getFirstStatus().toString());
                if (activeServicesList.get(i).getSecondStatus() != null) {
                    ps.setString(5, activeServicesList.get(i).getSecondStatus().toString());
                } else {
                    ps.setString(5, " ");
                }
                Timestamp timestamp = null;
                if (activeServicesList.get(i).getDate() != null) {
                    timestamp = new Timestamp(activeServicesList.get(i).getDate().getTime());
                }
                ps.setTimestamp(6, timestamp);
                ps.setInt(7, activeServicesList.get(i).getVersion());
                ps.setString(8, activeServicesList.get(i).getState().toString());
                ps.setInt(9, activeServicesList.get(i).getId());
                ps.setInt(10, activeServicesList.get(i).getId());
                ps.setInt(11, activeServicesList.get(i).getUserId());
                ps.setInt(12, activeServicesList.get(i).getServiceId());
                ps.setString(13, activeServicesList.get(i).getFirstStatus().toString());
                if (activeServicesList.get(i).getSecondStatus() != null)
                    ps.setString(14, activeServicesList.get(i).getSecondStatus().toString());
                else ps.setString(14, " ");
                ps.setTimestamp(15, timestamp);
                ps.setInt(16, activeServicesList.get(i).getVersion());
                ps.setString(17, activeServicesList.get(i).getState().toString());
                System.out.println("me be here now");
                ps.executeQuery();
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Exception occured!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            System.out.println(ex.getMessage());
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
            throw new Exception();
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
    public void deleteActiveServicesByUserId(int userId) {
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            String sql = "DELETE FROM ACTIVESERVICE WHERE USER_ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
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
    public List<String> getHistoryById(int activeServiceId) {
        List<String> messageList = null;
        try {
            messageList = new ArrayList<String>();
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            String sql = "SELECT MESSAGE FROM History WHERE (ACTIVESERVICE_ID=?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, activeServiceId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                messageList.add(rs.getString("message"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messageList;
    }

    public void setNextId(int currentId, int newId) {
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            String sql = " UPDATE ACTIVESERVICE set nextactiveserviceid=? where ACTIVESERVICE_ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, newId);
            ps.setInt(2, currentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<ActiveService> getActiveServicesHistoryByUserId(int userId, int serviceId) {
        List<ActiveService> activeServiceList = null;
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            String sql = "SELECT *FROM ACTIVESERVICE WHERE (USER_ID=?) and (SERVICE_ID IN(SELECT SERVICE_ID FROM SERVICE WHERE SERVICE_TYPE=(SELECT SERVICE_TYPE FROM SERVICE WHERE SERVICE_ID=?))) ORDER BY TDATE,NEXTACTIVESERVICEID";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, serviceId);
            activeServiceList=getActiveServiceListByParams(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activeServiceList;
    }

    @Override
    public void changeNewTariffDate(int activeServiceId, Date date) {
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            String sql = "UPDATE  ACTIVESERVICE SET TDATE=? WHERE ACTIVESERVICE_ID IN ((SELECT ACTIVESERVICE_ID FROM ACTIVESERVICE WHERE ACTIVESERVICE_ID=?),(SELECT NEXTACTIVESERVICEID FROM ACTIVESERVICE WHERE ACTIVESERVICE_ID=?))";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, new Timestamp(date.getTime()));
            ps.setInt(2, activeServiceId);
            ps.setInt(3, activeServiceId);
            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<ActiveService> getActiveServiceListByParams(PreparedStatement ps) {
        List<ActiveService> activeServiceList = null;
        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
            activeServiceList = new ArrayList<ActiveService>();
            while (rs.next()) {
                ActiveService activeService = new ActiveService();
                activeService.setUserId(rs.getInt("USER_ID"));
                activeService.setServiceId(rs.getInt("SERVICE_ID"));
                activeService.setId(rs.getInt("ACTIVESERVICE_ID"));
                activeService.setVersion(rs.getInt("VERSION"));
                String currentStatus = rs.getString("FIRST_STATUS");
                switch (currentStatus) {
                    case "ACTIVE":
                        activeService.setFirstStatus(ActiveServiceStatus.ACTIVE);
                        break;
                    case "PLANNED":
                        activeService.setFirstStatus(ActiveServiceStatus.PLANNED);
                        break;
                    case "SUSPENDED":
                        activeService.setFirstStatus(ActiveServiceStatus.SUSPENDED);
                        break;
                    case "DISCONNECTED":
                        activeService.setFirstStatus(ActiveServiceStatus.DISCONNECTED);
                        break;
                }
                String newStatus = rs.getString("SECOND_STATUS");
                if (newStatus == null)
                    activeService.setSecondStatus(null);
                else
                    switch (newStatus) {
                        case "ACTIVE":
                            activeService.setSecondStatus(ActiveServiceStatus.ACTIVE);
                            break;
                        case "PLANNED":
                            activeService.setSecondStatus(ActiveServiceStatus.PLANNED);
                            break;
                        case "SUSPENDED":
                            activeService.setSecondStatus(ActiveServiceStatus.SUSPENDED);
                            break;
                        case "DISCONNECTED":
                            activeService.setSecondStatus(ActiveServiceStatus.DISCONNECTED);
                            break;
                        case " ":
                            activeService.setSecondStatus(null);
                            break;
                    }
                if (rs.getTimestamp("TDATE") != null) {
                    activeService.setDate(new Date(rs.getTimestamp("TDATE").getTime()));
                } else {
                    activeService.setDate(null);
                }
                String state = rs.getString("state");
                switch (state) {
                    case "READY":
                        activeService.setState(ActiveServiceState.READY);
                        break;
                    case "NOT_READY":
                        activeService.setState(ActiveServiceState.NOT_READY);
                        break;
                    case "CANCELLED":
                        activeService.setState(ActiveServiceState.CANCELLED);
                        break;
                }
                if (rs.getInt("NEXTACTIVESERVICEID") != 0) {
                    activeService.setNextActiveServiceId(rs.getInt("NEXTACTIVESERVICEID"));
                }
                activeServiceList.add(activeService);
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
        return activeServiceList;

    }
    public void deleteNextActiveServiceId(int nextId){
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            String sql = "UPDATE ACTIVESERVICE set NEXTACTIVESERVICEID=NULL WHERE NEXTACTIVESERVICEID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,nextId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ActiveService getPreviousActiveService(int activeServiceId){
        ActiveService activeService = null;
        try {
            connection = DBConnection.getInstance().getDataSourse().getConnection();
            String sql = "SELECT *FROM ACTIVESERVICE WHERE NEXTACTIVESERVICEID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, activeServiceId);
            List<ActiveService> activeServiceList=getActiveServiceListByParams(ps);
            if(activeServiceList.size()>0){
            activeService=activeServiceList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activeService;

    }
}
