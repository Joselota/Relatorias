# 🏃 Análisis 10K Maratón de Santiago 2025

Este proyecto contiene un análisis preliminar del recorrido de 10K correspondiente a la Maratón de Santiago 2025, basado en los datos obtenidos desde un archivo GPX generado por un dispositivo de tracking GPS (reloj deportivo o app de running).

## 📂 Contenido

- `10K MdS 2025.ipynb`: Notebook en Python que realiza el análisis del recorrido.
- `activity_19162156358.gpx`: Archivo con los datos crudos del trayecto (coordenadas, altitud, tiempo, etc.).

## 🔍 ¿Qué incluye el análisis?

1. **Exploración inicial del GPX**: cantidad de puntos, revisión de estructura y atributos como latitud, longitud, tiempo y elevación.
2. **Visualización del recorrido**:
   - Mapa del trayecto utilizando Folium.
   - Coloreado del recorrido por tramos de velocidad (verde = rápido, rojo = lento).
3. **División por kilómetros**:
   - Cálculo de tiempo por kilómetro.
   - Identificación de *splits* (negativos o positivos).
   - Gráficos con Matplotlib.
4. **Splits por kilometros**:
   - Cálculo y gráficas por kilometro
   - Kilometro más rápido y kilometro más lento
5. **Elevación por kilómetro**:
   - Análisis de ganancia o pérdida de altitud durante la carrera.
   - Visualización del perfil de elevación.
6. **Mapa con kilómetros**
7. **Mapa consolidado por kilómetros y tiempos**

## 🛠️ Librerías utilizadas

- `gpxpy`: Para parsear el archivo GPX.
- `folium`: Para crear mapas interactivos con el recorrido.
- `matplotlib`: Para gráficos de velocidad y elevación.
- `geopy`: Para calcular distancias geodésicas entre puntos GPS.
- `pandas`: Para organizar los datos en estructuras tabulares.

## ✅ Objetivo

Este análisis permite visualizar el rendimiento en carrera, identificar fortalezas y oportunidades de mejora, y registrar el historial de entrenamiento o competencia para futuras comparaciones.

---

📌 **Autora**: Ingrid Solís
📅 **Última actualización**: junio 2025

