package umg.edu.gt.analizadorlexico;

public enum TokenType {
  // Números y variables
  NUMERO,          // 10, 3.5, 42
  VARIABLE,        // a, b, x, y, z (una letra)
  COEFICIENTE,     // 3 en "3a", 2 en "2(x+1)" (detectado por lookahead)

  // Operadores
  OP_SUMA,         // +
  OP_RESTA,        // -
  OP_MUL,          // *
  OP_DIV,          // /
  OP_POT,          // ^

  // Agrupadores
  PAREN_ABRE,      // (
  PAREN_CIERRA,    // )

  // Control
  ERROR,
  EOF
}
