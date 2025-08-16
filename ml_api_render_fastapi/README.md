# Despliegue de un modelo de ML como API (FastAPI + Render)

Este repositorio empaqueta un modelo *Iris* (scikit-learn) como un servicio de predicción con **FastAPI**. Incluye:
- API con validación de entrada (Pydantic) y OpenAPI
- Artefacto del modelo versionado
- Pruebas automatizadas (pytest + TestClient)
- Cliente Python para pruebas end-to-end
- Dockerfile y `render.yaml` para despliegue en Render
- Makefile con comandos útiles

## Requisitos locales
- Python 3.10+
- (Opcional) Docker

## Setup
```bash
python -m venv .venv && source .venv/bin/activate  # en Windows: .venv\Scripts\activate
pip install -r requirements.txt
make train        # entrena y guarda el modelo (si quieres regenerarlo)
make test         # corre pruebas
make run          # inicia la API local en http://localhost:8000
```

## Uso rápido (local)
```bash
curl -X POST http://localhost:8000/predict \  -H "Content-Type: application/json" \  -d '{"sepal_length":5.1,"sepal_width":3.5,"petal_length":1.4,"petal_width":0.2}'
```

Respuesta esperada:
```json
{"species":"setosa","proba":{"setosa":0.99,"versicolor":0.00,"virginica":0.01}}
```

## Documentación interactiva
- Swagger UI: `http://localhost:8000/docs`
- ReDoc: `http://localhost:8000/redoc`

## Despliegue en Render
1. Sube este repo a GitHub.
2. En Render, crea un **Web Service** desde el repo. Detectará Python.
3. **Runtime**: Python 3.10+
4. **Build Command**: `pip install -r requirements.txt`
5. **Start Command**: `gunicorn -k uvicorn.workers.UvicornWorker app.main:app`
6. Variables de entorno (opcional): `MODEL_PATH=models/iris_clf.joblib`
7. (Opcional) Habilita auto-deploys en cada push a `main`.

> Alternativa: usa `render.yaml` para infra como código (Render Blueprint).

## Estructura
```
app/
  main.py          # FastAPI app y rutas
  schemas.py       # Esquemas Pydantic (request/response)
  pipeline.py      # Carga de modelo y predicción
  model.py         # Utilidades de artefactos y metadatos
client/
  predict_client.py
models/
  iris_clf.joblib  # artefacto entrenado
  metadata.json
tests/
  test_app.py
  test_pipeline.py
training/
  train_iris.py    # script de entrenamiento
Dockerfile
Makefile
render.yaml
requirements.txt
README.md
```

## Endpoints
- `GET /health` → healthcheck
- `GET /metadata` → versión del modelo y metadatos
- `POST /predict` → predice la especie a partir de medidas de Iris

### Contrato `POST /predict` (JSON)
```json
{
  "sepal_length": 5.1,
  "sepal_width": 3.5,
  "petal_length": 1.4,
  "petal_width": 0.2
}
```

### Respuesta
```json
{
  "species": "setosa",
  "proba": {"setosa": 0.99, "versicolor": 0.0, "virginica": 0.01},
  "model_version": "1.0.0"
}
```

## Pruebas
```bash
pytest -q
```

## Cliente (desde consola)
```bash
python client/predict_client.py --url http://localhost:8000 --sl 5.1 --sw 3.5 --pl 1.4 --pw 0.2
```

## Notas de producción
- Usa *logging* estructurado.
- Versiona artefactos y fija `MODEL_PATH` por entorno.
- Añade CI (GitHub Actions) para lint + tests.
- Considera monitoreo de *latency* y *input drift*.
