package org.chernatkin.hybris.flexiblesearch.antlr;

import org.junit.Test;

public class SelectGroupByTest extends AbstractHybrisQLTest {

    @Test
    public void selectGroupByFields() {
        final String query = "SELECT * FROM {Product AS p} WHERE {p.code} = ?code GROUP BY {p.name}, {p.name2}";
        testQuery(query);
    }

    @Test
    public void selectGroupByFunction() {
        final String query = "SELECT DATE({p.creation_time}), COUNT({p.pk}) FROM {Product AS p} GROUP BY DATE({p.creation_time})";
        testQuery(query);
    }

}
