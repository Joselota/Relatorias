from pathlib import Path
import json
import joblib
from sklearn.datasets import load_iris
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

MODELS_DIR = Path("models")
MODELS_DIR.mkdir(exist_ok=True, parents=True)

def train_and_save():
    iris = load_iris()
    X, y = iris.data, iris.target
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42, stratify=y)

    clf = RandomForestClassifier(n_estimators=150, random_state=42)
    clf.fit(X_train, y_train)

    acc = accuracy_score(y_test, clf.predict(X_test))

    # Guardar artefacto
    model_path = MODELS_DIR / "iris_clf.joblib"
    joblib.dump(clf, model_path)

    # Guardar metadatos
    meta = {
        "model_name": "RandomForestClassifier",
        "dataset": "sklearn.iris",
        "accuracy_test": round(float(acc), 4),
        "model_version": "1.0.0"
    }
    (MODELS_DIR / "metadata.json").write_text(json.dumps(meta, indent=2), encoding="utf-8")
    print(f"Modelo guardado en: {model_path}")
    print(f"Metadatos: {meta}")

if __name__ == "__main__":
    train_and_save()
