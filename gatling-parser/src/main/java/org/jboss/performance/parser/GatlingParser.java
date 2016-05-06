package org.jboss.performance.parser;

import org.jboss.performance.parser.json.JsonFileReader;

import javax.json.JsonObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class GatlingParser {

    private static String[][] metrics = {
            {"maxResponseTime", "total", "Max"},
            {"meanResponseTime", "total", "Mean"},
            {"standardDeviation", "total", "SD"},
            {"percentiles3", "total", "95th pct"},
            {"percentiles4", "total", "99th pct"},
            {"group4", "count", "Errors"},
            {"meanNumberOfRequestsPerSecond", "total", "Mean req/s"}
    };

    public static void main(String[] args) {

        Map<Integer, Map<String, Integer>> collatedResults = new HashMap<>();

        URL url = null;

        for(int counter = 0 ; counter < args.length; counter++){
            Integer rate = Integer.parseInt(args[counter]);
            String inputFile =  args[++counter];

            try {
                url = new URL(inputFile);

                JsonObject jsonObj = JsonFileReader.readJson(url);

                Map<String, Integer> extractedResults = extractResults(jsonObj);

                collatedResults.put(rate,extractedResults);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        printCollatedResults(collatedResults);
    }

    private static void printResults(Map<String, Integer> extractedResults) {
        for(Map.Entry<String, Integer> entrySet: extractedResults.entrySet()){
            System.out.println(entrySet.getKey() + ": " + entrySet.getValue().toString());
        }
    }

    private static void printCollatedResults(Map<Integer, Map<String, Integer>> collatedResults) {

        String line = "\"rps\"";

        for(String[] headers: metrics){

            line = line + ", \"" + headers[2] + "\"";
        }

        System.out.println(line);

        for(Map.Entry<Integer, Map<String, Integer>> entrySet: collatedResults.entrySet()){
            line = "\"" + entrySet.getKey().toString() + "\"";

            for(String[] headers: metrics){

                line = line + ", \"" + entrySet.getValue().get(headers[2]) + "\"";
            }

            System.out.println(line);

        }
    }

    private static Map<String, Integer> extractResults(JsonObject jsonObj){

        Map<String, Integer> extractedResults = new HashMap<>();
        for(String[] metric: metrics){
            JsonObject results = jsonObj.getJsonObject(metric[0]);
            if (results != null) {
                extractedResults.put(metric[2], results.getInt(metric[1]));
            }
        }

        return extractedResults;

    }
}
