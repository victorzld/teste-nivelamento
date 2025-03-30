from http.server import BaseHTTPRequestHandler, HTTPServer
import json
import urllib.parse

# Carregar os dados do arquivo CSV
import csv

dados = []
with open("Relatorio_cadop.csv", encoding="utf-8") as f:
    leitor = csv.DictReader(f, delimiter=";")
    for linha in leitor:
        dados.append(linha)

class ServidorBusca(BaseHTTPRequestHandler):
    def do_OPTIONS(self):
        self.send_response(200)
        self.send_header("Access-Control-Allow-Origin", "*")
        self.send_header("Access-Control-Allow-Methods", "GET, OPTIONS")
        self.send_header("Access-Control-Allow-Headers", "Content-Type")
        self.end_headers()

    def do_GET(self):
        if self.path.startswith("/buscar?"):
            parametros = urllib.parse.parse_qs(urllib.parse.urlparse(self.path).query)
            query = parametros.get("query", [""])[0].lower()
            
            resultados = [operadora for operadora in dados if query in operadora["Razao_Social"].lower() or query in operadora.get("Nome_Fantasia", "").lower()]
            
            self.send_response(200)
            self.send_header("Content-type", "application/json")
            self.send_header("Access-Control-Allow-Origin", "*")
            self.end_headers()
            self.wfile.write(json.dumps(resultados, ensure_ascii=False).encode("utf-8"))
        else:
            self.send_response(404)
            self.end_headers()

if __name__ == "__main__":
    servidor = HTTPServer(("", 8000), ServidorBusca)
    print("Servidor rodando na porta 8000...")
    servidor.serve_forever()
