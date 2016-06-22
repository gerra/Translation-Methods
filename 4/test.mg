decls
    : decl decls
    |
    ;
decl
    : Str z = vars ';' {System.out.println($Str.text + " " + $z.str + ";" + $z.bool);}
    ;
vars returns [String str, boolean bool]
    : Str varsE {$str = $Str.text; bool = true;}
    ;
varsE
    : ',' Str varsE
    ;

Str: '[_a-zA-Z][_a-zA-Z0-9]*';