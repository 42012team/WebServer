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
    private ActiveServiceStatus currentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status")
    private ActiveServiceStatus newStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tdate")
    private Date date;

    @Column(name = "version")
    private int version;
    private int nextActiveServiceId;

    public ActiveService(int id, int serviceId, int userId, ActiveServiceStatus currentStatus, ActiveServiceStatus newStatus, Date date) {
        this.id = id;
        this.serviceId = serviceId;
        this.userId = userId;
        this.date = date;
        this.currentStatus = currentStatus;
        this.newStatus = newStatus;
    }

    public ActiveService() {
        id = 0;

    }

    public void setCurrentStatus(ActiveServiceStatus status) {
        currentStatus = status;
    }

    public ActiveServiceStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setNewStatus(ActiveServiceStatus status) {
        newStatus = status;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public ActiveServiceStatus getNewStatus() {
        return newStatus;
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
