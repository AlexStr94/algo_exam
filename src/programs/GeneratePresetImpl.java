package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.GeneratePreset;

import java.util.*;

public class GeneratePresetImpl implements GeneratePreset {
    // Алгоритмическая сложность O(n*m) - кол-во типов * максимальное количество юнитов
    @Override
    public Army generate(List<Unit> unitList, int maxPoints) {
        List<Unit> units = new ArrayList<>();
        int points = 0;
        unitList.sort(Comparator.comparingDouble(unit -> -((double) (unit.getBaseAttack() + unit.getHealth()) / unit.getCost())));
        for (Unit unit : unitList) {
            for (int i = 0; i < 11; i++) {
                if (points + unit.getCost() < maxPoints) {
                    points += unit.getCost();
                    units.add(createUnit(unit));
                } else {
                    break;
                }
            }
        }
        addCoordinates(units);
        Army army = new Army();
        army.setPoints(points);
        army.setUnits(units);
        return army;
    }

    private Unit createUnit(Unit baseUnit) {
        Unit newUnit = new Unit(baseUnit.getName(), baseUnit.getUnitType(), baseUnit.getHealth(),
                baseUnit.getBaseAttack(), baseUnit.getCost(), baseUnit.getAttackType(),
                baseUnit.getAttackBonuses(), baseUnit.getDefenceBonuses(), -1, -1);
        newUnit.setName(baseUnit.getUnitType() + " " + generateTatarName());
        return newUnit;
    }

    private String generateTatarName() {
        String[] firstPart = {"Ай", "Иль", "Ри"};
        String[] secondPart = {"дар", "шат", "нур"};

        Random random = new Random();

        String f = firstPart[random.nextInt(firstPart.length)];
        String l = secondPart[random.nextInt(secondPart.length)];

        return f + l;
    }

    private void addCoordinates(List<Unit> units) {
        Random random = new Random();
        HashMap<String, Unit> coordinates = new HashMap<>();

        for (Unit unit : units) {
            addCoordinate(unit, random, coordinates);
        }
    }

    private void addCoordinate(Unit unit, Random random, HashMap<String, Unit> coordinates) {
        int X, Y;
        while (true) {
            X = random.nextInt(3);
            Y = random.nextInt(21);
            String key = X + "-" + Y;
            if (coordinates.containsKey(key)){
                continue;
            }
            coordinates.put(key, unit);
            unit.setxCoordinate(X);
            unit.setyCoordinate(Y);
            return;
        }
    }
}