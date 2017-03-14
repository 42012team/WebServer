package classes.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ACTIVESERVICE")
public class ActiveService implements Comparable<ActiveService>, Serializable {
    @Id
    @Column(name = "activeservice_id")
    private int id;

    @Column(name = "service_id")
    private int serviceId;

    @Column(name = "user_id")
    private int userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_status")
    private ActiveServiceStatus firstStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status")
    private ActiveServiceStatus secondStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tdate")
    private Date date;

    @Column(name = "version")
    private int version;

    private int nextActiveServiceId;

    private ActiveServiceState state;
    public ActiveService(int id, int serviceId, int userId, ActiveServiceStatus firstStatus, ActiveServiceStatus secondStatus, Date date, ActiveServiceState state) {
        this.id = id;
        this.serviceId = serviceId;
        this.userId = userId;
        this.date = date;
        this.firstStatus = firstStatus;
        this.secondStatus = secondStatus;
        this.state=state;
    }

    public ActiveService() {
        id = 0;

    }

    public void setFirstStatus(ActiveServiceStatus status) {
        firstStatus = status;
    }

    public ActiveServiceStatus getFirstStatus() {
        return firstStatus;
    }

    public void setSecondStatus(ActiveServiceStatus status) {
        secondStatus = status;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    public void setState(ActiveServiceState state) {
        this.state = state;
    }

    public ActiveServiceStatus getSecondStatus() {
        return secondStatus;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNextActiveServiceId(int id) {
        nextActiveServiceId = id;
    }

    public int getVersion() {
         return version;
    }
    public ActiveServiceState getState(){return state;}

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public int getNextActiveServiceId() {
        return nextActiveServiceId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int compareTo(ActiveService o) {
        return date.compareTo(o.getDate());
    }

}
