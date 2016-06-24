@header {

    import java.util.HashMap;
}

@members {
  HashMap<String, Integer> memory = new HashMap<String, Integer>();
}

prog
    : expr
    {
        System.out.println($expr.val);
    }
    |
    {
        System.out.println("empty");
    }
    ;

expr returns [int val]
    : term exprP[$term.val]
    {
        $val = $exprP.val;
    }
    ;
exprP[int i] returns [int val]
    :
    {
        $val = $i;
    }
    | '+' term e = exprP[$i + $term.val]
    {
        $val = $e.val;
    }
    | '-' term e = exprP[$i - $term.val]
    {
        $val = $e.val;
    }
    ;

term returns [int val]
    : fact termP[$fact.val]
    {
        $val = $termP.val;
    }
    ;
termP[int i] returns [int val]
    :
    {
        $val = $i;
    }
    | '*' fact e = termP[$i * $fact.val]
    {
        $val = $e.val;
    }
    | '/' fact e = termP[$i / $fact.val]
    {
        $val = $e.val;
    }
    ;

fact returns [int val]
    : '-' last
    {
        $val = (-1) * $last.val;
    }
    | last
    {
        $val = $last.val;
    }
    ;

last returns [int val]
    : atom lastP[$atom.val]
    {
        $val = $lastP.val;
    }
    ;

lastP[int i] returns [int val]
    :
    {
        $val = $i;
    }
    | '^' e = expr
    {
        $val = (int)Math.pow((double)$i, (double)$e.val);
    }
    ;

atom returns [int val]
    : NUM
    {
        $val = Integer.parseInt($NUM.text);
    }
    | '(' expr ')'
    {
        $val = $expr.val;
    }
    ;

NUM:    '[0-9]+' ;