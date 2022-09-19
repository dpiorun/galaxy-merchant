package galaxy;

public class Utils {

    // based on https://stackoverflow.com/questions/703396/how-to-nicely-format-floating-numbers-to-string-without-unnecessary-fecimal-0s
    public static String formatPrice(Double d) {
        if (d == null)
            return "null";
        if (d == d.longValue())
            return String.format("%d", d.longValue());
        else
            return String.format("%s", d);
    }
}
