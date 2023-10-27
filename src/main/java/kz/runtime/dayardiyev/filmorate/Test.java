package kz.runtime.dayardiyev.filmorate;

import java.time.Duration;

public class Test {
    public static void main(String[] args) {
        Duration duration = Duration.ofSeconds(90);
        System.out.println(!duration.isNegative());
    }
}
