package org.chernatkin.hybris.flexiblesearch.antlr;

import org.junit.Test;

public class SelectGroupByTest extends AbstractHybrisQLTest {

    @Test
    public void selectGroupByFields() {
        final String query = "SELECT {p.name}, {p.code}, COUNT(1) FROM {Product AS p} WHERE {p.code} = ?code "
                           + "GROUP BY {p.name}, {p.code}";
        testQuery(query);
    }

    @Test
    public void selectGroupByFunction() {
        final String query = "SELECT DATE({p.creation_time}), COUNT({p.pk}) FROM {Product AS p} "
                           + "GROUP BY DATE({p.creation_time})";
        testQuery(query);
    }

    @Test
    public void selectGroupByHavingFields() {
        final String query = "SELECT {p.name}, {p.code}, COUNT(1) FROM {Product AS p} WHERE {p.code} = ?code "
                           + "GROUP BY {p.name}, {p.code} "
                           + "HAVING COUNT(1) < 10";
        testQuery(query);
    }

    @Test
    public void selectGroupByHavingFunction() {
        final String query = "SELECT DATE({p.creation_time}), COUNT({p.pk}) FROM {Product AS p} "
                           + "GROUP BY DATE({p.creation_time}) "
                           + "HAVING COUNT(1) < 10 AND COUNT(1) >= 0 OR COUNT(*) > 100";
        testQuery(query);
    }

    @Test
    public void selectGroupByWithSubquery() {
        final String query = "SELECT INNERTABLE.CatCode, COUNT(*) FROM\n" +
                "(\n" +
                "   {{\n" +
                "      SELECT {p:PK} AS PK, {c:code} AS CatCode FROM\n" +
                "      {\n" +
                "         Product AS p JOIN CategoryProductRelation AS rel\n" +
                "         ON {p:PK} = {rel:target}\n" +
                "         JOIN Category AS c\n" +
                "         ON {rel:source} = {c:PK}\n" +
                "      }\n" +
                "   }}\n" +
                ") INNERTABLE "
                + "GROUP BY INNERTABLE.CatCode";
        testQuery(query);
    }

    @Test
    public void selectGroupByFunctionWithSubquery() {
        final String query = "SELECT INNERTABLE.CatCode, SIGN(INNERTABLE.pk), COUNT(INNERTABLE.pk) FROM\n" +
                "(\n" +
                "   {{\n" +
                "      SELECT {p:PK} AS PK, {c:code} AS CatCode FROM\n" +
                "      {\n" +
                "         Product AS p JOIN CategoryProductRelation AS rel\n" +
                "         ON {p:PK} = {rel:target}\n" +
                "         JOIN Category AS c\n" +
                "         ON {rel:source} = {c:PK}\n" +
                "      }\n" +
                "   }}\n" +
                ") INNERTABLE "
                + "GROUP BY INNERTABLE.CatCode, SIGN(INNERTABLE.pk)";
        testQuery(query);
    }

    @Test
    public void selectGroupByHavingWithSubquery() {
        final String query = "SELECT INNERTABLE.CatCode, SIGN(INNERTABLE.pk), COUNT(INNERTABLE.pk) FROM\n" +
                "(\n" +
                "   {{\n" +
                "      SELECT {p:PK} AS PK, {c:code} AS CatCode FROM\n" +
                "      {\n" +
                "         Product AS p JOIN CategoryProductRelation AS rel\n" +
                "         ON {p:PK} = {rel:target}\n" +
                "         JOIN Category AS c\n" +
                "         ON {rel:source} = {c:PK}\n" +
                "      }\n" +
                "   }}\n" +
                ") INNERTABLE "
                + "GROUP BY INNERTABLE.CatCode, SIGN(INNERTABLE.pk) "
                + "HAVING COUNT(INNERTABLE.pk) > 1";
        testQuery(query);
    }
}
