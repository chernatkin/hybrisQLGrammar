package org.chernatkin.hybris.flexiblesearch.antlr;

import org.junit.Test;

public class SelectOrderByTest extends AbstractHybrisQLTest {

    @Test
    public void selectOrderByFields() {
        final String query = "SELECT * FROM {Product AS p} WHERE {p.code} = ?code GROUP BY {p.name}, {p.name2} ORDER BY {p.name}, {p.name2}";
        testQuery(query);
    }

    @Test
    public void selectOrderByAscAndDesc() {
        final String query = "SELECT * FROM {Product AS p} ORDER BY {p.name} ASC, {p.name2} DESC";
        testQuery(query);
    }

    @Test
    public void selectOrderByFunction() {
        final String query = "SELECT * FROM {Product AS p} ORDER BY TRIM({p.name}) ASC, SUBSTRING({p.name2}, 2) DESC";
        testQuery(query);
    }
}
