from pydantic import BaseModel, Field

class IrisFeatures(BaseModel):
    sepal_length: float = Field(..., ge=0, description="Longitud del sépalo (cm)")
    sepal_width: float = Field(..., ge=0, description="Ancho del sépalo (cm)")
    petal_length: float = Field(..., ge=0, description="Longitud del pétalo (cm)")
    petal_width: float = Field(..., ge=0, description="Ancho del pétalo (cm)")

class PredictionResponse(BaseModel):
    species: str
    proba: dict[str, float]
    model_version: str
