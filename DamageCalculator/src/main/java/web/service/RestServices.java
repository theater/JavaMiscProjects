package main.java.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.java.battle.Army;
import main.java.battle.BattleFactory;
import main.java.battle.BattleType;
import main.java.battle.IBattle;
import main.java.battle.Util;
import main.java.web.dto.BattleInputParameters;
import main.java.web.dto.BattleResultDto;
import main.java.web.dto.UserInputParameters;
import main.java.web.dto.WolfArmyResultDto;
import main.java.web.util.DataTransformUtil;
import main.java.wolf.DamageCalculator;
import main.java.wolf.WolfDamageCalculator;

@RestController
public class RestServices {

    private static final Logger logger = LoggerFactory.getLogger(RestServices.class);

    @RequestMapping(value = "/rest/wolf/calculate", method = RequestMethod.POST, produces = "application/json")
    public WolfArmyResultDto wolfCalculate(@RequestBody UserInputParameters inputData,
            BindingResult result, ModelMap model) throws IOException {
        logger.info(inputData.toString());
        DamageCalculator calculator = new WolfDamageCalculator(inputData);
        calculator.calculate();
        return DataTransformUtil.convertWolfDistributionToDTO(calculator.getArmyDistribution());
    }

    @RequestMapping(value = "/rest/battle/calculate", method = RequestMethod.POST, produces = "application/json")
    public List<BattleResultDto> battleCalculate(@RequestBody BattleInputParameters battleInput,
            BindingResult bindingResult, ModelMap model) throws IOException {
        logger.info(battleInput.toString());


        UserInputParameters attackerInput = battleInput.getAttacker();
        UserInputParameters defenderInput = battleInput.getDefender();

        return doBattles(attackerInput, defenderInput);
    }

    private List<BattleResultDto> doBattles(UserInputParameters attackerInput, UserInputParameters defenderInput) {
        List<BattleResultDto> result = new ArrayList<>();

        ArrayList<Army> attacker = new ArrayList<Army>();
        ArrayList<Army> defender = new ArrayList<Army>();
        Util.initializeArmyCollection(attacker, attackerInput);
        Util.initializeArmyCollection(defender, defenderInput);

        BattleFactory factory = new BattleFactory();
        BattleType[] values = BattleType.values();
        for (BattleType type : values) {
            logger.info("Starting battle type: " + type);

            List<Army> clonedAttacker = Util.cloneArmy(attacker);
            List<Army> clonedDefender = Util.cloneArmy(defender);
            IBattle battle = factory.getBattle(type, clonedAttacker, clonedDefender);
            battle.fight();

            BattleResultDto dto = new BattleResultDto(battle.getAttackerTotalLosses(), battle.getDefenderTotalLosses(), battle.getType().name());
            result.add(dto);
        }

        return result;
    }
}
