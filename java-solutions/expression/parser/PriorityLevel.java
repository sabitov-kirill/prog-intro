package expression.parser;

import java.util.List;
import java.util.Set;

public record PriorityLevel(boolean isUnary, Set<String> operatorsSigns) {
    public static final List<PriorityLevel> OPERATORS_BY_PRIOR = List.of(
            new PriorityLevel(false, Set.of("min", "max", "set", "clear")), // 50
            new PriorityLevel(false, Set.of("+", "-")),       // 100
            new PriorityLevel(false, Set.of("*", "/")),       // 200
            new PriorityLevel(true,  Set.of("-", "count"))    // 300 (Unary)
    );
}
