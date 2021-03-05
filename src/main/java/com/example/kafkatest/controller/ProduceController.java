package com.example.kafkatest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("kafka")
public class ProduceController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "Kafka_Test";

    @GetMapping("/publish")
    public String get() {
        String file ="src/test/file/test3.txt";
        long MAX_DURATION = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES);
        try {
            BufferedReader reader = new BufferedReader(new FileReader( file));
            String line;
            Map<String, Integer> data = new HashMap<>();
            LinkedHashMap<String, String> result = new LinkedHashMap<>();
            Date previous = new Date();
            int count =0;
            while ((line = reader.readLine()) != null) {
                List<String> getTime = new ArrayList<>(Arrays.asList(line.split("\\|")));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm");
                String dateInString = getTime.get(0);
                Date date = formatter.parse(dateInString);
                long duration = date.getTime() - previous.getTime();
                if (duration >= MAX_DURATION && count > 0) {
                    sendMessage(result);
                    result.clear();
                }
                Map<String, Object> pairKeyPrice = setKeyPrice(line);
                String key = pairKeyPrice.get("key").toString();
                Integer priceStock = Integer.valueOf(pairKeyPrice.get("price").toString());
                String newValue = key + "|High;"+priceStock.toString()+"|low;"+priceStock.toString();
                if(data.get(key)!= null){
                    Integer price = data.get(key);
                    if(Integer.compare(price, priceStock) > 0){
                        newValue = key + "|High;"+price.toString()+"|low;"+priceStock.toString();
                    }else {
                        newValue = key + "|High;"+priceStock.toString()+"|low;"+price.toString();
                    }
                }
                result.put(key, newValue);
                data.put(key, priceStock);
                previous = formatter.parse(dateInString);
                count++;
            }
            if(result.size()>0){
                sendMessage(result);
            }
            reader.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "Published successfully";
    }

    private void sendMessage(LinkedHashMap<String, String> result){
        List<String> data = new ArrayList<>();
        for (Object value : result.values()) {
            data.add(value.toString());
        }
        kafkaTemplate.send(TOPIC, String.join(",", data));
    }

    private Map<String, Object> setKeyPrice(String line){
        Map<String, Object> result = new HashMap<>();
        List<String> splitList = new ArrayList<>(Arrays.asList(line.split(";")));
        List<String> myList = new ArrayList<>(Arrays.asList(splitList.get(0).split("\\|")));
        myList.set(0,myList.get(0).replaceFirst(".{2}$", "00"));
        myList.remove(myList.size()-1);
        String key = String.join("|", myList);
        Integer priceStock =Integer.valueOf(splitList.get(1));
        result.put("key", key);
        result.put("price", priceStock);
        return result;
    }
}
