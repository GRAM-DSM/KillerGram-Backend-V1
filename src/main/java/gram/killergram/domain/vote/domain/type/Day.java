package gram.killergram.domain.vote.domain.type;

import lombok.Getter;

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

    public static Day fromValue(String name) {
        for (Day day : Day.values()) {
            if (day.getName() == name) {
                return day;
            }
        }
        throw new IllegalArgumentException("Invalid day: " + name);
    }
}
