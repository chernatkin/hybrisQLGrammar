package org.chernatkin.hybris.flexiblesearch.antlr;

import org.junit.Test;

public class SelectFromTest extends AbstractHybrisQLTest {

    @Test
    public void selectFrom() {
        final String query = "SELECT {vc.PK}, {vc.price} FROM {Order AS o JOIN VoucherCard AS vc ON {vc.order} = {o.pk}}";
        testQuery(query);
    }

    @Test
    public void selectFromAsterisk() {
        final String query = "SELECT * FROM {Order AS o JOIN VoucherCard AS vc ON {vc.order} = {o.pk}}";
        testQuery(query);
    }

    @Test
    public void selectFromDistinct() {
        final String query = "SELECT DISTINCT {vc.price} FROM {Order AS o JOIN VoucherCard AS vc ON {vc.order} = {o.pk}}";
        testQuery(query);
    }

    @Test
    public void selectFromWithoutAliases() {
        final String query = "SELECT {PK}, {price} FROM {Order}";
        testQuery(query);
    }

    @Test
    public void selectFromWithoutJoin() {
        final String query = "SELECT {o.PK} AS pk, {o.price} AS price FROM {Order AS o}";
        testQuery(query);
    }

    @Test
    public void selectFromWithSubquery() {
        final String query = "SELECT INNERTABLE.PK, INNERTABLE.CatCode FROM\n" +
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
                ") INNERTABLE";
        testQuery(query);
    }

    @Test
    public void selectFromWithSubqueryAndAsterisk() {
        final String query = "SELECT * FROM\n" +
                "(\n" +
                "   {{\n" +
                "      SELECT * FROM\n" +
                "      {\n" +
                "         Product AS p JOIN CategoryProductRelation AS rel\n" +
                "         ON {p:PK} = {rel:target}\n" +
                "         JOIN Category AS c\n" +
                "         ON {rel:source} = {c:PK}\n" +
                "      }\n" +
                "   }}\n" +
                ") INNERTABLE";
        testQuery(query);
    }

    @Test
    public void selectFromWithSubqueryAndAliasses() {
        final String query = "SELECT INNERTABLE.PK AS pk, INNERTABLE.CatCode as code FROM\n" +
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
                ") INNERTABLE";
        testQuery(query);
    }

    @Test
    public void selectFromWithSubqueryAndFunctions() {
        final String query = "SELECT TO_CHAR(INNERTABLE.PK, 10), LOWER(INNERTABLE.CatCode), RANDOM() FROM\n" +
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
                ") INNERTABLE";
        testQuery(query);
    }

    @Test
    public void selectFromWithSubqueryWithUnion() {
        final String query = "SELECT uniontable.PK, uniontable.CODE FROM\r\n" + 
                "(\r\n" + 
                "   {{\r\n" + 
                "      SELECT {c:PK} as PK, {c:code} AS CODE FROM {Chapter AS c}\r\n" + 
                "      WHERE {c:PUBLICATION} LIKE ?pk\r\n" + 
                "   }}\r\n" + 
                "   UNION ALL\r\n" + 
                "   {{\r\n" + 
                "      SELECT {p:PK} as PK, {p:code} AS CODE FROM {Page AS p}\r\n" + 
                "      WHERE {p:PUBLICATION} LIKE ?pk\r\n" + 
                "   }}\r\n" + 
                ") uniontable";
        testQuery(query);
    }
    
    @Test
    public void selectFromJoinWithOnExpression() {
        final String query = "SELECT {dm.code}, {pm.code} " + 
                "    FROM " + 
                "    {" + 
                "       DeliveryMode AS dm JOIN PaymentMode AS pm " + 
                "       ON {dm.supportedpaymentmodes} LIKE CONCAT( '%', CONCAT( {pm.PK} , '%' ) )" + 
                "    }";
        testQuery(query);
    }

    @Test
    public void selectFromWithFunctions() {
        final String query = "SELECT DATE_FORMAT({o:date},'%M/%Y'), COUNT(DISTINCT{o:PK}) FROM {Order AS o}";
        testQuery(query);
    }

    @Test
    public void selectFromWithCountAsreriskFunction() {
        final String query = "SELECT DATE_FORMAT({o:date},'%M/%Y'), COUNT(*) FROM {Order AS o}";
        testQuery(query);
    }

    @Test
    public void selectFromCaseWhen() {
        final String query = "SELECT {vc.PK}, CASE WHEN {vc.price} = 0 THEN 'FREE' WHEN {vc.price} < 10 THEN ROUND({vc.price}) ELSE 'EXPENSIVE' END "
                + "FROM {Order AS o JOIN VoucherCard AS vc ON {vc.order} = {o.pk}}";
        testQuery(query);
    }

    @Test
    public void selectFromCaseWhenWithBrackets() {
        final String query = "SELECT {vc.PK}, (CASE WHEN (0 = {vc.price}) THEN ('FREE') WHEN (IS_POSITIVE({vc.price})) THEN (ROUND({vc.price})) ELSE (RANDOM()) END) AS price "
                + "FROM {Order AS o JOIN VoucherCard AS vc ON {vc.order} = {o.pk}}";
        testQuery(query);
    }
}
