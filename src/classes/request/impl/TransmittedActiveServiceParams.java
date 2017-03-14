package classes.request.impl;

import classes.model.ActiveService;
import classes.model.ActiveServiceState;
import classes.model.ActiveServiceStatus;
import classes.request.RequestDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TransmittedActiveServiceParams implements RequestDTO, Serializable {

    private int serviceId;
    private int userId;
    private int activeServiceId;
    private int oldActiveServiceId;
    private ActiveServiceStatus firstStatus;
    private ActiveServiceStatus secondStatus;
    private Date date;
    private String requestType;
    private int version;
    private long unlockingTime;
    private ActiveServiceState state;
    private int nextActiveServiceId;
    private List<ActiveService> activeServicesList;

    private TransmittedActiveServiceParams() {
    }

    public static TransmittedActiveServiceParams create() {
        return new TransmittedActiveServiceParams();
    }

    public TransmittedActiveServiceParams withActiveServiceId(int activeServiceId) {
        this.activeServiceId = activeServiceId;
        return this;
    }

    public TransmittedActiveServiceParams withOldActiveServiceId(int oldActiveServiceId) {
        this.oldActiveServiceId = oldActiveServiceId;
        return this;
    }

    public TransmittedActiveServiceParams withServiceId(int serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public TransmittedActiveServiceParams withUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public TransmittedActiveServiceParams withFirstStatus(ActiveServiceStatus firstStatus) {
        this.firstStatus = firstStatus;
        return this;
    }

    public TransmittedActiveServiceParams withSecondStatus(ActiveServiceStatus secondStatus) {
        this.secondStatus = secondStatus;
        return this;
    }

    public TransmittedActiveServiceParams withDate(Date date) {
        this.date = date;
        return this;
    }

    public TransmittedActiveServiceParams withRequestType(String type) {
        this.requestType = type;
        return this;
    }

    public TransmittedActiveServiceParams withVersion(int version) {
        this.version = version;
        return this;
    }
    public TransmittedActiveServiceParams withNextActiveServiceId(int nextActiveServiceId) {
        this.activeServiceId = activeServiceId;
        return this;
    }

    public TransmittedActiveServiceParams withUnlockingTime(long unlockingTime) {
        this.unlockingTime = unlockingTime;
        return this;
    }
    public TransmittedActiveServiceParams withState(ActiveServiceState state){
        this.state=state;
        return this;
    }

    public TransmittedActiveServiceParams withActiveServiceList(List<ActiveService> activeServiceList) {
        this.activeServicesList = activeServiceList;
        return this;
    }

    public ActiveServiceStatus getFirstStatus() {
        return firstStatus;
    }

    public ActiveServiceStatus getSecondStatus() {
        return secondStatus;
    }

    public int getVersion() {
        return version;
    }

    public int getServiceId() {
        return serviceId;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return activeServiceId;
    }

    public int getOldActiveServiceId() {
        return oldActiveServiceId;
    }
    public  int getNextActiveServiceId(){return  nextActiveServiceId;}

    public Date getDate() {
        return date;
    }
    public ActiveServiceState getState(){
        return state;
    }

    public List<ActiveService> getActiveServicesList() {
        return activeServicesList;
    }

    @Override
    public String getRequestType() {
        return requestType;
    }

    public long getUnlockingTime() {
        return unlockingTime;
    }


}