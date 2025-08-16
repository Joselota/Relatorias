import json
from pathlib import Path

import joblib

DEFAULT_MODEL_PATH = Path("models/iris_clf.joblib")
METADATA_PATH = Path("models/metadata.json")

def load_model(model_path: Path | str = DEFAULT_MODEL_PATH):
    return joblib.load(model_path)

def load_metadata(path: Path | str = METADATA_PATH) -> dict:
    if Path(path).exists():
        return json.loads(Path(path).read_text(encoding="utf-8"))
    return {"model_version": "unknown"}
