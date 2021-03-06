// Generated from C:/Users/NIKITA SERGEEV/IdeaProjects/SergeevCompiler/Grammar\MiniJava.g4 by ANTLR 4.9.1
package gram;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MiniJavaLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, DIV=27, OR=28, GT=29, AND=30, LT=31, PLUS=32, MINUS=33, 
		TIMES=34, NOT=35, LSB=36, RSB=37, LP=38, RP=39, RETURN=40, EQ=41, BooleanLiteral=42, 
		SC=43, Identifier=44, WS=45, MULTILINE_COMMENT=46, LINE_COMMENT=47, STRING=48, 
		CHAR=49, IntegerLiteral=50;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
			"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
			"T__25", "DIV", "OR", "GT", "AND", "LT", "PLUS", "MINUS", "TIMES", "NOT", 
			"LSB", "RSB", "LP", "RP", "RETURN", "EQ", "BooleanLiteral", "SC", "Identifier", 
			"WS", "MULTILINE_COMMENT", "LINE_COMMENT", "STRING", "CHAR", "IntegerLiteral", 
			"JavaLetter", "JavaLetterOrDigit", "DecimalIntegerLiteral", "IntegertypeSuffix", 
			"DecimalNumeral", "Digits", "Digit", "NonZeroDigit", "DigitsAndUnderscores", 
			"DigitOrUnderscore", "Underscores", "HEX_DIGIT", "ESC_SEQ", "OCTAL_ESC", 
			"UNICODE_ESC"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'class'", "'{'", "'}'", "'public'", "'static'", "'void'", "'main'", 
			"'String'", "'...'", "','", "'int'", "'boolean'", "'char'", "'double'", 
			"'do'", "'while'", "'break'", "'continue'", "'System.out.println'", "'if'", 
			"'else'", "'.length'", "'.charAt'", "'.'", "'new'", "'this'", "'/'", 
			"'||'", "'>'", "'&&'", "'<'", "'+'", "'-'", "'*'", "'!'", "'['", "']'", 
			"'('", "')'", "'return'", "'='", null, "';'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, "DIV", "OR", "GT", "AND", "LT", "PLUS", "MINUS", "TIMES", 
			"NOT", "LSB", "RSB", "LP", "RP", "RETURN", "EQ", "BooleanLiteral", "SC", 
			"Identifier", "WS", "MULTILINE_COMMENT", "LINE_COMMENT", "STRING", "CHAR", 
			"IntegerLiteral"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public MiniJavaLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MiniJava.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\64\u01d3\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t"+
		"=\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3"+
		"\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7"+
		"\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3"+
		"\n\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16"+
		"\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25"+
		"\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\32\3\33"+
		"\3\33\3\33\3\33\3\33\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\37\3\37\3\37"+
		"\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3)\3"+
		")\3)\3)\3)\3*\3*\3+\3+\3+\3+\3+\3+\3+\3+\3+\5+\u0149\n+\3,\3,\3-\3-\7"+
		"-\u014f\n-\f-\16-\u0152\13-\3.\6.\u0155\n.\r.\16.\u0156\3.\3.\3/\3/\3"+
		"/\3/\7/\u015f\n/\f/\16/\u0162\13/\3/\3/\3/\3/\3/\3\60\3\60\3\60\3\60\7"+
		"\60\u016d\n\60\f\60\16\60\u0170\13\60\3\60\3\60\3\60\3\60\3\61\3\61\3"+
		"\61\7\61\u0179\n\61\f\61\16\61\u017c\13\61\3\61\3\61\3\62\3\62\3\62\5"+
		"\62\u0183\n\62\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65\3\66\3\66\5\66"+
		"\u018f\n\66\3\67\3\67\38\38\38\58\u0196\n8\38\38\38\58\u019b\n8\58\u019d"+
		"\n8\39\39\59\u01a1\n9\39\59\u01a4\n9\3:\3:\5:\u01a8\n:\3;\3;\3<\6<\u01ad"+
		"\n<\r<\16<\u01ae\3=\3=\5=\u01b3\n=\3>\6>\u01b6\n>\r>\16>\u01b7\3?\3?\3"+
		"@\3@\3@\3@\5@\u01c0\n@\3A\3A\3A\3A\3A\3A\3A\3A\3A\5A\u01cb\nA\3B\3B\3"+
		"B\3B\3B\3B\3B\4\u0160\u016e\2C\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13"+
		"\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61"+
		"\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61"+
		"a\62c\63e\64g\2i\2k\2m\2o\2q\2s\2u\2w\2y\2{\2}\2\177\2\u0081\2\u0083\2"+
		"\3\2\13\5\2\13\f\17\17\"\"\4\2$$^^\4\2))^^\6\2&&C\\aac|\7\2&&\62;C\\a"+
		"ac|\4\2NNnn\3\2\63;\5\2\62;CHch\n\2$$))^^ddhhppttvv\2\u01d9\2\3\3\2\2"+
		"\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3"+
		"\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2"+
		"\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2"+
		"\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2"+
		"\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3"+
		"\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2"+
		"\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2"+
		"W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3"+
		"\2\2\2\2e\3\2\2\2\3\u0085\3\2\2\2\5\u008b\3\2\2\2\7\u008d\3\2\2\2\t\u008f"+
		"\3\2\2\2\13\u0096\3\2\2\2\r\u009d\3\2\2\2\17\u00a2\3\2\2\2\21\u00a7\3"+
		"\2\2\2\23\u00ae\3\2\2\2\25\u00b2\3\2\2\2\27\u00b4\3\2\2\2\31\u00b8\3\2"+
		"\2\2\33\u00c0\3\2\2\2\35\u00c5\3\2\2\2\37\u00cc\3\2\2\2!\u00cf\3\2\2\2"+
		"#\u00d5\3\2\2\2%\u00db\3\2\2\2\'\u00e4\3\2\2\2)\u00f7\3\2\2\2+\u00fa\3"+
		"\2\2\2-\u00ff\3\2\2\2/\u0107\3\2\2\2\61\u010f\3\2\2\2\63\u0111\3\2\2\2"+
		"\65\u0115\3\2\2\2\67\u011a\3\2\2\29\u011c\3\2\2\2;\u011f\3\2\2\2=\u0121"+
		"\3\2\2\2?\u0124\3\2\2\2A\u0126\3\2\2\2C\u0128\3\2\2\2E\u012a\3\2\2\2G"+
		"\u012c\3\2\2\2I\u012e\3\2\2\2K\u0130\3\2\2\2M\u0132\3\2\2\2O\u0134\3\2"+
		"\2\2Q\u0136\3\2\2\2S\u013d\3\2\2\2U\u0148\3\2\2\2W\u014a\3\2\2\2Y\u014c"+
		"\3\2\2\2[\u0154\3\2\2\2]\u015a\3\2\2\2_\u0168\3\2\2\2a\u0175\3\2\2\2c"+
		"\u017f\3\2\2\2e\u0186\3\2\2\2g\u0188\3\2\2\2i\u018a\3\2\2\2k\u018c\3\2"+
		"\2\2m\u0190\3\2\2\2o\u019c\3\2\2\2q\u019e\3\2\2\2s\u01a7\3\2\2\2u\u01a9"+
		"\3\2\2\2w\u01ac\3\2\2\2y\u01b2\3\2\2\2{\u01b5\3\2\2\2}\u01b9\3\2\2\2\177"+
		"\u01bf\3\2\2\2\u0081\u01ca\3\2\2\2\u0083\u01cc\3\2\2\2\u0085\u0086\7e"+
		"\2\2\u0086\u0087\7n\2\2\u0087\u0088\7c\2\2\u0088\u0089\7u\2\2\u0089\u008a"+
		"\7u\2\2\u008a\4\3\2\2\2\u008b\u008c\7}\2\2\u008c\6\3\2\2\2\u008d\u008e"+
		"\7\177\2\2\u008e\b\3\2\2\2\u008f\u0090\7r\2\2\u0090\u0091\7w\2\2\u0091"+
		"\u0092\7d\2\2\u0092\u0093\7n\2\2\u0093\u0094\7k\2\2\u0094\u0095\7e\2\2"+
		"\u0095\n\3\2\2\2\u0096\u0097\7u\2\2\u0097\u0098\7v\2\2\u0098\u0099\7c"+
		"\2\2\u0099\u009a\7v\2\2\u009a\u009b\7k\2\2\u009b\u009c\7e\2\2\u009c\f"+
		"\3\2\2\2\u009d\u009e\7x\2\2\u009e\u009f\7q\2\2\u009f\u00a0\7k\2\2\u00a0"+
		"\u00a1\7f\2\2\u00a1\16\3\2\2\2\u00a2\u00a3\7o\2\2\u00a3\u00a4\7c\2\2\u00a4"+
		"\u00a5\7k\2\2\u00a5\u00a6\7p\2\2\u00a6\20\3\2\2\2\u00a7\u00a8\7U\2\2\u00a8"+
		"\u00a9\7v\2\2\u00a9\u00aa\7t\2\2\u00aa\u00ab\7k\2\2\u00ab\u00ac\7p\2\2"+
		"\u00ac\u00ad\7i\2\2\u00ad\22\3\2\2\2\u00ae\u00af\7\60\2\2\u00af\u00b0"+
		"\7\60\2\2\u00b0\u00b1\7\60\2\2\u00b1\24\3\2\2\2\u00b2\u00b3\7.\2\2\u00b3"+
		"\26\3\2\2\2\u00b4\u00b5\7k\2\2\u00b5\u00b6\7p\2\2\u00b6\u00b7\7v\2\2\u00b7"+
		"\30\3\2\2\2\u00b8\u00b9\7d\2\2\u00b9\u00ba\7q\2\2\u00ba\u00bb\7q\2\2\u00bb"+
		"\u00bc\7n\2\2\u00bc\u00bd\7g\2\2\u00bd\u00be\7c\2\2\u00be\u00bf\7p\2\2"+
		"\u00bf\32\3\2\2\2\u00c0\u00c1\7e\2\2\u00c1\u00c2\7j\2\2\u00c2\u00c3\7"+
		"c\2\2\u00c3\u00c4\7t\2\2\u00c4\34\3\2\2\2\u00c5\u00c6\7f\2\2\u00c6\u00c7"+
		"\7q\2\2\u00c7\u00c8\7w\2\2\u00c8\u00c9\7d\2\2\u00c9\u00ca\7n\2\2\u00ca"+
		"\u00cb\7g\2\2\u00cb\36\3\2\2\2\u00cc\u00cd\7f\2\2\u00cd\u00ce\7q\2\2\u00ce"+
		" \3\2\2\2\u00cf\u00d0\7y\2\2\u00d0\u00d1\7j\2\2\u00d1\u00d2\7k\2\2\u00d2"+
		"\u00d3\7n\2\2\u00d3\u00d4\7g\2\2\u00d4\"\3\2\2\2\u00d5\u00d6\7d\2\2\u00d6"+
		"\u00d7\7t\2\2\u00d7\u00d8\7g\2\2\u00d8\u00d9\7c\2\2\u00d9\u00da\7m\2\2"+
		"\u00da$\3\2\2\2\u00db\u00dc\7e\2\2\u00dc\u00dd\7q\2\2\u00dd\u00de\7p\2"+
		"\2\u00de\u00df\7v\2\2\u00df\u00e0\7k\2\2\u00e0\u00e1\7p\2\2\u00e1\u00e2"+
		"\7w\2\2\u00e2\u00e3\7g\2\2\u00e3&\3\2\2\2\u00e4\u00e5\7U\2\2\u00e5\u00e6"+
		"\7{\2\2\u00e6\u00e7\7u\2\2\u00e7\u00e8\7v\2\2\u00e8\u00e9\7g\2\2\u00e9"+
		"\u00ea\7o\2\2\u00ea\u00eb\7\60\2\2\u00eb\u00ec\7q\2\2\u00ec\u00ed\7w\2"+
		"\2\u00ed\u00ee\7v\2\2\u00ee\u00ef\7\60\2\2\u00ef\u00f0\7r\2\2\u00f0\u00f1"+
		"\7t\2\2\u00f1\u00f2\7k\2\2\u00f2\u00f3\7p\2\2\u00f3\u00f4\7v\2\2\u00f4"+
		"\u00f5\7n\2\2\u00f5\u00f6\7p\2\2\u00f6(\3\2\2\2\u00f7\u00f8\7k\2\2\u00f8"+
		"\u00f9\7h\2\2\u00f9*\3\2\2\2\u00fa\u00fb\7g\2\2\u00fb\u00fc\7n\2\2\u00fc"+
		"\u00fd\7u\2\2\u00fd\u00fe\7g\2\2\u00fe,\3\2\2\2\u00ff\u0100\7\60\2\2\u0100"+
		"\u0101\7n\2\2\u0101\u0102\7g\2\2\u0102\u0103\7p\2\2\u0103\u0104\7i\2\2"+
		"\u0104\u0105\7v\2\2\u0105\u0106\7j\2\2\u0106.\3\2\2\2\u0107\u0108\7\60"+
		"\2\2\u0108\u0109\7e\2\2\u0109\u010a\7j\2\2\u010a\u010b\7c\2\2\u010b\u010c"+
		"\7t\2\2\u010c\u010d\7C\2\2\u010d\u010e\7v\2\2\u010e\60\3\2\2\2\u010f\u0110"+
		"\7\60\2\2\u0110\62\3\2\2\2\u0111\u0112\7p\2\2\u0112\u0113\7g\2\2\u0113"+
		"\u0114\7y\2\2\u0114\64\3\2\2\2\u0115\u0116\7v\2\2\u0116\u0117\7j\2\2\u0117"+
		"\u0118\7k\2\2\u0118\u0119\7u\2\2\u0119\66\3\2\2\2\u011a\u011b\7\61\2\2"+
		"\u011b8\3\2\2\2\u011c\u011d\7~\2\2\u011d\u011e\7~\2\2\u011e:\3\2\2\2\u011f"+
		"\u0120\7@\2\2\u0120<\3\2\2\2\u0121\u0122\7(\2\2\u0122\u0123\7(\2\2\u0123"+
		">\3\2\2\2\u0124\u0125\7>\2\2\u0125@\3\2\2\2\u0126\u0127\7-\2\2\u0127B"+
		"\3\2\2\2\u0128\u0129\7/\2\2\u0129D\3\2\2\2\u012a\u012b\7,\2\2\u012bF\3"+
		"\2\2\2\u012c\u012d\7#\2\2\u012dH\3\2\2\2\u012e\u012f\7]\2\2\u012fJ\3\2"+
		"\2\2\u0130\u0131\7_\2\2\u0131L\3\2\2\2\u0132\u0133\7*\2\2\u0133N\3\2\2"+
		"\2\u0134\u0135\7+\2\2\u0135P\3\2\2\2\u0136\u0137\7t\2\2\u0137\u0138\7"+
		"g\2\2\u0138\u0139\7v\2\2\u0139\u013a\7w\2\2\u013a\u013b\7t\2\2\u013b\u013c"+
		"\7p\2\2\u013cR\3\2\2\2\u013d\u013e\7?\2\2\u013eT\3\2\2\2\u013f\u0140\7"+
		"v\2\2\u0140\u0141\7t\2\2\u0141\u0142\7w\2\2\u0142\u0149\7g\2\2\u0143\u0144"+
		"\7h\2\2\u0144\u0145\7c\2\2\u0145\u0146\7n\2\2\u0146\u0147\7u\2\2\u0147"+
		"\u0149\7g\2\2\u0148\u013f\3\2\2\2\u0148\u0143\3\2\2\2\u0149V\3\2\2\2\u014a"+
		"\u014b\7=\2\2\u014bX\3\2\2\2\u014c\u0150\5g\64\2\u014d\u014f\5i\65\2\u014e"+
		"\u014d\3\2\2\2\u014f\u0152\3\2\2\2\u0150\u014e\3\2\2\2\u0150\u0151\3\2"+
		"\2\2\u0151Z\3\2\2\2\u0152\u0150\3\2\2\2\u0153\u0155\t\2\2\2\u0154\u0153"+
		"\3\2\2\2\u0155\u0156\3\2\2\2\u0156\u0154\3\2\2\2\u0156\u0157\3\2\2\2\u0157"+
		"\u0158\3\2\2\2\u0158\u0159\b.\2\2\u0159\\\3\2\2\2\u015a\u015b\7\61\2\2"+
		"\u015b\u015c\7,\2\2\u015c\u0160\3\2\2\2\u015d\u015f\13\2\2\2\u015e\u015d"+
		"\3\2\2\2\u015f\u0162\3\2\2\2\u0160\u0161\3\2\2\2\u0160\u015e\3\2\2\2\u0161"+
		"\u0163\3\2\2\2\u0162\u0160\3\2\2\2\u0163\u0164\7,\2\2\u0164\u0165\7\61"+
		"\2\2\u0165\u0166\3\2\2\2\u0166\u0167\b/\2\2\u0167^\3\2\2\2\u0168\u0169"+
		"\7\61\2\2\u0169\u016a\7\61\2\2\u016a\u016e\3\2\2\2\u016b\u016d\13\2\2"+
		"\2\u016c\u016b\3\2\2\2\u016d\u0170\3\2\2\2\u016e\u016f\3\2\2\2\u016e\u016c"+
		"\3\2\2\2\u016f\u0171\3\2\2\2\u0170\u016e\3\2\2\2\u0171\u0172\7\f\2\2\u0172"+
		"\u0173\3\2\2\2\u0173\u0174\b\60\2\2\u0174`\3\2\2\2\u0175\u017a\7$\2\2"+
		"\u0176\u0179\5\177@\2\u0177\u0179\n\3\2\2\u0178\u0176\3\2\2\2\u0178\u0177"+
		"\3\2\2\2\u0179\u017c\3\2\2\2\u017a\u0178\3\2\2\2\u017a\u017b\3\2\2\2\u017b"+
		"\u017d\3\2\2\2\u017c\u017a\3\2\2\2\u017d\u017e\7$\2\2\u017eb\3\2\2\2\u017f"+
		"\u0182\7)\2\2\u0180\u0183\5\177@\2\u0181\u0183\n\4\2\2\u0182\u0180\3\2"+
		"\2\2\u0182\u0181\3\2\2\2\u0183\u0184\3\2\2\2\u0184\u0185\7)\2\2\u0185"+
		"d\3\2\2\2\u0186\u0187\5k\66\2\u0187f\3\2\2\2\u0188\u0189\t\5\2\2\u0189"+
		"h\3\2\2\2\u018a\u018b\t\6\2\2\u018bj\3\2\2\2\u018c\u018e\5o8\2\u018d\u018f"+
		"\5m\67\2\u018e\u018d\3\2\2\2\u018e\u018f\3\2\2\2\u018fl\3\2\2\2\u0190"+
		"\u0191\t\7\2\2\u0191n\3\2\2\2\u0192\u019d\7\62\2\2\u0193\u019a\5u;\2\u0194"+
		"\u0196\5q9\2\u0195\u0194\3\2\2\2\u0195\u0196\3\2\2\2\u0196\u019b\3\2\2"+
		"\2\u0197\u0198\5{>\2\u0198\u0199\5q9\2\u0199\u019b\3\2\2\2\u019a\u0195"+
		"\3\2\2\2\u019a\u0197\3\2\2\2\u019b\u019d\3\2\2\2\u019c\u0192\3\2\2\2\u019c"+
		"\u0193\3\2\2\2\u019dp\3\2\2\2\u019e\u01a3\5s:\2\u019f\u01a1\5w<\2\u01a0"+
		"\u019f\3\2\2\2\u01a0\u01a1\3\2\2\2\u01a1\u01a2\3\2\2\2\u01a2\u01a4\5s"+
		":\2\u01a3\u01a0\3\2\2\2\u01a3\u01a4\3\2\2\2\u01a4r\3\2\2\2\u01a5\u01a8"+
		"\7\62\2\2\u01a6\u01a8\5u;\2\u01a7\u01a5\3\2\2\2\u01a7\u01a6\3\2\2\2\u01a8"+
		"t\3\2\2\2\u01a9\u01aa\t\b\2\2\u01aav\3\2\2\2\u01ab\u01ad\5y=\2\u01ac\u01ab"+
		"\3\2\2\2\u01ad\u01ae\3\2\2\2\u01ae\u01ac\3\2\2\2\u01ae\u01af\3\2\2\2\u01af"+
		"x\3\2\2\2\u01b0\u01b3\5s:\2\u01b1\u01b3\7a\2\2\u01b2\u01b0\3\2\2\2\u01b2"+
		"\u01b1\3\2\2\2\u01b3z\3\2\2\2\u01b4\u01b6\7a\2\2\u01b5\u01b4\3\2\2\2\u01b6"+
		"\u01b7\3\2\2\2\u01b7\u01b5\3\2\2\2\u01b7\u01b8\3\2\2\2\u01b8|\3\2\2\2"+
		"\u01b9\u01ba\t\t\2\2\u01ba~\3\2\2\2\u01bb\u01bc\7^\2\2\u01bc\u01c0\t\n"+
		"\2\2\u01bd\u01c0\5\u0083B\2\u01be\u01c0\5\u0081A\2\u01bf\u01bb\3\2\2\2"+
		"\u01bf\u01bd\3\2\2\2\u01bf\u01be\3\2\2\2\u01c0\u0080\3\2\2\2\u01c1\u01c2"+
		"\7^\2\2\u01c2\u01c3\4\62\65\2\u01c3\u01c4\4\629\2\u01c4\u01cb\4\629\2"+
		"\u01c5\u01c6\7^\2\2\u01c6\u01c7\4\629\2\u01c7\u01cb\4\629\2\u01c8\u01c9"+
		"\7^\2\2\u01c9\u01cb\4\629\2\u01ca\u01c1\3\2\2\2\u01ca\u01c5\3\2\2\2\u01ca"+
		"\u01c8\3\2\2\2\u01cb\u0082\3\2\2\2\u01cc\u01cd\7^\2\2\u01cd\u01ce\7w\2"+
		"\2\u01ce\u01cf\5}?\2\u01cf\u01d0\5}?\2\u01d0\u01d1\5}?\2\u01d1\u01d2\5"+
		"}?\2\u01d2\u0084\3\2\2\2\27\2\u0148\u0150\u0156\u0160\u016e\u0178\u017a"+
		"\u0182\u018e\u0195\u019a\u019c\u01a0\u01a3\u01a7\u01ae\u01b2\u01b7\u01bf"+
		"\u01ca\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}