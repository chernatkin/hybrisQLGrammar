grammar hybrisQL;

/*
 * Parser Rules
 */

select_query
: ( select_single | select_temp_table )
;

select_temp_table
: K_SELECT K_DISTINCT? ( OP_ATSK | temp_table_result_column ( OP_CMA temp_table_result_column )* )
  K_FROM OP_RBO subquery_full OP_RBC any_identifier
  ( K_WHERE temp_table_filter_expression )?
  ( K_GROUP K_BY temp_table_group_by_expression ( OP_CMA temp_table_group_by_expression )* )?
  ( K_HAVING temp_table_filter_expression ( OP_CMA temp_table_filter_expression )* )?
  ( K_ORDER K_BY temp_table_group_by_expression ( K_ASC | K_DESC )? ( OP_CMA temp_table_group_by_expression ( K_ASC | K_DESC )? )* )?
;

select_single
: K_SELECT K_DISTINCT? ( OP_ATSK | result_column ( OP_CMA result_column )* )
  K_FROM OP_CBO table_expression join_clause* OP_CBC
  ( K_WHERE filter_expression )?
  ( K_GROUP K_BY group_by_expression ( OP_CMA group_by_expression )* )?
  ( K_HAVING filter_expression ( OP_CMA filter_expression )* )?
  ( K_ORDER K_BY group_by_expression ( K_ASC | K_DESC )? ( OP_CMA group_by_expression ( K_ASC | K_DESC )? )* )?
;

group_by_expression
: field_reference
| function_call
;

temp_table_group_by_expression
: temp_table_field_reference
| temp_table_function_call
;

filter_expression
: filter_expression ( ( K_AND | K_OR ) filter_expression )+
| OP_RBO filter_expression OP_RBC
| K_NOT filter_expression
| expression ( ( OP_EQ | OP_NOT_EQ | OP_LT | OP_LE | OP_GT | OP_GE | K_NOT? K_LIKE ) expression )?
| expression K_IS K_NOT? K_NULL
| expression K_NOT? K_IN OP_RBO ( expression (OP_CMA expression)* | subquery_full ) OP_RBC
| expression K_NOT? K_EXISTS OP_RBO ( subquery_full ) OP_RBC
;

temp_table_filter_expression
: temp_table_filter_expression ( ( K_AND | K_OR ) temp_table_filter_expression )+
| OP_RBO temp_table_filter_expression OP_RBC
| K_NOT temp_table_filter_expression
| temp_table_expression ( ( OP_EQ | OP_NOT_EQ | OP_LT | OP_LE | OP_GT | OP_GE | K_NOT? K_LIKE ) temp_table_expression )?
| temp_table_expression K_IS K_NOT? K_NULL
| temp_table_expression K_NOT? K_IN OP_RBO ( temp_table_expression (OP_CMA temp_table_expression)* | subquery_full ) OP_RBC
| temp_table_expression K_NOT? K_EXISTS OP_RBO ( subquery_full ) OP_RBC
;

join_clause
: join_operator table_expression join_constraint
;

join_operator
: K_LEFT? K_JOIN
;

join_constraint
: K_ON filter_expression
;

table_expression
: any_identifier OP_EXCL? ( K_AS any_identifier )?
;

subquery_full
: subquery_single ( compound_operator subquery_single )*
;

subquery_single
: OP_CBO OP_CBO select_single OP_CBC OP_CBC
;

result_column
: expression ( K_AS any_identifier )?
;

temp_table_result_column
: temp_table_expression ( K_AS any_identifier )?
;

field_reference
: OP_CBO (any_identifier ( '.' | ':' ) )? any_identifier ( OP_SBO any_identifier OP_SBC )? ( ( '.' | ':' ) IDENTIFIER )? OP_CBC
;

temp_table_field_reference
: (any_identifier '.')? any_identifier
;

function_call
: any_identifier OP_RBO expression (OP_CMA expression)* OP_RBC
| any_identifier OP_RBO K_DISTINCT? expression OP_RBC
| any_identifier OP_RBO OP_ATSK OP_RBC
| any_identifier OP_RBO OP_RBC
;

