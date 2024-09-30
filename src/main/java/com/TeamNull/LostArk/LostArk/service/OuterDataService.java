package com.TeamNull.LostArk.LostArk.service;

import com.TeamNull.LostArk.LostArk.dto.OuterDataDto;
import com.TeamNull.LostArk.LostArk.entity.OuterData;
import com.TeamNull.LostArk.LostArk.repository.OuterDataRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OuterDataService {

    private final OuterDataRepository outerDataRepository;
    private final ObjectMapper objectMapper;

    // json 파일을 읽어와서 DB에 저장하는 메소드
    private Map<String, JobAttributes> loadJobAttributesFromJson() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/LostArk.json");
            return objectMapper.readValue(inputStream, new TypeReference<Map<String,JobAttributes>>() {});
        } catch (Exception e) {
            throw new RuntimeException("json 파일에서 불러오기 실패", e);
        }
    }

    private final Map<String, JobAttributes> jobAttributesList = loadJobAttributesFromJson();


    // 매일 00시에 실행
    @Scheduled(cron = "0 0 0 * * *")
    public void update() throws IOException, ParseException {
        create();
    }

    // outerData DB에 저장
    public void create() throws IOException, ParseException {
        String result;
        URL url = new URL("https://asia-northeast3-loasearch.cloudfunctions.net/app/total?minLv=&maxLv=");
        BufferedReader bf;
        bf = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
        result = bf.readLine();

        // 받아온 데이터를 JSON으로 변환
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
        JSONObject data = (JSONObject) jsonObject.get("data");
        JSONArray classTotalArr = (JSONArray)data.get("classTotal");

        for (int i = 0; i < classTotalArr.size(); i++) {
            JSONObject classData = (JSONObject) classTotalArr.get(i);
            String className = (String) classData.get("className");

            // 특정 클래스 이름이 있는 경우 해당 객체를 삭제
            if (className.equals("전사(남)") || className.equals("전사(여)") ||
                    className.equals("무도가(남)") || className.equals("무도가(여)") ||
                    className.equals("헌터(남)") || className.equals("헌터(여)") ||
                    className.equals("마법사") || className.equals("암살자") ||
                    className.equals("스페셜리스트")) {

                classTotalArr.remove(i);
                i--;  // remove 했으므로 인덱스 조정
            }
        }

        // 받아온 JSON 배열을 Dto에 넣어서 save
        OuterData outerData = new OuterData();
        for (Object obj : classTotalArr){
            JSONObject classData = (JSONObject) obj;
            String className = (String) classData.get("className");
            int classTotal = Integer.parseInt(classData.get("classTotal").toString());

            switch (className) {
                case "디스트로이어" : outerData.setDestroyer(classTotal);
                    break;
                case "버서커" : outerData.setBerserker(classTotal);
                    break;
                case "슬레이어" : outerData.setSlayer(classTotal);
                    break;
                case "워로드" : outerData.setGunlancer(classTotal);
                    break;
                case "홀리나이트" : outerData.setPaladin(classTotal);
                    break;
                case "기공사" : outerData.setSoulfist(classTotal);
                    break;
                case "배틀마스터" : outerData.setWardancer(classTotal);
                    break;
                case "브레이커" : outerData.setBreaker(classTotal);
                    break;
                case "스트라이커" : outerData.setStriker(classTotal);
                    break;
                case "인파이터" : outerData.setScrapper(classTotal);
                    break;
                case "창술사" : outerData.setGlaivier(classTotal);
                    break;
                case "건슬링어" : outerData.setGunslinger(classTotal);
                    break;
                case "데빌헌터" : outerData.setDeadeye(classTotal);
                    break;
                case "블래스터" : outerData.setArtillerist(classTotal);
                    break;
                case "스카우터" : outerData.setMachinist(classTotal);
                    break;
                case "호크아이" : outerData.setSharpshooter(classTotal);
                    break;
                case "바드" : outerData.setBard(classTotal);
                    break;
                case "서머너" : outerData.setSummoner(classTotal);
                    break;
                case "소서리스" : outerData.setSorceress(classTotal);
                    break;
                case "아르카나" : outerData.setArcanist(classTotal);
                    break;
                case "데모닉" : outerData.setShadowhunter(classTotal);
                    break;
                case "리퍼" : outerData.setReaper(classTotal);
                    break;
                case "블레이드" : outerData.setDeathblade(classTotal);
                    break;
                case "소울이터" : outerData.setSouleater(classTotal);
                    break;
                case "기상술사" : outerData.setAeromancer(classTotal);
                    break;
                case "도화가" : outerData.setArtist(classTotal);
                    break;
            }
        }
        outerData.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        outerData.setId(1);
        outerDataRepository.save(outerData);
    }

    public List<OuterDataDto> read() {
        Optional<OuterData> outerData = outerDataRepository.findById(1);

        if(outerData.isPresent()){
            OuterData result = outerData.get();
            List<OuterDataDto> resData = new ArrayList<>();
            long total = result.getDestroyer() + result.getBerserker() + result.getSlayer() + result.getGunlancer() +
                    result.getPaladin() + result.getSoulfist() + result.getWardancer() + result.getBreaker() +
                    result.getStriker() + result.getScrapper() + result.getGlaivier() + result.getGunslinger() +
                    result.getDeadeye() + result.getArtillerist() + result.getMachinist() + result.getSharpshooter() +
                    result.getBard() + result.getSummoner() + result.getSorceress() + result.getArcanist() +
                    result.getShadowhunter() + result.getReaper() + result.getDeathblade() + result.getSouleater() +
                    result.getAeromancer() + result.getArtist();

            resData.add(new OuterDataDto("버서커", result.getBerserker(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/berserker.png"));
            resData.add(new OuterDataDto("디스트로이어", result.getDestroyer(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/destroyer.png"));
            resData.add(new OuterDataDto("워로드", result.getGunlancer(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/warlord.png"));
            resData.add(new OuterDataDto("홀리나이트", result.getPaladin(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/holyknight.png"));
            resData.add(new OuterDataDto("슬레이어", result.getSlayer(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/berserker_female.png"));
            resData.add(new OuterDataDto("아르카나", result.getArcanist(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/arcana.png"));
            resData.add(new OuterDataDto("서머너", result.getSummoner(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/summoner.png"));
            resData.add(new OuterDataDto("바드", result.getBard(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/bard.png"));
            resData.add(new OuterDataDto("소서리스", result.getSorceress(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/sorceress.png"));
            resData.add(new OuterDataDto("배틀마스터", result.getWardancer(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/battlemaster.png"));
            resData.add(new OuterDataDto("인파이터", result.getScrapper(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/infighter.png"));
            resData.add(new OuterDataDto("기공사", result.getSoulfist(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/soulmaster.png"));
            resData.add(new OuterDataDto("창술사", result.getGlaivier(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/lancemaster.png"));
            resData.add(new OuterDataDto("스트라이커", result.getStriker(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/striker.png"));
            resData.add(new OuterDataDto("브레이커", result.getBreaker(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/infighter_male.png"));
            resData.add(new OuterDataDto("블레이드", result.getDeathblade(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/blade.png"));
            resData.add(new OuterDataDto("데모닉", result.getShadowhunter(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/demonic.png"));
            resData.add(new OuterDataDto("리퍼", result.getReaper(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/reaper.png"));
            resData.add(new OuterDataDto("소울이터", result.getSouleater(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/soul_eater_s.png"));
            resData.add(new OuterDataDto("호크아이", result.getSharpshooter(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/hawkeye.png"));
            resData.add(new OuterDataDto("데빌헌터", result.getDeadeye(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/devilhunter.png"));
            resData.add(new OuterDataDto("블래스터", result.getArtillerist(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/blaster.png"));
            resData.add(new OuterDataDto("기상술사", result.getAeromancer(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/weather_artist.png"));
            resData.add(new OuterDataDto("스카우터", result.getMachinist(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/scouter.png"));
            resData.add(new OuterDataDto("건슬링어", result.getGunslinger(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/gunslinger.png"));
            resData.add(new OuterDataDto("도화가", result.getArtist(), total, "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/thumb/artist.png"));

            resData = resData.stream()
                    .sorted(Comparator.comparingDouble(OuterDataDto::getPercentage).reversed())
                    .collect(Collectors.toList());
            return resData;

        }

        return null;
    }
}
