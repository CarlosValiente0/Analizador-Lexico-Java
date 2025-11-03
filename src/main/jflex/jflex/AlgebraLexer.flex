/* ===  */
package umg.edu.gt.analizadorlexico;

import umg.edu.gt.analizadorlexico.Token;
import umg.edu.gt.analizadorlexico.TokenType;

/* === Sección 2: opciones, macros, y helpers === */
%%
%public
%class AlgebraLexer
%unicode
%column
%line
%type Token
%function nextToken

%{
  /** Construye Token con línea/columna base 1 */
  private Token tok(TokenType t, String lex) {
    return new Token(t, lex, yyline + 1, yycolumn + 1);
  }
%}

/* ---- Macros ---- */
D         = [0-9]
LETRA     = [a-zA-Z]
ESP       = [ \t\r\n\f]+
ENTERO    = {D}+
DECIMAL   = {D}+"."{D}+
FRACCION  = {D}+ "/" {D}+
NUMERO    = ({FRACCION})|({DECIMAL})|({ENTERO})

/* === Sección 3: reglas === */
%%

/*  */
{ESP}                      { /* ignore */ }

/* --- OPERADORES / PARÉNTESIS --- */
"("                        { return tok(TokenType.PAREN_ABRE,   yytext()); }
")"                        { return tok(TokenType.PAREN_CIERRA, yytext()); }
"+"                        { return tok(TokenType.OP_SUMA,      yytext()); }
"-"                        { return tok(TokenType.OP_RESTA,     yytext()); }
"*"                        { return tok(TokenType.OP_MUL,       yytext()); }
"/"                        { return tok(TokenType.OP_DIV,       yytext()); }
"^"                        { return tok(TokenType.OP_POT,       yytext()); }

/* --- COEFICIENTE ---
   Si un número (entero, decimal o fracción) va PEGADO a variable(s) o a '(',
   devolvemos COEFICIENTE sin consumir lo siguiente (lookahead).  Ej:
   3a, 10xyz, 2(x+1)
   Nota: si hay ESPACIO en medio, NO cuenta como coeficiente. */
{NUMERO}/{LETRA}+          { return tok(TokenType.COEFICIENTE,  yytext()); }
{NUMERO}/\(                { return tok(TokenType.COEFICIENTE,  yytext()); }

/* --- NÚMERO y VARIABLE ---
   (se ejecutan si no aplicó la regla de COEFICIENTE de arriba) */
{NUMERO}                   { return tok(TokenType.NUMERO,       yytext()); }
{LETRA}+                   { return tok(TokenType.VARIABLE,     yytext()); }

/* --- ERROR y EOF --- */
.                          { return tok(TokenType.ERROR,        yytext()); }
<<EOF>>                    { return tok(TokenType.EOF,          "<EOF>"); }