temp_table_function_call
: any_identifier OP_RBO temp_table_expression (OP_CMA temp_table_expression)* OP_RBC
| any_identifier OP_RBO K_DISTINCT? temp_table_expression OP_RBC
| any_identifier OP_RBO OP_ATSK OP_RBC
| any_identifier OP_RBO OP_RBC
;

expression
: OP_RBO expression OP_RBC
| field_reference
| STRING_LITERAL
| signed_number
| bind_parameter
| function_call
| case_when_expression
;

temp_table_expression
: OP_RBO temp_table_expression OP_RBC
| temp_table_field_reference
| STRING_LITERAL
| signed_number
| bind_parameter
| temp_table_function_call
| temp_table_case_when_expression
;

case_when_expression
: K_CASE ( K_WHEN filter_expression K_THEN case_then_expression+ )+ K_ELSE case_then_expression K_END
;

case_then_expression
: ( expression | OP_RBO OP_CBO OP_CBO subquery_full OP_CBC OP_CBC OP_RBC )
;

temp_table_case_when_expression
: K_CASE ( K_WHEN filter_expression K_THEN temp_table_case_then_expression+ )+ K_ELSE temp_table_case_then_expression K_END
;

temp_table_case_then_expression
: ( temp_table_expression | OP_RBO OP_CBO OP_CBO subquery_full OP_CBC OP_CBC OP_RBC )
;

compound_operator
: K_UNION
| K_UNION K_ALL
;

signed_number
: ( '+' | '-' )? NUMERIC_LITERAL
;

bind_parameter
: '?' any_identifier
;

any_identifier
: IDENTIFIER
| any_keyword
;

any_keyword
: K_SELECT
| K_FROM
| K_AS
| K_JOIN
| K_ON
| K_LEFT
| K_WHERE
| K_ORDER
| K_BY
| K_ASC
| K_DESC
| K_DISTINCT
| K_OR
| K_AND
| K_IS
| K_NOT
| K_NULL
| K_IN
| K_EXISTS
| K_LIKE
| K_UNION
| K_ALL
| K_CASE
| K_WHEN
| K_THEN
| K_ELSE
| K_GROUP
| K_HAVING
| K_END
;


/*
 * Lexer Rules
 */


//keywords
K_SELECT : S E L E C T;
K_FROM : F R O M;
K_AS : A S;
K_JOIN : J O I N;
K_ON : O N;
K_LEFT :  L E F T;
K_WHERE : W H E R E;
K_ORDER : O R D E R;
K_BY : B Y;
K_ASC : A S C;
K_DESC : D E S C;
K_DISTINCT : D I S T I N C T;
K_OR : O R;
K_AND : A N D;
K_IS : I S;
K_NOT : N O T;
K_NULL :  N U L L;
K_IN : I N;
K_EXISTS : E X I S T S;
K_LIKE : L I K E;
K_UNION : U N I O N;
K_ALL : A L L;
K_CASE : C A S E;
K_WHEN : W H E N;
K_THEN : T H E N;
K_ELSE : E L S E;
K_GROUP : G R O U P; 
K_HAVING : H A V I N G;
K_END : E N D;


//operators
OP_EQ : '=';
OP_NOT_EQ : '!=' | '<>';
OP_LT : '<';
OP_LE : '<=';
OP_GT : '>';
OP_GE : '>=';
OP_CBO : '{';
OP_CBC : '}';
OP_RBO : '(';
OP_RBC : ')';
OP_SBO : '[';
OP_SBC : ']';
OP_EXCL : '!';
OP_ATSK : '*';
OP_CMA : ',';

IDENTIFIER
: [a-zA-Z_][a-zA-Z_0-9]*
;

NUMERIC_LITERAL
: DIGIT+
;

STRING_LITERAL
: '\'' ~'\''* '\''
;

WHITESPACE : [ \t\r\n] -> skip;

fragment DIGIT : [0-9];

fragment A : [aA];
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];
