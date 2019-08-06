package main.java.web.util;

import java.util.ArrayList;
import java.util.List;

import main.java.web.dto.WolfArmyDto;
import main.java.web.dto.WolfArmyResultDto;
import main.java.wolf.Army;

public final class DataTransformUtil {

    private DataTransformUtil() {
    }

    public static WolfArmyResultDto convertWolfDistributionToDTO(List<Army> list) {
        List<WolfArmyDto> resultDistribution = new ArrayList<>();
        for (Army army : list) {
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
