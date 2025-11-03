package umg.edu.gt.analizadorlexico;

import java.io.*;
import java.util.Scanner;

public class Main {

    // Si luego quieres volver a usar símbolos Unicode, pon esto en true
    private static final boolean UNICODE_ICONS = false;

    private static String warn() { return UNICODE_ICONS ? "⚠" : "ADVERTENCIA"; }
    private static String arrow() { return UNICODE_ICONS ? "→" : "->"; }

    private static void escanear(Reader reader) throws IOException {
        AlgebraLexer lex = new AlgebraLexer(reader);

        System.out.printf("%-15s %-18s %s%n", "TOKEN", "LEXEMA", "POSICION");
        System.out.println("-----------------------------------------------");

        Token t;
        StringBuilder reconstruida = new StringBuilder();
        TokenType last = null;

        do {
            t = lex.nextToken();

            System.out.printf("%-15s %-18s [%d:%d]%n",
                    t.type, t.lexeme, t.line, t.column);

            if (t.type == TokenType.ERROR) {
                System.err.println(warn() + " Token invalido: '" + t.lexeme +
                        "' en [" + t.line + ":" + t.column + "]");
                break;
            }

            if (t.type != TokenType.EOF) {
                // Insertar '*' implicito SOLO para la cadena reconstruida (no afecta tokens)
                boolean insertarAsterisco = false;

                if (last != null) {
                    boolean lastIsNumVarParD =
                            (last == TokenType.NUMERO
                             || last == TokenType.COEFICIENTE
                             || last == TokenType.VARIABLE
                             || last == TokenType.PAREN_CIERRA);

                    boolean currIsNumVarParI =
                            (t.type == TokenType.NUMERO
                             || t.type == TokenType.COEFICIENTE
                             || t.type == TokenType.VARIABLE
                             || t.type == TokenType.PAREN_ABRE);

                    if (lastIsNumVarParD && currIsNumVarParI) {
                        if (t.type != TokenType.OP_SUMA
                                && t.type != TokenType.OP_RESTA
                                && t.type != TokenType.OP_MUL
                                && t.type != TokenType.OP_DIV
                                && t.type != TokenType.OP_POT) {
                            insertarAsterisco = true;
                        }
                    }
                }

                if (insertarAsterisco) {
                    reconstruida.append('*');
                }
                reconstruida.append(t.lexeme);
                last = t.type;
            }

        } while (t.type != TokenType.EOF);

        // Clasificacion de mono/bino/tri/poli a nivel top-level (fuera de parentesis)
        int n = contarTerminosTopLevel(reconstruida.toString());
        String tipo = switch (n) {
            case 1 -> "Monomio";
            case 2 -> "Binomio";
            case 3 -> "Trinomio";
            default -> "Polinomio (" + n + " terminos)";
        };

        System.out.println(arrow() + " Reconstruida: " + reconstruida);
        System.out.println(arrow() + " Clasificacion: " + tipo);
    }

    // Cuenta terminos top-level separados por + o - (ignora los que estan dentro de parentesis)
    private static int contarTerminosTopLevel(String s) {
        int nivel = 0;
        int terminos = 0;
        boolean dentroDeTermino = false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') { nivel++; continue; }
            if (c == ')') { nivel--; continue; }

            if (nivel == 0 && (c == '+' || c == '-')) {
                if (dentroDeTermino) terminos++;
                dentroDeTermino = false;
            } else if (!Character.isWhitespace(c)) {
                dentroDeTermino = true;
            }
        }
        if (dentroDeTermino) terminos++;

        return Math.max(terminos, 1);
    }

    public static void main(String[] args) {
      
        try {
            if (args.length > 0) {
                try (Reader r = new FileReader(args[0])) {
                    escanear(r);
                }
            } else {
                // Modo multi-ejemplos por consola
                Scanner sc = new Scanner(System.in);
                System.out.println("Analizador Lexico (multi-ejemplos). Escribe 'exit' para salir.");
                while (true) {
                    System.out.print("Escribe una expresion algebraica: ");
                    String entrada = sc.nextLine();
                    if (entrada == null) break;
                    if (entrada.trim().equalsIgnoreCase("exit")) break;
                    try (Reader r = new StringReader(entrada)) {
                        escanear(r);
                    }
                    System.out.println();
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("No se encontro el archivo: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }
}
