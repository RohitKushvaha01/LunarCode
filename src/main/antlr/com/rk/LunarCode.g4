grammar LunarCode;

options {
    language = Java;
}

// Lexer rules
IMPORT: 'import';
FROM: 'from';
STATIC: 'static';
NATIVE: 'native';
MATH: 'math';
LAZY: 'lazy';
DEFAULT: 'default';
PURE: 'pure';
FUN: 'fun';
VAL: 'val';
INLINE: 'inline';
CACHE: 'cache';
MODULE: 'module';
TRAIT: 'trait';
CLASS: 'class';
OVERRIDE: 'override';
MATCH: 'match';
IS: 'is';
ELSE: 'else';
RETURN: 'return';
THREAD: 'thread';
VIRTUAL: 'virtual';
PARALLEL: 'parallel';
FOR: 'for';
IN: 'in';
TO: 'to';
IF: 'if';
TRY: 'try';
FINALLY: 'finally';
SYSTEM: 'system';
LOAD: 'load';
GC: 'gc';
FREE: 'free';
DELAY: 'delay';

ID: [a-zA-Z_][a-zA-Z_0-9]*;
NUMBER: [0-9]+;
STRING: '"' .*? '"';
COMMENT: '//' ~[\r\n]* -> skip;
WHITESPACE: [ \t\r\n]+ -> skip;
ARROW: '->';
ASSIGN: '=';
COLON: ':';
SEMI: ';';
LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
LSQUARE: '[';
RSQUARE: ']';
COMMA: ',';
DOT: '.';
PLUS: '+';
MINUS: '-';
MULT: '*';
DIV: '/';
MOD: '%';
EQ: '==';
NEQ: '!=';
GT: '>';
LT: '<';
GE: '>=';
LE: '<=';
NOT: '!';

// Parser rules
compilationUnit: statement*;

statement
    : importStatement
    | functionDeclaration
    | valDeclaration
    | moduleDeclaration
    | traitDeclaration
    | classDeclaration
    | matchExpression
    | threadDeclaration
    | parallelForLoop
    | freeFunction
    ;

importStatement
    : IMPORT (FROM ID | ID | stdioImport) (DOT ID)* ('hash' STRING)?
    ;

stdioImport
    : 'stdio'
    ;
functionDeclaration
    : (DEFAULT | PURE | MATH | LAZY | CACHE | INLINE)? FUN returnType ID paramList functionBody
    ;


returnType
    : ID
    ;

paramList
    : LPAREN (param (COMMA param)*)? RPAREN
    ;

param
    : ID COLON ID
    ;

functionBody
    : LBRACE statement* RBRACE
    ;

valDeclaration
    : VAL ID ASSIGN expression
    ;

moduleDeclaration
    : MODULE ID LBRACE statement* RBRACE
    ;

traitDeclaration
    : TRAIT ID LBRACE traitMember* RBRACE
    ;

traitMember
    : functionDeclaration
    ;

classDeclaration
    : CLASS ID (COLON ID)? LBRACE classBody* RBRACE
    ;

classBody
    : functionDeclaration
    | valDeclaration
    | overrideFunction
    ;

overrideFunction
    : OVERRIDE FUN returnType ID paramList functionBody
    ;


matchExpression
    : MATCH expression LBRACE matchCase* RBRACE
    ;

matchCase
    : IS returnType ARROW statement
    | ELSE ARROW statement
    ;

threadDeclaration
    : THREAD LBRACE statement* RBRACE (DOT VIRTUAL)?
    ;

parallelForLoop
    : PARALLEL FOR ID IN expression TO expression LBRACE statement* RBRACE
    ;

freeFunction
    : FREE LPAREN ID RPAREN
    | FREE LPAREN cacheFunction RPAREN
    ;

cacheFunction
    : ID
    ;

// Modified expression rule with precedence levels
expression
    : primaryExpression (binaryOperator primaryExpression)*
    ;

primaryExpression
    : ID
    | STRING
    | NUMBER
    | functionCall
    | LPAREN expression RPAREN
    ;

binaryOperator
    : PLUS
    | MINUS
    | MULT
    | DIV
    | MOD
    ;

functionCall
    : ID LPAREN (paramList)? RPAREN
    ;

