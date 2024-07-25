package sn.ept.dic2git.stoch.entities;

public class Client {
    Integer id;
    Integer service_type;

    public Client(Integer id, Integer service_type){
        this.id = id;
        this.service_type = service_type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getService_type() {
        return service_type;
    }

    public void setService_type(Integer service_type) {
        this.service_type = service_type;
    }
}
