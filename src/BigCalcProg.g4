grammar BigCalcProg;

program
        : statement+ EOF
        ;

statement
        : assignmentStatement
        | expressionStatement
        ;

assignmentStatement
        : Variable '=' expression ';'
        ;

expressionStatement
        : expression ';'
        ;

expression  
        : expression op=('*' | '/') expression  # mulDiv
        | expression op=('+' | '-') expression  # addSub
        | '(' expression ')'                    # parenExpr
        | Variable                              # variable
        | Number                                # num
        ;

Variable
        : [a-zA-Z] [a-zA-Z0-9]*
        ;

Number  
        : Digit* '.' Digit+
        | Digit+
        ;

Digit   
        : [0-9]
        ;

WS
        : [ \t\r\n\u000C]+ -> skip
        ;

COMMENT
        :   '/*' .*? '*/' -> skip
        ;

LINE_COMMENT
        : '//' ~[\r\n]* -> skip 
        ;
