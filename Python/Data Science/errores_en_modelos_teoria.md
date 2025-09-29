# ❗ Comprender los Errores en Modelos de Clasificación

En un modelo de aprendizaje automático supervisado (como predecir si alguien sobrevive en el Titanic), **el modelo puede cometer errores**.

## 📊 Matriz de Confusión

| Real \ Predicho   | 0 (No sobrevive) | 1 (Sobrevive) |
|-------------------|------------------|----------------|
| **0 (No sobrevive)** | ✅ Verdadero Negativo (VN) | ❌ Falso Positivo (FP) |
| **1 (Sobrevive)**     | ❌ Falso Negativo (FN)     | ✅ Verdadero Positivo (VP) |

## 🔍 Tipos de errores

- **Falso Positivo (FP):** El modelo predice que sobrevivirá, pero no lo hace.
- **Falso Negativo (FN):** El modelo predice que no sobrevivirá, pero sí sobrevive.

## 🏥 ¿Por qué importa esto en salud?

En contexto APS:
- ❌ **FP:** asignar recursos a alguien que no los necesita → **ineficiencia**
- ❌ **FN:** no detectar a alguien en riesgo → **potencial daño o pérdida**

## ✅ En clase

- No basta con que el modelo tenga buena *accuracy*, hay que **entender qué tipo de error comete más** y su impacto.
- Esto permite elegir modelos más confiables y tomar mejores decisiones.

