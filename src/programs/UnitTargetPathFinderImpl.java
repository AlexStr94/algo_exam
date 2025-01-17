package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.Edge;
import com.battle.heroes.army.programs.UnitTargetPathFinder;

import java.util.List;
import java.util.*;

public class UnitTargetPathFinderImpl implements UnitTargetPathFinder {
    // Вспомогательный класс для реализации BFS
    class EdgeNode {
        private EdgeNode parent;
        private Edge edge;

        public EdgeNode(Edge edge) {
            this.edge = edge;
        }

        public void setParent(EdgeNode parent) {
            this.parent = parent;
        }

        public EdgeNode getParent() {
            return parent;
        }

        public Edge getEdge() {
            return edge;
        }

        public boolean parentExists() {
            return parent != null;
        }
    }

    // Метод реализует алгоритм поиска в ширину. Сложность BFS составляет O(V + E), где:
    //   - V — это количество вершин (или узлов) в графе,
    //   - E — это количество рёбер (или связей) между этими вершинами.
    // Таким образом достигается сложность O((WIDTH × HEIGHT) log(WIDTH × HEIGHT)),
    // указанная в примере.
    @Override
    public List<Edge> getTargetPath(Unit attackUnit, Unit targetUnit, List<Unit> existingUnitList) {
        int startX = attackUnit.getxCoordinate();
        int startY = attackUnit.getyCoordinate();
        int targetX = targetUnit.getxCoordinate();
        int targetY = targetUnit.getyCoordinate();

        int maxX = 26;
        int maxY = 20;

        // Set для хранения посещенных координат
        Set<String> visited = new HashSet<>();
        visited.add(startX + "," + startY);
        for (Unit unit : existingUnitList) {
            // Цель не надо добавлять в посещенные
            if (unit.getxCoordinate() != targetX && unit.getyCoordinate() != targetY) {
                visited.add(unit.getxCoordinate() + "," + unit.getyCoordinate());
            }
        }

        // Очередь для BFS
        Queue<EdgeNode> queue = new LinkedList<>();
        queue.add(new EdgeNode(new Edge(startX, startY)));

        // Направления движения (вверх, вниз, влево, вправо)
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while (!queue.isEmpty()) {
            EdgeNode currentNode = queue.poll();

            // Проверяем, достигли ли цели
            if (currentNode.getEdge().getX() == targetX && currentNode.getEdge().getY() == targetY) {
                return buildPath(currentNode);
            }

            // Проходим по всем возможным направлениям
            for (int[] direction : directions) {
                int newX = currentNode.getEdge().getX() + direction[0];
                int newY = currentNode.getEdge().getY() + direction[1];
                String newCoordinates = newX + "," + newY;

                // Проверяем выход за границы и посещенные ячейки.
                if (newX > maxX || newX < 0 || newY > maxY || newY < 0 || visited.contains(newCoordinates)) {
                    visited.add(newCoordinates);
                    continue;
                }

                EdgeNode nextNode = new EdgeNode(new Edge(newX, newY));
                nextNode.setParent(currentNode);

                queue.add(nextNode);
                visited.add(newCoordinates);
            }
        }

        return Collections.emptyList();
    }

    private List<Edge> buildPath(EdgeNode target) {
        List<Edge> path = new ArrayList<>();

        while (target.parentExists()) {
            path.add(target.getEdge());
            target = target.getParent();
        }
        path.add(target.getEdge());

        Collections.reverse(path); // Обратим порядок, чтобы путь начинался с атакующего юнита
        return path;
    }
}
