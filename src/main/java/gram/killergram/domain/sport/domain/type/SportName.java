package gram.killergram.domain.sport.domain.type;

public enum SportName {
    SOCCER("축구"),
    BASKETBALL("농구"),
    BASEBALL("야구"),
    BADMINTON("배드민턴"),
    VOLLEYBALL("배구"),
    GYM("헬스"),
    PING_PONG("탁구"),
    WOMAN_SPORTS("여자 스포츠");

    private final String value;

    SportName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SportName fromValue(String name) {
        for (SportName value : SportName.values()) {
            if (value.getValue() == name) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid day: " + name);
    }
}
