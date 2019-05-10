package main.java.battle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.config.ArmyType;
import main.java.config.CalculationsHelper;
import main.java.config.ConfigManager;
import main.java.web.dto.UserInputParameters;

public final class Util {

    private Util() {

    }

    public static int calculateAverage(int currentNumber, int prevAvg, int iteration) {
        if (iteration <= 0) {
            throw new ArithmeticException("Iteration should start from 1!");
        }

        return ((iteration - 1) * prevAvg + currentNumber) / iteration;
    }

    public static void initializeArmyCollection(List<Army> armyCollection, UserInputParameters input) {
        final int MAX_TIER = ConfigManager.getInstance().getConfiguration().ABSOLUTE_MAX_TIER;

        if (armyCollection == null) {
            armyCollection = new ArrayList<Army>(MAX_TIER * 4);
        }

        ArmyType[] armyTypes = ArmyType.values();
        Map<ArmyType, List<Integer>> army = input.getArmy();
        CalculationsHelper calculationsHelper = new CalculationsHelper(input);
        for (ArmyType armyType : armyTypes) {
            List<Integer> armyByType = army.get(armyType);
            for (int i = 0; i < MAX_TIER; i++) {
                int unitsAmount = 0;
                if (armyByType != null && armyByType.size() == MAX_TIER) {
                    unitsAmount = armyByType.get(i);
                }
                Army newArmy = new Army(armyType, i, unitsAmount);
                armyCollection.add(newArmy);
                newArmy.addModifiedArmyStats(calculationsHelper);
            }
        }
    }

    /**
     * @param object
     * @throws IOException
     * @throws JsonGenerationException
     * @throws JsonMappingException
     *                                 This method can be used to easily export
     *                                 complex object in JSON file. Careful - it
     *                                 erases resources\\inputParams.json. Use it
     *                                 only as a template to generate JSON file from
     *                                 particular object
     */
    public static void printInputParams(Object object)
            throws IOException, JsonGenerationException, JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("resources\\inputParams.json"), object);
    }

    @SuppressWarnings("unchecked")
    public static List<Army> cloneArmy(List<Army> armies) {
        List<Army> newList = new ArrayList<>(armies.size());
        for (Army army : armies) {
            newList.add(army.clone());
        }
        return newList;
    }
}
