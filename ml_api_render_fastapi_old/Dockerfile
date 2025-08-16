# Imagen base
FROM python:3.11-slim

WORKDIR /app

# Instalar dependencias del sistema (si fueran necesarias)
RUN apt-get update && apt-get install -y --no-install-recommends build-essential && rm -rf /var/lib/apt/lists/*

COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

# Variable de entorno para la ruta del modelo (puede ajustarse en Render)
ENV MODEL_PATH=models/iris_clf.joblib

# Puerto expuesto (coincide con Render)
EXPOSE 10000

# Comando de inicio con gunicorn+uvicorn worker
CMD ["gunicorn", "-k", "uvicorn.workers.UvicornWorker", "-b", "0.0.0.0:10000", "app.main:app"]
