package main.java.web.util;

import java.util.ArrayList;
import java.util.List;

import main.java.web.dto.WolfArmyDto;
import main.java.web.dto.WolfArmyResultDto;
import main.java.wolf.WolfArmy;

public final class DataTransformUtil {

    private DataTransformUtil() {
    }

    public static WolfArmyResultDto convertWolfDistributionToDTO(List<WolfArmy> distribution) {
        List<WolfArmyDto> resultDistribution = new ArrayList<>();
        for (WolfArmy army : distribution) {
            WolfArmyDto dto = new WolfArmyDto();
            dto.setType(army.getType().name());
            dto.setSubType(army.getSubType().name());
            dto.setTier(army.getTier() + 1);
            dto.setNumber(army.getTroopsNumber());
            resultDistribution.add(dto);
        }
        return new WolfArmyResultDto(resultDistribution);
    }
}
