package sn.ept.dic2git.stoch;


import sn.ept.dic2git.stoch.entities.Agent;
import umontreal.ssj.randvar.ExponentialGen;
import umontreal.ssj.randvar.LognormalGen;
import umontreal.ssj.randvar.RandomVariateGen;
import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.simevents.*;

import sn.ept.dic2git.stoch.entities.Tuple;
import sn.ept.dic2git.stoch.entities.Tuple1;
import umontreal.ssj.stat.Tally;

import java.util.*;

public class Simulation {

    static final double HOUR = 3600;
    static final double MINUTE = 60;
    static final int NOMBRE_AGENTS = 17;
    static final int NOMBRE_SERVICES = 6;


    List<Agent> agentList = new ArrayList<>();
    List<Integer> servicesPeriod = new ArrayList<>();


    double openingTime = 8 * HOUR; //temps d'ouverture
    int numPeriods = 16; //nombre de periodes

    //garder en tête que je dois utiliser les agents à chaque periode

    Event[] nextArrival = new Arrival[NOMBRE_SERVICES];


    List<Call> queue = new ArrayList<>();

    RandomVariateGen[] genService; //chaque agent à son générateur

    RandomVariateGen[] genArr; //chaque type d'appel à son générateur
    Tally statWaits = new Tally ("Average waiting time per customer");
    Tally waitTimesUnder60s = new Tally("Wait times under 60 seconds");
    Map<String,List<Tuple1>> logParams;
    Map<String,List<Tuple>> lambdas;

    public Simulation(String logParamsFile,String lambdasFile){
        agentList.add(new Agent(1,1105,Utils.agent1105Services,Utils.agent1105Availability));
        agentList.add(new Agent(2,6947,Utils.agent6947Services,Utils.agent6947Availability));
        agentList.add(new Agent(3,6989,Utils.agent6989Services,Utils.agent6989Availability));
        agentList.add(new Agent(4,7440,Utils.agent7440Services,Utils.agent7440Availability));
        agentList.add(new Agent(5,8514,Utils.agent8514Services,Utils.agent8514Availability));
        agentList.add(new Agent(6,9427,Utils.agent9427Services,Utils.agent9427Availability));
        agentList.add(new Agent(7,9514,Utils.agent9514Services,Utils.agent9514Availability));
        agentList.add(new Agent(8,9687,Utils.agent9687Services,Utils.agent9687Availability));
        agentList.add(new Agent(9,9828,Utils.agent9828Services,Utils.agent9828Availability));
        agentList.add(new Agent(10,9515,Utils.agent9515Services,Utils.agent9515Availability));
        agentList.add(new Agent(11,6926,Utils.agent6926Services,Utils.agent6926Availability));
        agentList.add(new Agent(12,7030,Utils.agent7030Services,Utils.agent7030Availability));
        agentList.add(new Agent(13,1049,Utils.agent1049Services,Utils.agent1049Availability));
        agentList.add(new Agent(14,8374,Utils.agent8374Services,Utils.agent8374Availability));
        agentList.add(new Agent(15,8749,Utils.agent8749Services,Utils.agent8749Availability));
        agentList.add(new Agent(16,7002,Utils.agent7002Services,Utils.agent7002Availability));
        agentList.add(new Agent(17,9113,Utils.agent9113Services,Utils.agent9113Availability));
        genService = new RandomVariateGen[NOMBRE_AGENTS];
        for (int agent = 0; agent < NOMBRE_AGENTS; agent++) {
            genService[agent] = new LognormalGen(new MRG32k3a(), 10,0.3); //initialisation (à enlever)
        }
        for (int i = 0; i < NOMBRE_SERVICES;i++){
            nextArrival[i] = new Arrival(i);
        }

        genArr = new RandomVariateGen[NOMBRE_SERVICES];
        for (int serv = 0; serv < NOMBRE_SERVICES; serv++) {
            genArr[serv] = new ExponentialGen(new MRG32k3a(), 3); //initialisation (a enlever)
        }
        logParams = Utils.ReadLogParams(logParamsFile);
        lambdas = Utils.ReadLambdas(lambdasFile);

    }
    class  Call {
        double serviceTime;
        double arrivalTime;
        int type;

