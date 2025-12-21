import gpxpy
import folium
import requests

# 1. Obtener el archivo GPX desde tu GitHub (usando la URL raw)
url = "https://raw.githubusercontent.com/Joselota/Relatorias/main/10K_MdS_2025/Running_20251115_10K.gpx"
response = requests.get(url)
gpx_data = response.text

# 2. Parsear (leer) el archivo con gpxpy
gpx = gpxpy.parse(gpx_data)

# 3. Extraer las coordenadas (Latitud y Longitud)
puntos_ruta = []
for track in gpx.tracks:
    for segment in track.segments:
        for point in segment.points:
            puntos_ruta.append([point.latitude, point.longitude])

# 4. Crear el mapa centrado en el inicio de tu carrera
inicio = puntos_ruta[0]
mapa = folium.Map(location=inicio, zoom_start=14)

# 5. Dibujar la ruta (PolyLine) sobre el mapa
folium.PolyLine(puntos_ruta, color="red", weight=3.5, opacity=0.8).add_to(mapa)

# Opcional: Marcar el punto de salida y meta
folium.Marker(puntos_ruta[0], popup="Salida", icon=folium.Icon(color='green')).add_to(mapa)
folium.Marker(puntos_ruta[-1], popup="Meta", icon=folium.Icon(color='red')).add_to(mapa)

# 6. Guardar el resultado
mapa.save("ruta_10K_MdS.html")
print("¡Mapa generado! Abre 'ruta_10K_MdS.html' en tu navegador.")