package gram.killergram.domain.sport.domain.type;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
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

    private static final Map<String, SportName> VALUE_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(SportName::getValue, Function.identity()));

    // to change Korean and insert DB as Korean
    public static SportName fromValue(String name) {
        SportName sport = VALUE_MAP.get(name);
        if (sport == null) {
            throw new IllegalArgumentException("Invalid sport name: " + name);
        }
        return sport;
    }
}
