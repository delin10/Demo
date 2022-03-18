package nil.ed.test.json;

import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

public class Jsonout {


    private static final Set<Character> ESCAPE_CHARS = Sets.newHashSet('<', '(', '[', '{', '\\', '^', '-', '=', '$', '!', '|', ']', '}', ')', '?', '*', '+', '.', '>');
    private static final char MYSQL_ESCAPE_CHAR = '\\';
    private static final char REGEX_ESCAPE_CHAR = '\\';

    public static String escapeForMysqlRegex(String origin) {
        if (StringUtils.isBlank(origin)) {
            return origin;
        }
        StringBuilder builder = new StringBuilder(origin.length() * 2);
        for (int i = 0; i < origin.length(); ++i) {
            char ch = origin.charAt(i);
            if (ESCAPE_CHARS.contains(ch)) {
                builder.append(MYSQL_ESCAPE_CHAR).append(REGEX_ESCAPE_CHAR);
            }
            builder.append(ch);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        String json = "{\"email\":\"/Apee7j/ULsw9OMZp/dGJXO0ubT0xd5+myt5NLrOQ9g=\",\"firstName\":\"JDx93Y06ASUGenJWmJ/+7g==\",\"haveAccount\":true,\"identityNumber\":\"McJBtz7ElGd4h1QK5+zUJA==\",\"identityType\":\"2\",\"lastName\":\"bian\",\"mobileCountryCode\":\"+86\",\"mobilePhone\":\"hv/krAmuvzI5xDXYGpjeRw==\",\"purchaserAccount\":\"13671210339\",\"purchaserType\":\"2\",\"storeNameCn\":\"方恒自提点\",\"storeNameEn\":\"FanghengPickUp\",\"storeType\":\"3\"}";
        System.out.println(JSON.parseObject(json).getString("purchaserAccount"));

        System.out.println(escapeForMysqlRegex("HjekX/2pXWhCZCBM+T7PkA=="));

        System.out.println(String.format("%.02f", 0D));
    }

}
