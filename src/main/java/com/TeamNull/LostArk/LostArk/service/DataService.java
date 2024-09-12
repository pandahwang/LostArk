package com.TeamNull.LostArk.LostArk.service;

import com.TeamNull.LostArk.LostArk.dto.DataDto;
import com.TeamNull.LostArk.LostArk.entity.Data;
import com.TeamNull.LostArk.LostArk.repository.DataRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataService {

    private final DataRepository dataRepository;


    public DataDto findById(Integer id) {
        Data data = dataRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found for id: " + id));

        DataDto dataDto = new DataDto();
        dataDto.setId(data.getId());
        dataDto.setBerserker(data.getBerserker());
        dataDto.setDestroyer(data.getDestroyer());
        dataDto.setGunlancer(data.getGunlancer());
        dataDto.setPaladin(data.getPaladin());
        dataDto.setArcanist(data.getArcanist());
        dataDto.setSummoner(data.getSummoner());
        dataDto.setBard(data.getBard());
        dataDto.setSorceress(data.getSorceress());
        dataDto.setWardancer(data.getWardancer());
        dataDto.setScrapper(data.getScrapper());
        dataDto.setSoulfist(data.getSoulfist());
        dataDto.setGlaivier(data.getGlaivier());
        dataDto.setStriker(data.getStriker());
        dataDto.setDeathblade(data.getDeathblade());
        dataDto.setShadowhunter(data.getShadowhunter());
        dataDto.setReaper(data.getReaper());
        dataDto.setSharpshooter(data.getSharpshooter());
        dataDto.setDeadeye(data.getDeadeye());
        dataDto.setArtillerist(data.getArtillerist());
        dataDto.setMachinist(data.getMachinist());
        dataDto.setGunslinger(data.getGunslinger());
        dataDto.setAeromancer(data.getAeromancer());
        dataDto.setSlayer(data.getSlayer());
        dataDto.setCreatedAt(data.getCreatedAt());
        return dataDto;
    }


}
