package gram.killergram.domain.vote.domain.type;

public enum TimeSlot {
    LUNCH_TIME("점심시간"),
    DINNER_TIME("저녁시간");

    private final String value;

    TimeSlot(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TimeSlot fromValue(String value) {
        for (TimeSlot timeSlot : TimeSlot.values()) {
            if (timeSlot.getValue() == value) {
                return timeSlot;
            }
        }
        throw new IllegalArgumentException("Invalid day: " + value);
    }
}
