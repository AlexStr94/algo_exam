package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.GeneratePreset;

import java.util.*;

public class GeneratePresetImpl implements GeneratePreset {

    @Override
    public Army generate(List<Unit> unitList, int maxPoints) {
        Army army = new Army();
        List<Unit> units = new ArrayList<>();
        int points = 0;
        HashSet<String> occupaidCoordinates = new HashSet<>();
        unitList.sort(new Comparator<Unit>() {
            @Override
            public int compare(Unit unit1, Unit unit2) {
                double efficiency1 = (unit1.getBaseAttack() + unit1.getHealth()) / (double) unit1.getCost();
                double efficiency2 = (unit2.getBaseAttack() + unit2.getHealth()) / (double) unit2.getCost();
                return Double.compare(efficiency2, efficiency1);
            }
        });
        for (Unit unit : unitList) {
            for (int i = 0; i < 11; i++) {
                if (points + unit.getCost() < maxPoints) {
                    int[] unitCoorditane = getRandomCoordinate(occupaidCoordinates);
                    Unit unit1 = new Unit(unit.getUnitType() + " " + i, unit.getUnitType(), unit.getHealth(),
                            unit.getBaseAttack(), unit.getCost(), unit.getAttackType(), unit.getAttackBonuses(),
                            unit.getDefenceBonuses(), unitCoorditane[0], unitCoorditane[1]);
                    units.add(unit1);
                    points += unit.getCost();
                }
            }
        }
        army.setPoints(points);
        army.setUnits(units);
        return army;
    }

    private int[] getRandomCoordinate(Set<String> coordinate) {
        Random random = new Random();
        int X, Y;
        while (true) {
            X = random.nextInt(3);
            Y = random.nextInt(21);
            String coord = X + "-" + Y;
            if (coordinate.contains(coord)) {
                continue;
            }
            return new int[]{X, Y};
        }
    }
}