        public Call(int type){

            this.type = type;

            for (Agent agent : agentList){
                //System.out.println("last served : " + agent.getLast_served());
                //System.out.println("available : " + agent.getAvailable() + " ; serviceType : " + agent.hasService(type) + " work on this period : " + agent.getWorking()  + " agent : " + Agent.servedLast(agentList));

                if(agent.getAvailable() && agent.hasService(type) && agent.getWorking() && agent == Agent.servedLast(agentList)){
                    System.out.println("servi par : " + agent.agent_number + " a " + Sim.time());
                    double mu = 0.0;
                    double sigma = 0.0;
                    //System.out.println(agent.agent_number + " available : " + agent.getAvailable());
                    agent.setAvailable(false);
                    //System.out.println(agent.agent_number + " available : " + agent.getAvailable());
                    //anyAgentAvailable = true;
                    int index = agentList.indexOf(agent);
                    //changer genService selon le type de l'appel (attribut type)
                    List<Tuple1> listOfParams =  logParams.get(agent.agent_number.toString());
                    for (Tuple1 tuple : listOfParams){
                        if (Objects.equals(tuple.getServiceType(), Utils.serviceMap().get(type))){
                            mu = tuple.getMu();
                            sigma = tuple.getSigma();
                        }
                    }
                    if (sigma <= 0) {
                        throw new IllegalArgumentException("Sigma must be greater than zero for agent " + agent.agent_number + " and service type " + type);
                    }
                    genService[index] = new LognormalGen(new MRG32k3a(),mu ,sigma);
                    serviceTime = genService[index].nextDouble();
                    //System.out.println("service time : " + serviceTime);
                    System.out.println("service time : " + serviceTime);
                    statWaits.add(0.0);
                    waitTimesUnder60s.add(0.0);
                    new CallCompletion(agent).schedule(serviceTime);
                    return;
                }
            }
                   System.out.println("ajoutee a la queue : appel " + type );
                   arrivalTime = Sim.time();
                   //System.out.println("Temps d arrivee : " + arrivalTime);
                   queue.add(this); // à revoir
                   //System.out.println("A ajouter dans la file");
                   //System.out.println("ajouter a la queue : " + this.type );
                   //System.out.println("Nombre d elements dans la file : " + queue.size());

              // }
            //System.out.println("booleen de disponibilite : " + anyAgentAvailable);

        }

        public static Call getCall(List<Call> queue) {
            for (Call call :  queue){
                return call;
            }
            return null;
        }
        public void endWait(){
            System.out.println("On retire de la queue : " + this.type + " a " + Sim.time());
            double wait = Sim.time() - arrivalTime;
            //System.out.println("Temps d'attente : " + wait);
                Double last_served = Double.MAX_VALUE;
                Agent agentToServe = null;
                int index;
                for (Agent agent : agentList){
                    if(agent.getLast_served() < last_served && agent.getAvailable() && agent.hasService(type) && agent.getWorking()){
                        last_served = agent.getLast_served();
                        agentToServe = agent;
                    }
                }
                if(agentToServe!=null) {
                    double mu = 0.0;
                    double sigma = 0.0;
                    //System.out.println("Est servi par " + agentToServe.agentId);
                    List<Tuple1> listOfParams =  logParams.get(agentToServe.agent_number.toString());
                    for (Tuple1 tuple : listOfParams){
                        if (Objects.equals(tuple.getServiceType(), Utils.serviceMap().get(type))){
                            mu = tuple.getMu();
                            sigma = tuple.getSigma();

                        }
                    }
                    index = agentList.indexOf(agentToServe);
                    genService[index] = new LognormalGen(new MRG32k3a(),mu ,sigma);
                    serviceTime = genService[index].nextDouble();
                    System.out.println("service time : " + serviceTime);
                    agentToServe.setAvailable(false);
                    new CallCompletion(agentToServe).schedule(serviceTime);
                    System.out.println("wait : " + wait);
                    statWaits.add(wait);
                    if(wait < 60)
                        waitTimesUnder60s.add(wait);
                }

                //collect stats
            //System.out.println("Adding stats");


        }

    }

