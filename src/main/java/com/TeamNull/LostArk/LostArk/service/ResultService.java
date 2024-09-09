package com.TeamNull.LostArk.LostArk.service;

import com.TeamNull.LostArk.LostArk.Job.JobAttributes;
import com.TeamNull.LostArk.LostArk.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;

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
}
