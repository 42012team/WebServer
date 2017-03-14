package classes.model;

import java.util.Date;

public class ActiveServiceParams {
    private int activeServiceId;
    private int serviceId;
    private int userId;
    private ActiveServiceStatus firstStatus;
    private ActiveServiceStatus secondStatus;
    private Date date;
    private int version;
    private int oldActiveServiceId;
    private int nextActiveServiceId;
    private ActiveServiceState state;

    private ActiveServiceParams() {

    }

    public static ActiveServiceParams create() {
        return new ActiveServiceParams();
    }

    public ActiveServiceParams withServiceId(int serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public ActiveServiceParams withUserId(int userId) {
        this.userId = userId;
        return this;
    }
    public ActiveServiceParams withActiveServiceId(int activeServiceId) {
        this.activeServiceId = activeServiceId;
        return this;
    }

    public ActiveServiceParams withOldActiveServiceId(int oldActiveServiceId) {
        this.oldActiveServiceId = oldActiveServiceId;
        return this;
    }

    public ActiveServiceParams withFirstStatus(ActiveServiceStatus firstStatus) {
        this.firstStatus = firstStatus;
        return this;
    }

    public ActiveServiceParams withSecondStatus(ActiveServiceStatus secondStatus) {
        this.secondStatus = secondStatus;
        return this;
    }

    public ActiveServiceParams withDate(Date date) {
        this.date = date;
        return this;
    }

    public ActiveServiceParams withVersion(int version) {
        this.version = version;
        return this;
    }
    public ActiveServiceParams withState(ActiveServiceState state) {
        this.state = state;
        return this;
    }
    public ActiveServiceParams withNextActiveServiceId(int nextActiveServiceId) {
        this.nextActiveServiceId = nextActiveServiceId;
        return this;
    }
    public ActiveServiceStatus getFirstStatus() {
        return firstStatus;
    }

    public ActiveServiceStatus getSecondStatus() {
        return secondStatus;
    }

    public Date getDate() {
        return date;
    }

    public int getServiceId() {
        return serviceId;
    }

    public int getUserId() {
        return userId;
    }

    public int getVersion() {
        return version;
    }
    public ActiveServiceState getState(){
        return state;
    }
   public int getNextActiveServiceId(){return nextActiveServiceId;}
    public int getOldActiveServiceId() {return oldActiveServiceId;}

}
