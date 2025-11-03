# 🧠 Analizador Léxico de Expresiones Algebraicas en Java (JFlex)

Este proyecto implementa un **analizador léxico** en Java capaz de reconocer y clasificar expresiones algebraicas utilizando **JFlex** como generador de tokens.  
El programa identifica números, variables, coeficientes, operadores, paréntesis y clasifica la expresión resultante como:

✅ Monomio  
✅ Binomio  
✅ Trinomio  
✅ Polinomio (4+ términos)

---

## 🛠️ Tecnologías utilizadas

| Tecnología | Uso |
|------------|-----|
| **Java 17+** | Lenguaje principal |
| **Maven** | Gestión del proyecto y dependencias |
| **JFlex 1.9.1** | Generación del analizador léxico |
| **NetBeans 15+** | IDE de desarrollo |
| **GitHub** | Control de versiones |

---

## 📂 Estructura del proyecto

 
---

## ▶️ Cómo ejecutar el programa

### 🔹 Opción 1 – Usando Maven (recomendado)
```bash
mvn clean compile exec:java

(3a + b)^2

TOKEN           LEXEMA     POSICIÓN
PAREN_ABRE      (          [1:1]
COEFICIENTE     3          [1:2]
VARIABLE        a          [1:3]
OP_SUMA         +          [1:5]
VARIABLE        b          [1:7]
PAREN_CIERRA    )          [1:8]
OP_POT          ^          [1:9]
NUMERO          2          [1:10]
→ Clasificación: Monomio
2(x + 1) + 4y

→ Clasificación: Binomio


Expresión ingresada
        ↓
Analizador Léxico (JFlex)
        ↓
Tokens generados
        ↓
Clasificación algebraica
🧭 Mejoras futuras

🔹 Implementar analizador sintáctico (árbol de expresiones)
🔹 Agregar GUI con JavaFX / Swing
🔹 Soporte para funciones y notación matemática avanzada
🔹 Exportar resultados como JSON o HTML

👤 Autor

Carlos Adan Noe Morales Valiente
Universidad Mariano Gálvez de Guatemala
Facultad de Ingeniería en Sistemas
Curso: Autómatas y Lenguajes Formales
Docente: Ing. William Arango
Año: 2025

📧 Email académico: cmoralesv16@miumg.edu.gt

🔗 GitHub: https://github.com/CarlosValiente0
