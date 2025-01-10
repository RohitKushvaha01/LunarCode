grammar LunarCode;

// Lexer Rules
IMPORT      : 'import';
STATIC      : 'static';
NATIVE      : 'native';
FUN         : 'fun';
PURE        : 'pure';
MATCH       : 'match';
IS          : 'is';
ELSE        : 'else';
MODULE      : 'module';
TRAIT       : 'trait';
CLASS       : 'class';
OVERRIDE    : 'override';
THREAD      : 'thread';
FREE        : 'free';
VAL         : 'val';
RETURN      : 'return';
IF          : 'if';
ELSE_IF     : 'else if';
FOR         : 'for';
WHILE       : 'while';
DO          : 'do';
BREAK       : 'break';
CONTINUE    : 'continue';

LPAREN      : '(';
RPAREN      : ')';
LBRACE      : '{';
RBRACE      : '}';
LBRACK      : '[';
RBRACK      : ']';
ARROW       : '->';
COLON       : ':';
SEMICOLON   : ';';
COMMA       : ',';
DOT         : '.';
EQUALS      : '=';
ASSIGN      : ':=';
PLUS        : '+';
MINUS       : '-';
STAR        : '*';
SLASH       : '/';
MODULO      : '%';
GT          : '>';
LT          : '<';
GTE         : '>=';
LTE         : '<=';
EQ          : '==';
NEQ         : '!=';
AND         : '&&';
OR          : '||';
NOT         : '!';

ID          : [a-zA-Z_][a-zA-Z0-9_]*;
INT         : [0-9]+;
STRING      : '"' .*? '"';
WS          : [ \t\r\n]+ -> skip;
COMMENT     : '//' .*? '\n' -> skip;

// Parser Rules
program     : statement*;

statement
    : importStatement
    | staticBlock
    | functionDeclaration
    | nativeFunctionDeclaration
    | traitDeclaration
    | classDeclaration
    | moduleDeclaration
    | threadStatement
    | variableDeclaration
    | matchStatement
    | controlStatement
    | expressionStatement
    ;

importStatement
    : IMPORT ID (DOT ID)* SEMICOLON
    ;

staticBlock
    : STATIC LBRACE statement* RBRACE
    ;

functionDeclaration
    : (PURE)? FUN ID (LPAREN parameterList? RPAREN)? (COLON type)? block
    ;

nativeFunctionDeclaration
    : NATIVE FUN ID type ID DOT ID LPAREN parameterList? RPAREN SEMICOLON
    ;

traitDeclaration
    : TRAIT ID LBRACE functionDeclaration* RBRACE
    ;

classDeclaration
    : CLASS ID (COLON ID)? LBRACE (OVERRIDE functionDeclaration | functionDeclaration | variableDeclaration)* RBRACE
    ;

moduleDeclaration
    : MODULE ID LBRACE statement* RBRACE
    ;

threadStatement
    : THREAD LBRACE statement* RBRACE
    ;

variableDeclaration
    : VAL ID (COLON type)? (EQUALS expression)? SEMICOLON
    ;

matchStatement
    : MATCH expression LBRACE matchCase* (ELSE ARROW expressionStatement)? RBRACE
    ;

matchCase
    : IS type ARROW expressionStatement
    ;

controlStatement
    : ifStatement
    | forLoop
    | whileLoop
    | doWhileLoop
    | BREAK SEMICOLON
    | CONTINUE SEMICOLON
    ;

ifStatement
    : IF LPAREN expression RPAREN block (ELSE_IF LPAREN expression RPAREN block)* (ELSE block)?
    ;

forLoop
    : FOR LPAREN (variableDeclaration | expression)? SEMICOLON expression? SEMICOLON expression? RPAREN block
    ;

whileLoop
    : WHILE LPAREN expression RPAREN block
    ;

doWhileLoop
    : DO block WHILE LPAREN expression RPAREN SEMICOLON
    ;

expressionStatement
    : expression SEMICOLON
    ;

block
    : LBRACE statement* RBRACE
    ;

expression
    : primary
    | expression operator=('*'|'/') expression
    | expression operator=('+'|'-') expression
    | expression operator=('>'|'>='|'<'|'<='|'=='|'!=') expression
    | expression operator=('&&'|'||') expression
    ;

primary
    : INT
    | STRING
    | ID
    | ID LPAREN argumentList? RPAREN
    | LPAREN expression RPAREN
    ;

argumentList
    : expression (COMMA expression)*
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : ID COLON type
    ;

type
    : ID
    ;
