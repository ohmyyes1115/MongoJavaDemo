package util;

import java.time.LocalDateTime;
import java.util.Arrays;

public enum Period {
    MORNING("上午"),
    AFTERNOON("下午"),
    NIGHT("晚上");

    private String mStr;

    Period(String str) {
        mStr = str;
    }

    public static Period of(String periodStr) {
        return Arrays.stream(values())
                     .filter(period ->period.toString().equals(periodStr))
                     .findFirst()
                     .orElseThrow(IllegalArgumentException::new);
    }

    public static Period now() {
        return of(LocalDateTime.now());
    }

    public static Period of(LocalDateTime time) {
        if (time.getHour() < 12) {
            return MORNING;
        }
        else if (time.getHour() < 18) {
            return AFTERNOON;
        }
        else {
            return NIGHT;
        }
    }

    @Override
    public String toString() {
        return mStr;
    }
}