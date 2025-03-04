package net.engineeringdigest.journalApp.Scheduler;

import net.engineeringdigest.journalApp.Cache.AppCache;
import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Enums.Sentiment;
import net.engineeringdigest.journalApp.Repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.Service.EmailService;
import net.engineeringdigest.journalApp.Service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN") // Runs every Sunday at 9 AM
    public void fetchUserAndSendSaMail(){
        List<User> users = userRepository.getUserForSA();
        for (User user: users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCount = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null){
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0)+1);

                }
                Sentiment mostFrequentSentiment = null;
                int maxCount = 0;
                for (Map.Entry<Sentiment, Integer> entry: sentimentCount.entrySet()){
                    if (entry.getValue()>maxCount){
                        maxCount= entry.getValue();
                        mostFrequentSentiment=entry.getKey();

                    }
                }
                if (mostFrequentSentiment != null){
                    emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days",mostFrequentSentiment.toString());
                }


            }

        }
    }
//    @Scheduled(cron = "0 0/10 * ? * *")
//    public void clearAppCache(){
//        appCache.init();
//    }
}
