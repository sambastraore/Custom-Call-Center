package sn.ept.dic2git.stoch.entities;

import java.util.ArrayList;
import java.util.List;

public class Agent {
    public Integer agent_number;
    public List<Integer> services;
    public List<Integer> availability;
    public Double last_served;
    public Boolean available=true;

    public Boolean isWorking=false;

    public Integer agentId;


    public Agent(Integer agentId,Integer agent_number, List<Integer> services, List<Integer> availability){
        this.agentId = agentId;
        this.agent_number = agent_number;
        this.services = services;
        this.availability = availability;
        this.setAvailable(true);
        this.last_served = 0.0;
    }

    public void setLast_served(double last_served) {
        this.last_served = last_served;
    }

    public Boolean getWorking() {
        return isWorking;
    }

    public void setWorking(Boolean working) {
        isWorking = working;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getAgent_number() {
        return agent_number;
    }

    public void setAgent_number(Integer agent_number) {
        this.agent_number = agent_number;
    }

    public List<Integer> getServices() {
        return services;
    }

    public void setServices(List<Integer> services) {
        this.services = services;
    }

    public List<Integer> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Integer> availability) {
        this.availability = availability;
    }

    public Double getLast_served() {
        return last_served;
    }

    public void setLast_served(Double last_served) {
        this.last_served = last_served;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public boolean hasService(Integer serviceId) {
        if (services == null) {
            return false;
        }
        return services.contains(serviceId);
    }


    public static Agent servedLast(List<Agent> listAgent){
        Double lastServed = Double.MAX_VALUE;
        Agent theAgent = null;
        for (Agent agent : listAgent) {
            if (agent.getWorking() && agent.getAvailable()) {
                if (agent.getLast_served() < lastServed) {
                    theAgent = agent;
                }
            }
        }
        return theAgent;
    }

    public static Boolean availableAgent (List<Agent> listAgent, Integer type){
        for (Agent agent : listAgent){
            if (agent.getWorking() && agent.getAvailable() && agent.hasService(type)){
                return true;
            }
        }
        return false;
    }


}
