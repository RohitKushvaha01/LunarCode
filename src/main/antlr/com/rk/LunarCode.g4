grammar LunarCode;

// Parser rules
program     : statement+ ;

statement   : assignment     # assignmentStmt
            | printStatement # printStmt
            ;

assignment  : IDENTIFIER '=' expression ;

functionCall : IDENTIFIER '(' argumentList? ')' ;
argumentList : expression (',' expression)* ;

expression  : term (('+'|'-') term)* ;

term        : factor (('*'|'/') factor)* ;

factor      : INT
            | STRING
            | IDENTIFIER
            | '(' expression ')' ;

printStatement : 'print' '(' id=IDENTIFIER ')';

// Lexer rules
IDENTIFIER  : [a-zA-Z_][a-zA-Z_0-9]* ;
INT         : [0-9]+ ;
STRING      : '"' ( ~["\r\n\\] | '\\' [\\tnr"] )* '"' ;

// Skip whitespace and newlines
WS          : [ \t\r\n]+ -> skip ;
