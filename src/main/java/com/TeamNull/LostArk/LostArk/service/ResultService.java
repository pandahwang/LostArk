package com.TeamNull.LostArk.LostArk.service;

import com.TeamNull.LostArk.LostArk.Job.JobAttributes;
import com.TeamNull.LostArk.LostArk.entity.User;
import com.TeamNull.LostArk.LostArk.repository.ResultRepository;
import com.TeamNull.LostArk.LostArk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;
    private final UserRepository userRepository;

    //직업별 성향치
    public List<JobAttributes> getAlljobAttributes() {
        return Arrays.asList(
                new JobAttributes("Destroyer",3,4,1,2,5),
                new JobAttributes("Berserker",2,4,2,2,5),
                new JobAttributes("Slayer",2,3,2,3,5),
                new JobAttributes("Gunlancer",5,4,4,1,1),
                new JobAttributes("Paladin",3,4,5,1,2),
                new JobAttributes("Soulfist",2,2,5,3,3),
                new JobAttributes("Wardancer",4,3,4,3,1),
                new JobAttributes("Breaker",2,2,4,4,3),
                new JobAttributes("Striker",2,2,4,5,2),
                new JobAttributes("Scrapper",3,3,3,3,3),
                new JobAttributes("Glaivier",2,1,5,3,4),
                new JobAttributes("Gunslinger",1,3,2,4,5),
                new JobAttributes("Deadeye",1,3,1,5,5),
                new JobAttributes("Artillerist",2,4,3,3,3),
                new JobAttributes("Machinist",2,4,4,2,3),
                new JobAttributes("Sharpshooter",2,3,3,4,3),
                new JobAttributes("Bard",4,5,4,1,1),
                new JobAttributes("Summoner",3,3,3,4,2),
                new JobAttributes("Sorceress",2,3,2,5,3),
                new JobAttributes("Arcanist",2,3,3,5,2),
                new JobAttributes("Shadowhunter",3,3,2,2,5),
                new JobAttributes("Reaper",2,2,1,5,5),
                new JobAttributes("Deathblade",2,2,3,4,4),
                new JobAttributes("Souleater",1,1,5,5,3),
                new JobAttributes("Aeromancer",4,3,4,2,2),
                new JobAttributes("Artist",4,4,4,2,1)
        );
    }


    public Optional<List<Integer>> user(UUID id) {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()) {
            User user = result.get();

            double sum1 = (user.getQuestion1()+ user.getQuestion2())/2;
            double sum2 = (user.getQuestion3()+ user.getQuestion4())/2;
            double sum3 = (user.getQuestion5()+ user.getQuestion6())/2;
            double sum4 = (user.getQuestion7()+ user.getQuestion8())/2;
            double sum5 = (user.getQuestion9()+ user.getQuestion10())/2;

            List<Integer> results = new ArrayList<>();

            for (JobAttributes job : getAlljobAttributes()) {
                double totalScore = (sum1 * job.getFriendliness()) +
                        (sum2 * job.getConscientiousness()) +
                        (sum3 * job.getExtraversion()) +
                        (sum4 * job.getOpenness()) +
                        (sum5 * job.getNeuroticism());
                System.out.println("직업명 : " +job.getJobName() + "점수 : " + totalScore);

                results.add((int) totalScore);
            }
            return Optional.of(results);
        }
        return Optional.empty();
    }
}
