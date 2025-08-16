from app.pipeline import predict_proba

def test_predict_proba_shape():
    species, proba, version = predict_proba((5.1, 3.5, 1.4, 0.2))
    assert isinstance(species, str)
    assert set(proba.keys()) == {"setosa", "versicolor", "virginica"}
    assert 0.0 <= proba["setosa"] <= 1.0
    assert isinstance(version, str)
