package nil.ed.test.antlr4;

import java.nio.CharBuffer;

import org.antlr.v4.runtime.CodePointBuffer;
import org.antlr.v4.runtime.CodePointCharStream;

/**
 * java -Xmx500M -cp "/Users/lidelin10/lidelin/env/extlib/antlr-4.9-complete.jar" org.antlr.v4.Tool MySqlParser.g4.
 * java -Xmx500M -cp "/Users/lidelin10/lidelin/env/extlib/antlr-4.9-complete.jar" org.antlr.v4.Tool MySqlLexer.g4.
 * @author lidelin.
 */
public class LexerDemo {

    public static void main(String[] args) {
        /*
        As SQL grammar are normally not case sensitive but this grammar implementation is, you must use a custom character stream that converts all characters to uppercase before sending them to the lexer.
         */
        String sql = "SELECT * FROM TEST".toUpperCase();
        CharBuffer charBuffer = CharBuffer.wrap(sql.toCharArray());
        CodePointBuffer codePointBuffer = CodePointBuffer.withChars(charBuffer);
        CodePointCharStream codePointCharStream = CodePointCharStream.fromBuffer(codePointBuffer);
        MySqlLexer lexer = new MySqlLexer(codePointCharStream);
        lexer.getAllTokens().forEach(token -> {
            System.out.println(token);
        });
    }

}
