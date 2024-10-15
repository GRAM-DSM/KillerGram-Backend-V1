package gram.killergram.domain.vote.domain.type;

public enum TimeSlot {
    LAUNCH_TIME("점심시간"),
    DINNER_TIME("저녁시간");

    private final String value;

    TimeSlot(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
