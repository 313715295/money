import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class DateUtil {

    private static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate toDate(String string) {
        return LocalDate.parse(string, DATE_TIME_FORMATTER);
    }

    public static String toString(LocalDate date) {
        return date.format(DATE_TIME_FORMATTER);
    }
}
