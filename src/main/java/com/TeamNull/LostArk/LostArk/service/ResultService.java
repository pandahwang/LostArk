package com.TeamNull.LostArk.LostArk.service;

import com.TeamNull.LostArk.LostArk.Job.JobAttributes;
import com.TeamNull.LostArk.LostArk.dto.UserDto;
import com.TeamNull.LostArk.LostArk.entity.User;
import com.TeamNull.LostArk.LostArk.repository.ResultRepository;
import com.TeamNull.LostArk.LostArk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

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

   @Transactional
    public void top5 (UUID id){
        User user= userRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("user not found"));

        double avg1 = (user.getQuestion1()+user.getQuestion2())/2.0;
        double avg2 = (user.getQuestion3()+user.getQuestion4())/2.0;
        double avg3 = (user.getQuestion5()+user.getQuestion6())/2.0;
        double avg4 = (user.getQuestion7()+user.getQuestion8())/2.0;
        double avg5 = (user.getQuestion9()+user.getQuestion10())/2.0;

        List<JobAttributes> results = getAlljobAttributes();

        Map<JobAttributes, Double> jobScores = results.stream()
               .collect(Collectors.toMap(
                       job -> job,
                       job -> avg1 * job.getAgreeableness() +
                               avg2 * job.getConscientiousness() +
                               avg3 * job.getExtraversion() +
                               avg4 * job.getOpenness() +
                               avg5 * job.getNeuroticism()
               ));


        List<JobAttributes> top5Jobs = jobScores.entrySet().stream()
               .sorted(Map.Entry.<JobAttributes, Double>comparingByValue().reversed())
               .limit(5)
               .map(Map.Entry::getKey)
               .collect(Collectors.toList());

       top5Jobs.forEach(job -> System.out.println("Job Name: " + job.getJobName() + ", Score: " + jobScores.get(job)));
   }
    
}
