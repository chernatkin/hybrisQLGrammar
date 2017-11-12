package org.chernatkin.hybris.flexiblesearch.antlr;

import java.util.BitSet;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

public class TestANTLRErrorListener implements ANTLRErrorListener {

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
            int line, int charPositionInLine, String msg,
            RecognitionException e) {
        throw new IllegalArgumentException("line[" + line + ":" + charPositionInLine + "] " + msg, e);
    }

    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa,
            int startIndex, int stopIndex, int prediction,
            ATNConfigSet configs) {
        throw new RuntimeException();
    }

    @Override
    public void reportAttemptingFullContext(Parser recognizer, DFA dfa,
            int startIndex, int stopIndex, BitSet conflictingAlts,
            ATNConfigSet configs) {
    }

    @Override
    public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex,
            int stopIndex, boolean exact, BitSet ambigAlts,
            ATNConfigSet configs) { 
    }
}
