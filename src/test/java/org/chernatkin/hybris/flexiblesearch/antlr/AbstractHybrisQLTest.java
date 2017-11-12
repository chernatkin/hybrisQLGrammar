package org.chernatkin.hybris.flexiblesearch.antlr;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.chernatkin.hybris.flexiblesearch.antlr.grammar.hybrisQLLexer;
import org.chernatkin.hybris.flexiblesearch.antlr.grammar.hybrisQLParser;
import org.chernatkin.hybris.flexiblesearch.antlr.grammar.hybrisQLParser.Select_singleContext;
import org.junit.Assert;


public abstract class AbstractHybrisQLTest {
    

    protected void testQuery(final String query) {

        final Lexer lexer = new hybrisQLLexer(CharStreams.fromString(query));

        final TokenStream tokenStream = new CommonTokenStream(lexer);

        final hybrisQLParser parser = new hybrisQLParser(tokenStream);
        parser.addErrorListener(new TestANTLRErrorListener());

        final Select_singleContext equationContext = parser.select_single();

        Assert.assertEquals(query.replaceAll("[\\s]", ""), equationContext.getText());
    }

}
