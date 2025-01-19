grammar LunarCode;

// Parser rules
program         : statement+ ;

statement       : assignment     # assignmentStmt
                | functionDecl   # functionDeclStmt
                | functionCall   # functionCallStmt
                ;

assignment      : IDENTIFIER '=' expression ;

functionCall    : IDENTIFIER '(' argumentList? ')' ;

functionDecl    : 'fun' IDENTIFIER '(' parameterList? ')' '{' statement* '}' ;

parameterList   : IDENTIFIER (',' IDENTIFIER)* ;

argumentList    : expression (',' expression)* ;

expression      : term (('+'|'-') term)* ;

term            : factor (('*'|'/') factor)* ;

factor          : INT
                | STRING
                | IDENTIFIER
                | '(' expression ')' ;

// Lexer rules
IDENTIFIER      : [a-zA-Z_][a-zA-Z_0-9]* ;
INT             : [0-9]+ ;
STRING          : '"' ( ~["\r\n\\] | '\\' [\\tnr"] )* '"' ;

// Skip whitespace and newlines
WS              : [ \t\r\n]+ -> skip ;
LINE_COMMENT    : '//' ~[\r\n]* -> skip ; // Single-line comment
BLOCK_COMMENT   : '/*' .*? '*/' -> skip ; // Multi-line comment
