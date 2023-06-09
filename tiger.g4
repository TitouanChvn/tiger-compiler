grammar tiger;

@header{
package parser;
}

// TERMINAUX : 


INT : ('0'..'9')+; //An integer constant is a sequence of one or more decimal digits (i.e., 0123456789)

IDENTIFIER : (('a'..'z')|('A'..'Z'))(('a'..'z')|('A'..'Z')|('0'..'9')|'_')* ;   //An identifier is a sequence of letters, digits, and underscores that starts with a letter. Case is significant.


//The binary operators are + - * / = <> < > <= >= & |

OPERATOR_MULT_DIV: ('*'|'/') ;
OPERATOR_PLUS_MOINS : ('+'); 
OPERATOR_ET : ('&');
OPERATOR_OU : ('|');
OPERATOR_COMPARATEUR : ('<>'|'<'|'>'|'<='|'>='); 

OPERATOR_MOINS : ('-');
OPERATOR_EGAL :('=');

STRING : ('"')(('a'..'z')|('A'..'Z')|('0'..'9')|'_'|' ')*('"');   //  on gère pas '\n'|'\t' pour l'instant



atom //Normalement c'est bon pour toujours (Aurélien)
    : INT       #Integer
    | STRING    #String
    | seqExp    #SeqExpAtom
    | callExp   #CallExpAtom       
    | IDENTIFIER #IdAtom
    | lValue    #LValueAtom
    ;

program 
    : exp+
    ;

dec
    : typeDec 
    | varDec 
    | funDec 
    ;

typeDec
    : 'type' IDENTIFIER OPERATOR_EGAL ty
    ;

ty
    : IDENTIFIER    #IdTy
    | arrTy         #ArrTyTy
    | recTy         #RecTyTy
    ;

arrTy 
    : 'array of' IDENTIFIER
    ;

recTy
    : '{' listrecTyFieldDec '}'
    ;

listrecTyFieldDec
    : (fieldDec (',' fieldDec)*)?
    ;

fieldDec 
    : IDENTIFIER ':' IDENTIFIER
    ;

funDec 
    : 'function' IDENTIFIER '(' listArgs ')' funDecBody
    ;

listArgs
    : (fieldDec (',' fieldDec)*)?
    ;

funDecBody
    : OPERATOR_EGAL exp                     #funDecBodyWithoutId
    | ':' IDENTIFIER OPERATOR_EGAL exp      #funDecBodyWithId
    ;

varDec
    : 'var' IDENTIFIER varDecA
    ;

varDecA
    : ':=' exp                      #VarDecExp
    | ':' IDENTIFIER ':=' exp       #VarDecIdExp
    ;


lValue
    : IDENTIFIER ('[' exp ']' | '.' IDENTIFIER)*
    ;

exp 
    : 'nil'         #Nil 
    | create        #Expcreate
    | orExp         #ExpinfixExp
    | assignment    #Expassignment
    | ifThenElse    #ExpIfThenElse
    | ifThen        #ExpIfThen
    | whileExp      #ExpwhileExp
    | forExp        #ExpforExp
    |'break'        #Break
    | letExp        #ExpletExp
    ;

seqExp
    : '(' (exp (';' exp)*)? ')'
    ;

callExp
    : IDENTIFIER '(' callExpList ')'
    ;

callExpList
    : (exp (',' exp)*)?
    ;

create
    : IDENTIFIER createA
    ;

createA
    : arrCreate
    | recCreate
    ;

arrCreate
    : '['exp']' 'of' exp
    ;

recCreate
    : '{' (fieldCreate (',' fieldCreate)*)? '}'
    ;

fieldCreate 
    : IDENTIFIER OPERATOR_EGAL exp  #Affect
    ;

assignment 
    : lValue ':=' exp #Assign
    ;

ifThenElse
    : 'if' exp 'then' exp 'else' exp
    ;

ifThen
    : 'if' exp 'then' exp
    ;

whileExp 
    :  'while' exp 'do' exp  
    ;


forExp 
    : 'for' IDENTIFIER ':=' exp 'to' exp 'do' exp      
    ;

letExp
    : 'let' listDec 'in' listExp 'end'          
    ;

listDec
    : dec+
    ;

listExp
    : (exp (';' exp)*)?
    ;

orExp
    : andExp (OPERATOR_OU andExp)*
    ;

andExp
    : cmpExp (OPERATOR_ET cmpExp)* 
    ;

cmpExp
    : asExp ((OPERATOR_COMPARATEUR|OPERATOR_EGAL) asExp)*
    ;

asExp
    : mdExp ((OPERATOR_PLUS_MOINS|OPERATOR_MOINS) mdExp)*
    ;

mdExp
    : negExp (OPERATOR_MULT_DIV negExp)*
    ;

negExp
    : (OPERATOR_MOINS) negExp   #Negation
    | atom                      #AtomN
    ;


COMMENTAIRE : '/*'.*?'*/'-> skip ;
WS : ('\n'|'\r'|'\t'|' ')+ -> skip ;
