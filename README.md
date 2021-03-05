# Spring Boot with Kafka Producer Consumer Test

This Project covers how to use Spring Boot with Spring Kafka to Consume String message
## Start Zookeeper
- `bin/zookeeper-server-start.bat config/zookeeper.properties`

## Start Kafka Server
- `bin/kafka-server-start.bat config/server.properties`

## Running springboot:
run KafkaTestApplication

## Publish message via WebService
- `http://localhost:8085`

Jawaban 
nomor 1;
select u.id, u.username ,u2.username as parent from user u left join user u2 on u.parent =u2.id order by u.id asc;

nomor 3:
    func findFirstStringInBracket(str string) string {
        var res = ""
        if (len(str) > 0) {
            indexFirstBracketFound := strings.Index(str,"(")
            if indexFirstBracketFound >= 0 {
                runes := []rune(str)
                wordsAfterFirstBracket := string(runes[indexFirstBracketFound:len(str)])
                indexClosingBracketFound := strings.Index(wordsAfterFirstBracket,")")
                if indexClosingBracketFound >= 0 {
                    runes := []rune(wordsAfterFirstBracket)
                    res :=string(runes[1:indexClosingBracketFound-1])                     
                }
            }
        }
        return res
    }

nomor 4:
import java.util.*;

public class anagram {
    public static void main(String[] args) {
        System.out.println(cluster(Arrays.asList("kita", "atik", "tika", "aku", "kia", "makan", "kua")));
    }

    static LinkedList<List<String>> cluster(List<String> words) {
        LinkedHashMap<String, List<String>> allClusters = new LinkedHashMap<>();
        LinkedList<List<String>> result = new LinkedList<>();
        for (String word : words) {
            String sortedWord = sortWord(word);
            List<String> cluster = allClusters.get(sortedWord);
            if (cluster == null) {
                cluster = new ArrayList<>();
            }
            cluster.add(word);
            allClusters.put(sortedWord, cluster);
        }
        for (List<String> value : allClusters.values()){
            result.add(value);
        }
        return result;
    }

    static String sortWord(String w) {
        int freq[] = new int[256];
        for (char c : w.toCharArray()) {
            freq[c]++;
        }
        StringBuilder sortedWord = new StringBuilder();
        for (int i = 0; i < freq.length; ++i) {
            for (int j = 0; j < freq[i]; ++j) {
                sortedWord.append((char)i);
            }
        }
        return sortedWord.toString();
    }
}