    class NextPeriod extends Event{
        int j; //number of the period
        public NextPeriod(int period){j=period;};
        public void actions(){
            if(j<numPeriods){
                System.out.println("period : " + j);
                //mettre à jour les agents
                for (Agent agent : agentList){
                    if (agent.getAvailability().contains(j+1)){
                        System.out.println("agent dispo : " + agent.agentId);
                        agent.setWorking(true);
                    }
                    else{
                        agent.setWorking(false);
                    }
                }
                //System.out.println("intitialisation des agents... fait");
                //changer les paramètres
                //voir pour chaque service, si pour la periode concernee y a un element (dans le map des lambda)
                for (int serv = 0; serv < NOMBRE_SERVICES; serv++) {
                    String service = Utils.serviceMap().get(serv);
                    int thePeriod = j+1;
                    List<Tuple> arrivals = lambdas.get(Integer.toString(thePeriod));
                    for (Tuple tuple : arrivals){
                        if(Objects.equals(tuple.getServiceType(), service)){
                            genArr[serv] = new ExponentialGen(new MRG32k3a(), tuple.getArrivalRate());
                            System.out.println(genArr[serv]);
                        }
                    }
                }




                if(j==0){
                    programArrivals();
                } else {
                    checkQueue();
                    //System.out.println("Verification de la queue...");
                    //reschedule
                    for (Integer servi : servicesPeriod){
                        System.out.println("canceling : " + servi);
                        nextArrival[servi].cancel();
                    }
                    servicesPeriod = new ArrayList<>();

                    programArrivals();

                }
                new NextPeriod(j+1).schedule(30 * MINUTE);
                System.out.println("taille queue : " + queue.size());

            } else {
                    nextArrival[0].cancel();
                    nextArrival[1].cancel();
            }

        }

        private void programArrivals() {
            for (int serv = 0 ; serv < NOMBRE_SERVICES ; serv ++) {
                String service = Utils.serviceMap().get(serv);
                int thePeriod = j+1;
                List<Tuple> arrivals = lambdas.get(Integer.toString(thePeriod));
                for (Tuple tuple : arrivals){
                    if(Objects.equals(tuple.getServiceType(), service)){
                        //nextArrival[serv] = new Arrival(serv);
                        //System.out.println("arrivee " + serv + " a venir" );
                        nextArrival[serv].schedule(genArr[serv].nextDouble());
                        servicesPeriod.add(serv);
                    }
                }
               // nextArrival[serv].reschedule((nextArrival[serv].time() - Sim.time()) * 0.8); //ajuster apres
            }
        }
    }


    class Arrival extends Event {
        int service;

        public Arrival(int service){
            this.service=service;
        }
        public void actions(){
            nextArrival[service].schedule(genArr[service].nextDouble());
            System.out.println("arrivee " + service + " a " + Sim.time());
            new Call(service);
        }
    }

    class CallCompletion extends Event {
        public Agent agent;

        public CallCompletion(Agent agent){
            this.agent = agent;
        }
        public void actions(){
            agent.setLast_served(Sim.time());
            agent.setAvailable(true);
            System.out.println(agent.agent_number + " a fini de servir " + " a " + Sim.time());
            checkQueue();
        }
    }

    public void checkQueue(){
        Call theCall = Call.getCall(queue);
        while (theCall != null && Agent.availableAgent(agentList,theCall.type)){
            System.out.println("On prend de la queue " + theCall.type);
            theCall.endWait();
            queue.remove(theCall);
            theCall = Call.getCall(queue);
            System.out.println("Etat de la queue apres supp : " + queue.size());
        }
    }

    public void simulateOneDay(){
        Sim.init();
        statWaits.init();
        waitTimesUnder60s.init();
        new NextPeriod(0).schedule(0);
        System.out.println("la simulation va commencer...");
        Sim.start();
        //System.out.println("debut de la simulation");
        System.out.println(statWaits.report());
        System.out.println(waitTimesUnder60s.report());
    }
    public static void main(String[] args) {
        Simulation mySimulation = new Simulation("/Users/sambastraore/Desktop/logparams1.csv","/Users/sambastraore/Desktop/lambdas.csv");
        mySimulation.simulateOneDay();
        System.out.println("Simulation terminée avec succès !");
    }
    }
