package org.chernatkin.hybris.flexiblesearch.antlr;

import org.junit.Test;

public class SelectWhereTest extends AbstractHybrisQLTest {

    @Test
    public void selectWhereTest() {
        final String query = "SELECT {p1.PK}, {p2.PK} "
                + "FROM {Product AS p1} "
                + "WHERE {p1.PK} <> {p2.PK} "
                  + "AND {p1.PK} != {p2.PK} "
                   + "OR {p1.code} = ?code "
                  + "AND {p2.code} IS NULL "
                  + "AND {p1.code} IS NOT NULL "
                   + "OR {p1.index} > 2 "
                   + "AND {p2.index} >= +2 "
                   + "AND {p1.index} < -5 "
                   + "AND {p2.index} <= -5 "
                   + "AND {p1.name} LIKE '%name%' "
                   + "AND {p2.name} NOT LIKE '%test%'";
        testQuery(query);
    }

    @Test
    public void selectWhereWithBracketsTest() {
        final String query = "SELECT {p1.PK}, {p2.PK} "
                + "FROM {Product AS p1} "
                + "WHERE {p1.PK} <> {p2.PK} "
                  + "AND ({p1.PK} != {p2.PK} OR ({p1.code} = ?code AND {p2.code} IS NULL)) "
                  + "AND ({p1.code} IS NOT NULL) "
                   + "OR ({p1.index} > 2 "
                   + "AND {p2.index} >= +2 "
                   + "AND {p1.index} < -5 "
                   + "AND {p2.index} <= -5 "
                   + "AND {p1.name} LIKE '%name%' "
                   + "AND {p2.name} NOT LIKE '%test%')";
        testQuery(query);
    }

    @Test
    public void selectWhereInValues() {
        final String query = "SELECT * FROM {Product AS p} WHERE {p.pk} IN ('a', 'b', 'c')";
        testQuery(query);
    }

    @Test
    public void selectWhereNotInValues() {
        final String query = "SELECT * FROM {Product AS p} WHERE {p.pk} NOT IN (1, 2, 3)";
        testQuery(query);
    }

    @Test
    public void selectWhereNotInParam() {
        final String query = "SELECT * FROM {Product AS p} WHERE {p.pk} IN (?pks)";
        testQuery(query);
    }

    @Test
    public void selectWhereInSubquery() {
        final String query = "SELECT * FROM {Product AS p} WHERE {p.pk} NOT IN ({{ SELECT {product} FROM {Order} }})";
        testQuery(query);
    }

    @Test
    public void selectWhereExistsSubquery() {
        final String query = "SELECT * FROM {Product AS p} WHERE {p.pk} EXISTS ({{ SELECT {product} FROM {Order} }})";
        testQuery(query);
    }

    @Test
    public void selectWhereNotExistsSubquery() {
        final String query = "SELECT * FROM {Product AS p} WHERE {p.pk} NOT EXISTS ({{ SELECT {product} FROM {Order} }})";
        testQuery(query);
    }

    @Test
    public void selectWhereWithFunctions() {
        final String query = "SELECT * FROM {Product AS p} WHERE DATE({p.creation_time}) >= DATE(MINUS_MONTH({p.end_time}, 2), 'flag')";
        testQuery(query);
    }
}
