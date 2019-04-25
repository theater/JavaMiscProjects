package main.java.web.dto;

import java.util.List;

public class WolfArmyResultDto {

    private List<WolfArmyDto> armies;

    public WolfArmyResultDto() {
    }

    public WolfArmyResultDto(List<WolfArmyDto> armies) {
        this.armies = armies;
    }

    public List<WolfArmyDto> getArmies() {
        return armies;
    }

    public void setArmies(List<WolfArmyDto> armies) {
        this.armies = armies;
    }
}
