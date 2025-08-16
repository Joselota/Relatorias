import argparse
import json
import httpx

def main():
    p = argparse.ArgumentParser()
    p.add_argument('--url', default='http://localhost:8000', help='Base URL de la API')
    p.add_argument('--sl', type=float, required=True, help='sepal_length')
    p.add_argument('--sw', type=float, required=True, help='sepal_width')
    p.add_argument('--pl', type=float, required=True, help='petal_length')
    p.add_argument('--pw', type=float, required=True, help='petal_width')
    args = p.parse_args()

    payload = {
        "sepal_length": args.sl,
        "sepal_width": args.sw,
        "petal_length": args.pl,
        "petal_width": args.pw
    }
    with httpx.Client() as c:
        r = c.post(f"{args.url}/predict", json=payload, timeout=30)
        r.raise_for_status()
        print(json.dumps(r.json(), indent=2, ensure_ascii=False))

if __name__ == "__main__":
    main()
