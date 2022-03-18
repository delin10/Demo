package nil.ed.test.antlr4.parse;

import java.nio.CharBuffer;

import nil.ed.test.antlr4.MySqlLexer;
import org.antlr.v4.runtime.CodePointBuffer;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * @author lidelin.
 */
public class ParserDemo {
    public static void main(String[] args) {
        String sql = "SELECT * FROM TEST".toUpperCase();
        CharBuffer charBuffer = CharBuffer.wrap(sql.toCharArray());
        CodePointBuffer codePointBuffer = CodePointBuffer.withChars(charBuffer);
        CodePointCharStream codePointCharStream = CodePointCharStream.fromBuffer(codePointBuffer);
        MySqlLexer lexer = new MySqlLexer(codePointCharStream);
        MySqlParser mySqlParser = new MySqlParser(new CommonTokenStream(lexer));
        System.out.println(mySqlParser.tables().tableName());
    }
}
