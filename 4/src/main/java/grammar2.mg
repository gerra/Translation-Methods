main:decls;

decls
    : decl decls 
    |
    ;
decl
    : Str z = vars ';' {System.out.println($Str.text + " " + $z.str + ";" + "// " + $z.bool);}
    ;
vars returns [String str, boolean bool]
    : Str varsE {$str = $Str.text + $varsE.str; $bool = true;}
    ;
varsE returns [String str]
    : ',' Str varsE {$str = "," + $Str.text + $varsE.str; }
    | {$str="";}
    ;

Str: '[_a-zA-Z][_a-zA-Z0-9]*';
