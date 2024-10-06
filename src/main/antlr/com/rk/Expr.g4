grammar Expr;

prog:   expr+ ;  // Program consists of one or more expressions
expr:   expr ('+'|'-') expr   # addSubExpr
    |   INT                    # intExpr
    ;

INT : [0-9]+ ;  // Define integers
WS  : [ \t\r\n]+ -> skip ;  // Skip whitespace
