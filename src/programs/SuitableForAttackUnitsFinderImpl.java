package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;

import java.util.ArrayList;
import java.util.List;

public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder {
    // Алгоритмическая сложность O(n*m), так как мы два раза проходимся по всем ячейкам в рядах.
    // Константой 2 мы пренебрегаем.

    @Override
    public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) {
        List<Unit> suitableUnits = new ArrayList<>();
        Unit[][] units = new Unit[3][21];

        // Делаем удобный для использования упорядоченный массив
        for (int i = 0; i < 3; i++) {
            List<Unit> row = unitsByRow.get(i);
            for (Unit unit : row) {
                units[i][unit.getyCoordinate()] = unit;
            }
        }

        for (int i = 0; i < 21; i++) {
            if (isLeftArmyTarget) {
                for (int j = 2; j >= 0; j--) {
                    Unit unit = units[j][i];
                    if (unit != null && unit.isAlive()) {
                        suitableUnits.add(unit);
                        break;
                    }
                }
            } else {
                for (int j = 0; j < 3; j++) {
                    Unit unit = units[j][i];
                    if (unit != null && unit.isAlive()) {
                        suitableUnits.add(unit);
                        break;
                    }
                }
            }
        }
        return suitableUnits;
    }

}
