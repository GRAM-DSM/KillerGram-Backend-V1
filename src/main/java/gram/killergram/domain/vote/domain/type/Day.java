package gram.killergram.domain.vote.domain.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Day {
    MONDAY("월요일"),
    TUESDAY("화요일"),
    WEDNESDAY("수요일"),
    THURSDAY("목요일"),
    FRIDAY("금요일");

    private final String name;

    Day(String name) {
        this.name = name;
    }

    private static final Map<String, Day> NAME_TO_DAY_MAP = new HashMap<>();

    static {
        for (Day day : Day.values()) {
            NAME_TO_DAY_MAP.put(day.getName(), day);
        }
    }

    //change value Korean
    public static Day fromValue(String name) {
        Day day = NAME_TO_DAY_MAP.get(name);
        if (day != null) {
            return day;
        }
        throw new IllegalArgumentException("In valid day: " + name);
    }
}
