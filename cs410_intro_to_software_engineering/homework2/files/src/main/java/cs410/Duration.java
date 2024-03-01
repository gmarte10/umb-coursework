package cs410;

/*
Represents an immutable duration of time with precision of seconds
Responsibilities: add 2 durations objects, get total seconds of a duration, convert to string, two static factory
of methods, one with total seconds argument and another containing hours, minutes and seconds.
 */

public class Duration {
    /*
    Duration is displayed in H:MM:SS format.
    Example: cs410 lecture is 1:15:00, 1 million seconds corresponds to 277:46:40,
    500s <-> 0:08:20
    7000s <-> 1:56:40
    79025s <-> 21:57:05
    */
    private final int hours;
    private final int minutes;
    private final int seconds;

    // minutes and seconds have to be in correct 60m or 60s format.
    // hours, minutes and seconds cannot be negative
    public Duration(int hours, int minutes, int seconds) {
        if (minutes >= 60 || seconds >= 60 || hours < 0 || minutes < 0 || seconds < 0) {
            throw new IllegalArgumentException();
        }
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    /*
    converts seconds into a new Duration of h:mm:ss
    Example: 15950 -> 4:25:50, 45091 -> 12:31:31, 250 -> 0:04:10
    seconds cannot be negative
     */
    public static Duration of(int seconds) {
        if (seconds < 0) {
            throw new IllegalArgumentException();
        }
        if ((double) seconds >= 60) {
            double totalHours = (double) seconds / 3600;
            double hr = Math.floor(totalHours);
            double totalMinutes = (totalHours - hr) * 60;
            double m = Math.floor(totalMinutes);
            double totalSeconds = (totalMinutes - m) * 60;
            double sec = Math.round(totalSeconds);
            return new Duration((int) hr, (int) totalMinutes, (int) sec);
        }
        else {
            return new Duration(0, 0, (int) (double) seconds);
        }
    }

    /*
   converts hr, min, sec input into a new Duration of h:mm:ss
   Example: (1, 30, 5) -> 1:30:05, (14, 1, 35) -> 14:01:35
   hr, m, s cannot be negative
    */
    public static Duration of(int hr, int min, int s) {
        if (hr < 0 || min < 0 || s < 0) {
            throw new IllegalArgumentException();
        }
        int totalSeconds = (hr * 3600) + (min * 60) + s;
        return of(totalSeconds);
    }

    /*
    returns the Duration's total number of seconds
    Example: 4:25:50 -> 15950, 24:29:01 -> 88141, 0:14:50 -> 890
     */
    public int seconds() {
        return (this.hours * 3600) + (this.minutes * 60) + this.seconds;
    }

    /*
    adds two Duration objects together
    Example: Duration.of(400) + Duration.of(400) = Duration.of(800),
    0:05:50 + 6:45:13 = 6:51:03, 41:29:01 + 17:19:56 = 58:48:57
     */
    public Duration add(Duration other) {
        int h = this.hours + other.hours;
        int m = this.minutes + other.minutes;
        int s = this.seconds + other.seconds;
        int totalS = (h * 3600) + (m * 60) + s;
        return of(totalS);
    }

    // return Duration as a string in h:mm:ss format
    @Override
    public String toString() {
        return String.format("%d:%02d:%02d", this.hours, this.minutes, this.seconds);
    }

    // method to check if two Duration objects are equal, used for testing
    @Override
    public boolean equals(Object other) {
        if (other instanceof Duration) {
            Duration otherd = (Duration) other;
            return (this.hours == otherd.hours && this.minutes == otherd.minutes && this.seconds == otherd.seconds);
        }
        return false;
    }
}
