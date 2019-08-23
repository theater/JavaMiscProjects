package main.java.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import main.java.parser.JSONParser;
import main.java.web.dto.BattleInputParameters;
import main.java.web.dto.BattleResultDto;
import main.java.web.dto.UserInputParameters;
import main.java.web.dto.WolfArmyResultDto;
import main.java.web.util.DataTransformUtil;
import main.java.wolf.DamageCalculator;
import main.java.wolf.PvPArmyCalculator;
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
        calculator.printResults();
        return DataTransformUtil.convertWolfDistributionToDTO(calculator.getArmyDistribution());
    }

    @RequestMapping(value = "/rest/pvp/calculate", method = RequestMethod.POST, produces = "application/json")
    public WolfArmyResultDto pvpCalculate(@RequestBody UserInputParameters inputData,
            BindingResult result, ModelMap model) throws IOException {
        logger.info(inputData.toString());
        DamageCalculator calculator = new PvPArmyCalculator(inputData);
        calculator.calculate();
        calculator.printResults();
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
        final long START_TIME_MILLIS = System.currentTimeMillis();

        List<BattleResultDto> result = new ArrayList<>();

        ArrayList<Army> attacker = new ArrayList<Army>();
        ArrayList<Army> defender = new ArrayList<Army>();
        Util.initializeArmyCollection(attacker, attackerInput);
        Util.initializeArmyCollection(defender, defenderInput);

        BattleFactory factory = new BattleFactory();
        BattleType[] values = BattleType.values();
        Map<BattleType, Long> battleTimes = new HashMap<>();
        for (BattleType type : values) {
            final long BATTLE_START_TIME_MILLIS = System.currentTimeMillis();
            logger.debug("Starting battle type: " + type);

            IBattle battle = factory.getBattle(type, attacker, defender, attackerInput.getRounds());
            battle.fight();
            logger.info("Attacker losses={}, \tDefender losses={}, \tBattle type={}", battle.getAttackerTotalLosses(), battle.getDefenderTotalLosses(), battle.getType());

            BattleResultDto dto = new BattleResultDto(battle.getAttackerTotalLosses(), battle.getDefenderTotalLosses(), battle.getType().name());
            result.add(dto);
            battleTimes.put(type, System.currentTimeMillis() - BATTLE_START_TIME_MILLIS);
        }

        battleTimes.entrySet().stream().forEach(entry -> logger.info("Simulation for battleType {} finished for {} ms", entry.getKey(), entry.getValue()));

        logger.info("Total simulation time = {} ms", System.currentTimeMillis() - START_TIME_MILLIS);
        return result;
    }

    @RequestMapping(value = "/rest/battle/defaultValues", method = RequestMethod.GET, produces = "application/json")
    public BattleInputParameters provideBattleDefaultValues() throws IOException {
        final String attackerFile = "attacker.json";
        final String defenderFile = "defender.json";

        JSONParser parser = new JSONParser();
        UserInputParameters attackerInput = parser.parseUserInput(attackerFile);
        UserInputParameters defenderInput = parser.parseUserInput(defenderFile);

        return new BattleInputParameters(attackerInput, defenderInput);
    }

    @RequestMapping(value = "/rest/wolf/defaultValues", method = RequestMethod.GET, produces = "application/json")
    public UserInputParameters provideWolfDefaultValues() throws IOException {
        final String attackerFile = "wolfInput.json";

        JSONParser parser = new JSONParser();
        UserInputParameters wolfInput = parser.parseUserInput(attackerFile);

        return wolfInput;
    }
}
