package core.poseidon.executor.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LvShengyI
 */
public class StatementHandler {

    private StatementHandler() {
    }

    public static String handle(String rawSql) {
        return rawSql.replaceAll("#\\{.*?}", "?");
    }

    public static ParamNode preProcess(String rawSql) {
        Pattern pattern = Pattern.compile("\\{.*?}");
        Matcher matcher = pattern.matcher(rawSql);

        ParamNode head = null;
        ParamNode old = null;
        while (matcher.find()) {
            ParamNode node = new ParamNode();

            if (head == null) {
                head = node;
            }

            if (old != null) {
                old.next(node);
            }
            node.setFieldName(matcher.group().replaceAll("\\{|}", ""));

            old = node;
        }

        return head;
    }
}
