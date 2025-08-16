from fastapi import FastAPI
from .schemas import IrisFeatures, PredictionResponse
from .pipeline import predict_proba
from .model import load_metadata

app = FastAPI(title="Iris ML API", version="1.0.0", description="Servicio de predicción para Iris (FastAPI)")

@app.get("/health")
def health():
    return {"status": "ok"}

@app.get("/metadata")
def metadata():
    return load_metadata()

@app.post("/predict", response_model=PredictionResponse)
def predict(body: IrisFeatures):
    species, proba, version = predict_proba((
        body.sepal_length,
        body.sepal_width,
        body.petal_length,
        body.petal_width
    ))
    return {"species": species, "proba": proba, "model_version": version}
