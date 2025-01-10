grammar LunarCode;

program: statement* EOF;

statement
    : variableDeclaration
    | functionDeclaration
    | expressionStatement
    | block
    ;

variableDeclaration
    : 'var' ID '=' expression ';'
    ;

functionDeclaration
    : 'fun' ID '(' parameterList? ')' block
    ;

parameterList
    : ID (',' ID)*
    ;

expressionStatement
    : expression ';'
    ;

expression
    : primary
    | functionCall
    | ID
    | NUMBER
    | STRING
    ;

functionCall
    : ID '(' argumentList? ')'
    ;

argumentList
    : expression (',' expression)* // List of expressions, separated by commas
    ;

block
    : '{' statement* '}'
    ;

primary
    : '(' expression ')'
    | ID
    | NUMBER
    | STRING
    ;

// Lexer Rules
ID: [a-zA-Z_][a-zA-Z0-9_]*;
NUMBER: [0-9]+('.'[0-9]+)?;
STRING: '"' (~["\\] | '\\' .)* '"'; // Improved STRING rule to handle commas and escape sequences.
WS: [ \t\r\n]+ -> skip;
COMMENT: '//' .*? '\r'? '\n' -> skip;
