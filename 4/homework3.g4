@header {
    import java.lang.StringBuilder;
    import misc.Utils;
}

prog[String begin, String end] returns [String s]
    : b=body[begin, end, 1]
    {
        $s = $begin + $b.s + "\n" + $end;
    }
    ;

body[String begin, String end, int shift] returns [String s]
    :
    {
        $s = "";
    }
    | i=IF c=condition e=exprs[begin, end, shift + 1] ifp=ifP[begin, end, shift]
    {
        $s = "\n" + Utils.genTab($shift) + $i.text + " " + $c.s +
        " " + $begin + "\n" + $e.s + "\n" + Utils.genTab($shift) + $end + $ifp.s;
    }
    | e=exprs[begin, end, shift] b=body[begin, end, shift] {
        $s = "\n" + $e.s + $b.s;
    }
    ;

ifP[String begin, String end, int shift] returns [String s]
    : i=elif c=condition e=exprs[begin, end, shift + 1] ifp=ifP[begin, end, shift]
    {
        $s = " else if " + $c.s + " " + $begin + "\n" + $e.s + "\n" + Utils.genTab($shift) + $end + $ifp.s;
    }
    | el=ELSE ex=exprs[begin, end, shift + 1] b=body[begin, end, shift]
    {
        $s = " else " + $begin + "\n" + $ex.s + "\n" + Utils.genTab($shift) + $end + $b.s;
    }
    | b=body[begin, end, shift]
    {
        $s = $b.s;
    }
    |
    {
    $s = "";
    }
    ;

elif returns [String s]
    : i=IF
    {
        $s = $i.text;
    }
    | e=ELSE i=IF
    {
        $s = $e.text + " " + $i.text;
    }
    ;

condition returns [String s]
    : l=LACTION o1=operand o2=operand
    {
        $s = "(" + $o1.s + " " + $l.text + " " + $o2.s + ")";
    }
    | '(' c=condition ')'
    {
        $s = $c.s;
    }
    ;

exprs[String begin, String end, int shift] returns [String s]
    : '{' e=expr[begin, end, shift] ep=exprsP[begin, end, shift] '}'
    {
        $s = $e.s + $ep.s;
    }
    | e=expr[begin, end, shift] exprEnd
    {
        $s = $e.s;
    }
    ;

exprsP[String begin, String end, int shift] returns [String s]
    : exprEnd
    {
        $s = "";
    }
    | SEMICOLON e=expr[begin, end, shift] ep=exprsP[begin, end, shift]
    {
        $s = "\n" + $e.s;
    }
    ;

exprEnd returns [String s]
    :
    | div=SEMICOLON
    ;

expr[String begin, String end, int shift] returns [String s]
    : p=PRINT o=operand
    {
         $s = Utils.genTab($shift) + $p.text + "(" + $o.s + ");";
    }
    | a=assignment
    {
        $s = Utils.genTab($shift) + $a.s;
    }
    | i=IF c=condition e=exprs[begin, end, shift + 1] ifp=ifP[begin, end, shift]
    {
        $s = Utils.genTab($shift) + $i.text + " " + $c.s +" " + $begin + "\n" + $e.s + "\n" + Utils.genTab($shift) + $end + $ifp.s;
    }
    ;

assignment returns [String s]
    : v=VAR '=' o=operand
    {
        $s = $v.text + " = " + $o.s + ";";
    }
    ;

operand returns [boolean isSum, String s]
    : op=operation
    {
        $isSum = $op.isSum;
        $s = $op.s;
    }
    | n=NUM
    {
        $isSum = false;
        $s = $n.text;
    }
    | v=VAR
    {
        $isSum = false;
        $s = $v.text;
    }
    | '(' o=operand ')'
    {
        $isSum = $o.isSum;
        $s = $o.s;
    }
    ;

operation returns [boolean isSum, String s]
    : ac1=aCTION1 a=operand b=operand
    {
        String aVal = "";
        if ($a.isSum == true) aVal = "(" + $a.s + ")";
        else aVal = $a.s;
        String bVal = "";
        if ($b.isSum == true) bVal = "(" + $b.s + ")";
        else bVal = $b.s;
        $s = aVal + $ac1.text + bVal;
    }
    | ac2=aCTION2 a=operand b=operand
    {
        $isSum = true;
        $s = $a.s + $ac2.text + $b.s;
    }
    ;

NUM: '[0-9]+';
VAR: '[$]{1}[a-zA-Z_][a-zA-Z0-9_]*';
PRINT: 'print';
LACTION: '(<|>|==|!=|<=|>=)';
aCTION1: '*'|'/';
aCTION2: '+'|'-';
IF: 'if';
ELSE: 'else';
SEMICOLON: ';';