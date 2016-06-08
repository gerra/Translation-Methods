grammar Exprs;

@header {
    import java.util.HashMap;
}

@members {
  HashMap<String, Integer> memory = new HashMap<String, Integer>();
}

assignments : (assignment ';')* ;

assignment returns [String assignmentAsString] 
        : NAME '=' expr 
        {
            String varName = $NAME.text;
            int varValue = $expr.val; 
            memory.put(varName, varValue);
            $assignmentAsString = varName + " = " + varValue;
            System.out.println($assignmentAsString); 
        }
        ;
    
expr returns [int val]
    : term exprP[$term.val]                         { $val = $exprP.val; }
    ;
exprP[int i] returns [int val]
    :                                               { $val = $i; }
    | '+' term e = exprP[$i + $term.val]            { $val = $e.val; }
    | '-' term e = exprP[$i - $term.val]            { $val = $e.val; }
    ;
term returns [int val]
    : fact termP[$fact.val]                         { $val = $termP.val; }
    ;
termP[int i] returns [int val]
    :                                               { $val = $i; }
    | '*' fact e = termP[$i * $fact.val]            { $val = $e.val; }
    | '/' fact e = termP[$i / $fact.val]            { $val = $e.val; }
    ;
    
fact returns [int val]
    : last                                          { $val = $last.val; }
    | '-' last                                      { $val = (-1) * $last.val; }
    ;
    
last returns [int val]
    : atom lastP[$atom.val] { $val = $lastP.val; }
    ;
    
lastP[int i] returns [int val]
    : '^' e = last { $val = (int)Math.pow((double)$i, (double)$e.val); }
    | { $val = $i; }

    ;
atom returns [int val]
    : '(' expr ')'                                  { $val = $expr.val; }
    | NUM                                           { $val = Integer.parseInt($NUM.text); }
    | NAME                                          { $val = memory.get($NAME.text); }
    ;

WS : [ \t \r \n]+ -> skip ;
NUM : [0-9]+ ;
NAME: [_a-zA-Z][_a-zA-Z0-9]* ;
