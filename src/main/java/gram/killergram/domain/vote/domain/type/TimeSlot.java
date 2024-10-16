package gram.killergram.domain.vote.domain.type;

import lombok.Getter;

@Getter
public enum TimeSlot {
    LUNCH_TIME("점심시간"),
    DINNER_TIME("저녁시간");

    private final String value;

    TimeSlot(String value) {
        this.value = value;
    }

    public static TimeSlot fromValue(String value) {
        return switch (value) {
            case "점심시간" -> LUNCH_TIME;
            case "저녁시간" -> DINNER_TIME;
            default -> throw new IllegalArgumentException("Invalid time_slot: " + value);
        };
    }
}
