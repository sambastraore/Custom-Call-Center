package sn.ept.dic2git.stoch;

import sn.ept.dic2git.stoch.entities.Tuple;
import sn.ept.dic2git.stoch.entities.Tuple1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    public static List<Integer> agent1105Availability = new ArrayList<>(List.of(1,2,5,6,7,8));
    public static List<Integer> agent1105Services = new ArrayList<>(List.of(0,1,3));
    public static List<Integer> agent6947Availability = new ArrayList<>(List.of(1,2,3,4));
    public static List<Integer> agent6947Services = new ArrayList<>(List.of(4,0,1,3));
    public static List<Integer> agent6989Availability = new ArrayList<>(List.of(1,2,3,4));
    public static List<Integer> agent6989Services = new ArrayList<>(List.of(0,1,3));
    public static List<Integer> agent7440Availability = new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9,11,12));
    public static List<Integer> agent7440Services = new ArrayList<>(List.of(0,1,3,2));
    public static List<Integer> agent8514Availability = new ArrayList<>(List.of(1,2,3,4,5,6));
    public static List<Integer> agent8514Services = new ArrayList<>(List.of(0,1,3,2));
    public static List<Integer> agent9427Availability = new ArrayList<>(List.of(1,2,5,6,7,8,9,10,11,12,15,16));
    public static List<Integer> agent9427Services = new ArrayList<>(List.of(4,0,1,2,3));
    public static List<Integer> agent9514Availability = new ArrayList<>(List.of(1,2,3,4,5,6,7,8));
    public static List<Integer> agent9514Services = new ArrayList<>(List.of(0,1,2,3));
    public static List<Integer> agent9687Availability = new ArrayList<>(List.of(1,2,7,8,9,10,11));
    public static List<Integer> agent9687Services = new ArrayList<>(List.of(0,1,2));
    public static List<Integer> agent9828Availability = new ArrayList<>(List.of(1,2,3,4,5,6,9,10,11,12,13,14));
    public static List<Integer> agent9828Services = new ArrayList<>(List.of(4,0,1,2,5,3));
    public static List<Integer> agent9515Availability = new ArrayList<>(List.of(3,4,5,6,7,8,9,10));
    public static List<Integer> agent9515Services = new ArrayList<>(List.of(0, 1, 2,3));
    public static List<Integer> agent6926Availability = new ArrayList<>(List.of(5,6,7,8));
    public static List<Integer> agent6926Services = new ArrayList<>(List.of(4,0,1,3));
    public static List<Integer> agent7030Availability = new ArrayList<>(List.of(5,6));
    public static List<Integer> agent7030Services = new ArrayList<>(List.of(0,1,3));
    public static List<Integer> agent1049Availability = new ArrayList<>(List.of(9,10,11,12,13,14,15,16));
    public static List<Integer> agent1049Services = new ArrayList<>(List.of(0,1,3,2));
    public static List<Integer> agent8374Availability = new ArrayList<>(List.of(9,10,11,12));
    public static List<Integer> agent8374Services = new ArrayList<>(List.of(0,1,3,2));
    public static List<Integer> agent8749Availability = new ArrayList<>(List.of(9,10,11,12));
    public static List<Integer> agent8749Services = new ArrayList<>(List.of(0,1,3));
    public static List<Integer> agent7002Availability = new ArrayList<>(List.of(11,12,13,14,15,16));
    public static List<Integer> agent7002Services = new ArrayList<>(List.of(0,1,3,4));
    public static List<Integer> agent9113Availability = new ArrayList<>(List.of(11,12,13,14,15,16));
    public static List<Integer> agent9113Services = new ArrayList<>(List.of(0,1,2));
    public static List<Integer> typesServices = new ArrayList<>(List.of(30172,30175,30179,30560,30066,30518));
    //30172 is 1
    //30175 is 2
    //30179 is 3
    //30560 is 4
    //30066 is 5
    //30518 is 6


    public static Map<Integer,Integer> returnAgentMap(){
        Map<Integer,Integer> agentMap = new HashMap<>();
        agentMap.put(1, 1105);
        agentMap.put(2, 6947);
        agentMap.put(3, 6989);
        agentMap.put(4, 7440);
        agentMap.put(5, 8514);
        agentMap.put(6, 9427);
        agentMap.put(7, 9514);
        agentMap.put(8, 9687);
        agentMap.put(9, 9828);
        agentMap.put(10, 9515);
        agentMap.put(11, 6926);
        agentMap.put(12, 7030);
        agentMap.put(13, 1049);
        agentMap.put(14, 8374);
        agentMap.put(15, 8749);
        agentMap.put(16, 7002);
        agentMap.put(17, 9113);

        return agentMap;
    }


    public static Map<String,List<Tuple>> ReadLambdas (String csvFile){
        String line;
        String csvSplitBy = ","; // Délimiteur CSV
        Map<String, List<Tuple>> resultMap = new HashMap<>();
        Map<String, Integer> timeRangeMap = new HashMap<>();
        timeRangeMap.put("09:00", 1);
        timeRangeMap.put("09:30", 2);
        timeRangeMap.put("10:00", 3);
        timeRangeMap.put("10:30", 4);
        timeRangeMap.put("11:00", 5);
        timeRangeMap.put("11:30", 6);
        timeRangeMap.put("12:00", 7);
        timeRangeMap.put("12:30", 8);
        timeRangeMap.put("13:00", 9);
        timeRangeMap.put("13:30", 10);
        timeRangeMap.put("14:00", 11);
        timeRangeMap.put("14:30", 12);
        timeRangeMap.put("15:00", 13);
        timeRangeMap.put("15:30", 14);
        timeRangeMap.put("16:00", 15);
        timeRangeMap.put("16:30", 16);

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String headerLine = br.readLine();
            if (headerLine != null) {
                String[] headers = headerLine.split(csvSplitBy);
            }

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(csvSplitBy);
                String timeRange = columns[0];
                String serviceType = columns[1];
                double arrivalRate = Double.parseDouble(columns[3]);

                String timeRangeHour = timeRange.split(" ")[1].substring(0, 5);
                //System.out.println(timeRangeHour);
                Integer timeRangeNumber = timeRangeMap.get(timeRangeHour);
                if (timeRangeNumber != null) {
                    String timeRangeNumberStr = timeRangeNumber.toString();
                    Tuple tuple = new Tuple(arrivalRate, serviceType);

                    resultMap.computeIfAbsent(timeRangeNumberStr, k -> new ArrayList<>()).add(tuple);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, List<Tuple>> entry : resultMap.entrySet()) {
            System.out.println("Time Range Number: " + entry.getKey());
            for (Tuple tuple : entry.getValue()) {
                System.out.println("  Service Type: " + tuple.getServiceType() + ", Arrival Rate: " + tuple.getArrivalRate());
            }
        }
        return resultMap;
    }

    public static Map<String,List<Tuple1>> ReadLogParams(String csvFile){ //la liste des logparams est incomplete
        String line;
        String csvSplitBy = ","; // Délimiteur CSV
        Map<String, List<Tuple1>> resultMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String headerLine = br.readLine();
            if (headerLine != null) {
                String[] headers = headerLine.split(csvSplitBy);
            }

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(csvSplitBy);
                System.out.println(columns.length);
                String agentNumber = columns[0];
                String serviceType = columns[1];
                double mean = Double.parseDouble(columns[2]);
                double std = Double.parseDouble(columns[3]);

                Tuple1 tuple1 = new Tuple1(mean,std, serviceType);

                resultMap.computeIfAbsent(agentNumber, k -> new ArrayList<>()).add(tuple1);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, List<Tuple1>> entry : resultMap.entrySet()) {
            System.out.println("Agent Number: " + entry.getKey());
            for (Tuple1 tuple1 : entry.getValue()) {
                System.out.println("  Service Type: " + tuple1.getServiceType() + ", Mean: " + tuple1.getMean_service_time() + "Std: " + tuple1.getStd_service_time());
            }
        }
        return resultMap;
    }
}
