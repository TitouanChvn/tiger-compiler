grammar tiger;

@header{
package parser;
}
/* 
program : expr EOF ;

expr 
    :STRING 
	|INT 
	|'nil' 
	|lvalue 
	//|OPERATOR_MOINS expr 
    //|expr OPERATOR expr  
    |lvalue ':=' expr 
    //|IDENTIFIER '('exprlist*')' 
    |'('exprseq*')' 
    |typeid  exprtype
    |'if' expr 'then' expr  exprelse
    |'while' expr 'do' expr 
    |'for' IDENTIFIER ':='expr 'to' expr 'do' expr 
    |'break' //exprp
    |'let' declarationlist 'in' exprseq* 'end' 
    | expinf
    ;

//exprp
//    : ((OPERATOR_PLUS_MOINS|OPERATOR_COMPARATEUR|OPERATOR_MULT_DIV|OPERATOR_OU|OPERATOR_ET|OPERATOR_EGAL|OPERATOR_MOINS) expr exprp)?  //opérateurs = et - sont dans comparateurs et +-
//    ;
 
exprtype
    : '{'filedlist*'}'
    | '['expr']' 'of' expr
    ;

exprelse 
    : ('else' expr)?
    ;

exprseq 
    :expr exprseqp
    ;

exprseqp
    : (';' expr exprseqp)?
    ;

exprlist 
    :expr exprlistp
    ;

exprlistp 
    : (',' expr exprlistp)?
    ;

filedlist 
 	:IDENTIFIER OPERATOR_EGAL expr filedlistp
    ;

filedlistp 
    : (',' IDENTIFIER OPERATOR_EGAL expr filedlistp)?
    ;

 
variabledeclaration 
    :'var' IDENTIFIER variabledeclarationsuite
    ;
    
variabledeclarationsuite
    :':=' expr
    | ':' typeid ':=' expr
    ;



functiondeclaration 
    :'function' IDENTIFIER '(' typefields* ')' functiondeclarationsuite
    ;

functiondeclarationsuite
    : OPERATOR_EGAL expr
    | ':' typeid OPERATOR_EGAL expr
    ;

type 
    :typeid 
    |'{' typefield*'}' 
    |'array' 'of' typeid
    ;

typeid 
    : 'int'
    | 'string'
    ;


typefields
    :typefield typefieldsp
    ;

typefieldsp 
    : (',' typefield typefieldsp)?
    ;

typefield 
    :IDENTIFIER ':' typeid
    ;

declarationlist 
    :declaration declarationlistp
    ;

declarationlistp
    : (declaration declarationlistp)?
    ;

declaration 
    : typedeclaration 
    |variabledeclaration 
    |functiondeclaration
    ;

typedeclaration 
    :'type' typeid OPERATOR_EGAL type
    ;

lvalue 
    :IDENTIFIER lvaluep
    ;

lvaluep
    : ('.'IDENTIFIER lvaluep)?
    | ('['expr']' lvaluep)?
    ;



atom 
    : INT
    | STRING
    |IDENTIFIER
    |IDENTIFIER '('exprlist*')' 
    ;

expinf 
    : expou (OPERATOR_OU expinf )?
    ;

expou 
    : expet (OPERATOR_ET expou )?
    ;

expet 
    : expcom ((OPERATOR_COMPARATEUR|OPERATOR_EGAL) expet )?
    ;


expcom 
    : expas ((OPERATOR_MOINS|OPERATOR_PLUS_MOINS) expcom)?
    ;

expas
    : expmd ((OPERATOR_MULT_DIV) expas )?
    ;

expmd
    : (OPERATOR_MOINS)? atom
    ;



*/

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
    : INT
    | STRING
    | seqExp
    | callExp
    | lValue
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
    : IDENTIFIER
    | arrTy
    | recTy
    ;

arrTy 
    : 'array of' IDENTIFIER
    ;

recTy
    : '{' (fieldDec (';' fieldDec)*)? '}'
    ;

fieldDec
    : IDENTIFIER ':' IDENTIFIER
    ;

/*funDec
    : 'function' IDENTIFIER '(' (fieldDec (';' fieldDec)*)? ')' OPERATOR_EGAL exp 
    | 'function' IDENTIFIER '(' (fieldDec (';' fieldDec)*)? ')' ':' IDENTIFIER OPERATOR_EGAL exp
    ;

varDec
    : 'var' IDENTIFIER ':=' exp 
    | 'var' IDENTIFIER ':' IDENTIFIER ':=' exp
    ; */

funDec
    : 'function' IDENTIFIER '(' (fieldDec (',' fieldDec)*)? ')' (OPERATOR_EGAL exp | ':' IDENTIFIER OPERATOR_EGAL exp)
    ;

varDec
    : 'var' IDENTIFIER (':=' exp | ':' IDENTIFIER ':=' exp)
    ;

/*lValue
    : IDENTIFIER
    | subscript
    | fieldExp
    ;

subscript 
    : lValue '[' exp ']'
    ;

fieldExp
    : lValue '.' IDENTIFIER
    ;*/

lValue
    : IDENTIFIER ('[' exp ']' | '.' IDENTIFIER)*
    ;

exp 
    : 'nil'
    | infixExp
    | arrCreate
    | recCreate
    | assignment
    | ifThenElse
    | ifThen
    | whileExp
    | forExp
    |'break'
    | letExp
    ;

seqExp
    : '(' (exp (';' exp)*)? ')'
    ;

callExp
    : IDENTIFIER '(' (exp (',' exp)*)? ')' //Pourquoi on met pas seqExp ? (Titouan)
    ;

arrCreate
    : IDENTIFIER '['exp']' 'of' exp
    ;

recCreate
    : IDENTIFIER '{' fieldCreate '}'
    ;

fieldCreate 
    : IDENTIFIER OPERATOR_EGAL exp
    ;

assignment 
    : lValue ':=' exp
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
    : 'let' dec+ 'in' (exp (';' exp)*)? 'end'
    ;

infixExp
    : orExp (':=' orExp)*
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
    : (OPERATOR_MOINS) negExp
    | atom
    ;


COMMENTAIRE : '/*'.*?'*/'-> skip ;
WS : ('\n'|'\r'|'\t'|' ')+ -> skip ;
