package nova.committee.atom.sweep.util;

/**
 * Project: clean
 * Author: cnlimiter
 * Date: 2022/12/6 23:14
 * Description:
 */
public class JSONFormat {
    private static String SPACE = "    ";

    public JSONFormat() {
    }

    public static String formatJson(String json) {
        StringBuilder result = new StringBuilder();
        int length = json.length();
        int number = 0;

        for(int i = 0; i < length; ++i) {
            char key = json.charAt(i);
            if (key != '[' && key != '{') {
                if (key != ']' && key != '}') {
                    if (key == ',') {
                        result.append(key);
                        result.append('\n');
                        result.append(indent(number));
                    } else {
                        result.append(key);
                    }
                } else {
                    result.append('\n');
                    --number;
                    result.append(indent(number));
                    result.append(key);
                    if (i + 1 < length && json.charAt(i + 1) != ',') {
                        result.append('\n');
                    }
                }
            } else {
                if (i - 1 > 0 && json.charAt(i - 1) == ':') {
                    result.append('\n');
                    result.append(indent(number));
                }

                result.append(key);
                result.append('\n');
                ++number;
                result.append(indent(number));
            }
        }

        return result.toString();
    }

    private static String indent(int number) {
        StringBuffer result = new StringBuffer();

        for(int i = 0; i < number; ++i) {
            result.append(SPACE);
        }

        return result.toString();
    }
}
