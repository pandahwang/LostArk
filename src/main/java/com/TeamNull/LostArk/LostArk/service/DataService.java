package com.TeamNull.LostArk.LostArk.service;

import com.TeamNull.LostArk.LostArk.Job.JobAttributes;
import com.TeamNull.LostArk.LostArk.dto.DataDto;
import com.TeamNull.LostArk.LostArk.entity.Data;
import com.TeamNull.LostArk.LostArk.repository.DataRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataService {

    private final DataRepository dataRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // json 파일을 읽어와서 DB에 저장하는 메소드
    private Map<String, JobAttributes> loadJobAttributesFromJson() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/LostArk.json");
            return objectMapper.readValue(inputStream, new TypeReference<Map<String,JobAttributes>>() {});
        } catch (Exception e) {
            throw new RuntimeException("json 파일에서 불러오기 실패", e);
        }
    }
    private final Map<String, JobAttributes> jobAttributesMap = loadJobAttributesFromJson();

//    public DataDto findById(Integer id) {
        public List<DataDto> findById(int id) {
            Optional<Data> data = dataRepository.findById(id);

            if (data.isPresent()) {
                Data result = data.get();
                List<DataDto> resData = new ArrayList<>();
                long total = result.getDestroyer() + result.getBerserker() + result.getSlayer() + result.getGunlancer() +
                        result.getPaladin() + result.getSoulfist() + result.getWardancer() + result.getBreaker() +
                        result.getStriker() + result.getScrapper() + result.getGlaivier() + result.getGunslinger() +
                        result.getDeadeye() + result.getArtillerist() + result.getMachinist() + result.getSharpshooter() +
                        result.getBard() + result.getSummoner() + result.getSorceress() + result.getArcanist() +
                        result.getShadowhunter() + result.getReaper() + result.getDeathblade() + result.getSouleater() +
                        result.getAeromancer() + result.getArtist();

                resData.add(new DataDto("버서커", result.getBerserker(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/berserker.png"));
                resData.add(new DataDto("디스트로이어", result.getDestroyer(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/destroyer.png"));
                resData.add(new DataDto("워로드", result.getGunlancer(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/warlord.png"));
                resData.add(new DataDto("홀리나이트", result.getPaladin(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/holyknight.png"));
                resData.add(new DataDto("슬레이어", result.getSlayer(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/berserker_female.png"));
                resData.add(new DataDto("아르카나", result.getArcanist(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/arcana.png"));
                resData.add(new DataDto("서머너", result.getSummoner(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/summoner.png"));
                resData.add(new DataDto("바드", result.getBard(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/bard.png"));
                resData.add(new DataDto("소서리스", result.getSorceress(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/sorceress.png"));
                resData.add(new DataDto("배틀마스터", result.getWardancer(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/battlemaster.png"));
                resData.add(new DataDto("인파이터", result.getScrapper(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/infighter.png"));
                resData.add(new DataDto("기공사", result.getSoulfist(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/soulmaster.png"));
                resData.add(new DataDto("창술사", result.getGlaivier(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/lancemaster.png"));
                resData.add(new DataDto("스트라이커", result.getStriker(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/striker.png"));
                resData.add(new DataDto("브레이커", result.getBreaker(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/infighter_male.png"));
                resData.add(new DataDto("블레이드", result.getDeathblade(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/blade.png"));
                resData.add(new DataDto("데모닉", result.getShadowhunter(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/demonic.png"));
                resData.add(new DataDto("리퍼", result.getReaper(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/reaper.png"));
                resData.add(new DataDto("소울이터", result.getSouleater(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/soul_eater_s.png"));
                resData.add(new DataDto("호크아이", result.getSharpshooter(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/hawkeye.png"));
                resData.add(new DataDto("데빌헌터", result.getDeadeye(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/devilhunter.png"));
                resData.add(new DataDto("블래스터", result.getArtillerist(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/blaster.png"));
                resData.add(new DataDto("기상술사", result.getAeromancer(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/weather_artist.png"));
                resData.add(new DataDto("스카우터", result.getMachinist(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/scouter.png"));
                resData.add(new DataDto("건슬링어", result.getGunslinger(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/gunslinger.png"));
                resData.add(new DataDto("도화가", result.getArtist(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/artist.png"));

                resData = resData.stream()
                        .sorted(Comparator.comparingDouble(DataDto::getPercentage).reversed())
                        .collect(Collectors.toList());

                return resData;
            } else {
                // 데이터가 없을 때 처리 (예외를 던지거나 빈 리스트 반환)
                throw new EntityNotFoundException("Data not found for id: " + id);
            }
        }
}

//        DataDto dataDto = new DataDto();
//        dataDto.setId(data.getId());
//        dataDto.setBerserker(data.getBerserker());
//        dataDto.setDestroyer(data.getDestroyer());
//        dataDto.setGunlancer(data.getGunlancer());
//        dataDto.setPaladin(data.getPaladin());
//        dataDto.setArcanist(data.getArcanist());
//        dataDto.setSummoner(data.getSummoner());
//        dataDto.setBard(data.getBard());
//        dataDto.setSorceress(data.getSorceress());
//        dataDto.setWardancer(data.getWardancer());
//        dataDto.setScrapper(data.getScrapper());
//        dataDto.setSoulfist(data.getSoulfist());
//        dataDto.setGlaivier(data.getGlaivier());
//        dataDto.setStriker(data.getStriker());
//        dataDto.setDeathblade(data.getDeathblade());
//        dataDto.setShadowhunter(data.getShadowhunter());
//        dataDto.setReaper(data.getReaper());
//        dataDto.setSharpshooter(data.getSharpshooter());
//        dataDto.setDeadeye(data.getDeadeye());
//        dataDto.setArtillerist(data.getArtillerist());
//        dataDto.setMachinist(data.getMachinist());
//        dataDto.setGunslinger(data.getGunslinger());
//        dataDto.setAeromancer(data.getAeromancer());
//        dataDto.setSlayer(data.getSlayer());
//        dataDto.setCreatedAt(data.getCreatedAt());





