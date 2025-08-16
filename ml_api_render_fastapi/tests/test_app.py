from fastapi.testclient import TestClient
from app.main import app

client = TestClient(app)

def test_health():
    r = client.get('/health')
    assert r.status_code == 200
    assert r.json()['status'] == 'ok'

def test_predict():
    payload = {
        "sepal_length": 5.1,
        "sepal_width": 3.5,
        "petal_length": 1.4,
        "petal_width": 0.2
    }
    r = client.post('/predict', json=payload)
    assert r.status_code == 200
    data = r.json()
    assert "species" in data and "proba" in data and "model_version" in data
    assert set(data["proba"].keys()) == {"setosa", "versicolor", "virginica"}
