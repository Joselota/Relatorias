from __future__ import annotations

import numpy as np
from typing import Tuple

from .model import load_model, load_metadata

# Carga perezosa de artefactos (al primer uso)
_model = None
_metadata = None

TARGET_NAMES = ["setosa", "versicolor", "virginica"]

def get_model_and_meta():
    global _model, _metadata
    if _model is None:
        _model = load_model()
    if _metadata is None:
        _metadata = load_metadata()
    return _model, _metadata

def predict_proba(features: Tuple[float, float, float, float]) -> tuple[str, dict[str, float], str]:
    model, meta = get_model_and_meta()
    X = np.array([features], dtype=float)
    probs = model.predict_proba(X)[0]  # Probabilidades por clase
    idx = int(np.argmax(probs))
    species = TARGET_NAMES[idx]
    proba_dict = {name: float(round(probs[i], 4)) for i, name in enumerate(TARGET_NAMES)}
    version = meta.get("model_version", "unknown")
    return species, proba_dict, version
