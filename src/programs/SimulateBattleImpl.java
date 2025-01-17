package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.PrintBattleLog;
import com.battle.heroes.army.programs.SimulateBattle;

import java.util.*;

public class SimulateBattleImpl implements SimulateBattle {
    private PrintBattleLog printBattleLog;

    //  Алгоритмическая сложность O(n2*log n)
    @Override
    public void simulate(Army playerArmy, Army computerArmy) throws InterruptedException {
        List<Unit> playerUnits = playerArmy.getUnits();
        List<Unit> computerUnits = computerArmy.getUnits();

        while (!playerUnits.isEmpty() && !computerUnits.isEmpty()) {
            armyAttack(playerUnits);
            armyAttack(computerUnits);
        }
    }

    private void armyAttack(List<Unit> units) throws InterruptedException {
        List<Integer> indexToRemove = new ArrayList<>();
        for (int i = 0; i < units.size(); i++){
            Unit unit = units.get(i);
            if (!unit.isAlive()) {
                indexToRemove.add(i);
                continue;
            }
            Unit target = unit.getProgram().attack();
            if (target != null) {
                printBattleLog.printBattleLog(unit, target);
            }
        }
        indexToRemove.sort(Collections.reverseOrder());
        for (int i : indexToRemove) {
            units.remove(i);
        }
    }
}