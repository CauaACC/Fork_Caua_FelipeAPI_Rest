from locust import HttpUser, task, between
import random

class CaminhaoJoaoTest(HttpUser):
    wait_time = between(1, 2)

    def on_start(self):
        response = self.client.post(
            "/admin/gerar-chave",
            json={
                "nomeCliente": "Joao"
            }
        )

        if response.status_code == 200:
            data = response.json()

            self.api_key = data.get("apiKey") or data.get("key") or data.get("chave")

            print("API KEY:", self.api_key)
        else:
            print("Erro ao gerar API Key")
            self.api_key = None

    @task
    def criar_e_buscar_caminhao(self):
        if not self.api_key:
            return

        headers = {
            "x-api-key": self.api_key,
            "Content-Type": "application/json"
        }

        response = self.client.post(
            "/api/caminhao",
            json={
                "nome": "Caminhao Teste",
                "placa": f"ABC{random.randint(1000,9999)}"
            },
            headers=headers
        )

        if response.status_code in [200, 201]:
            try:
                caminhao = response.json()
                caminhao_id = caminhao.get("id")

                if caminhao_id:
                    self.client.get(
                        f"/api/caminhao/{caminhao_id}",
                        headers=headers
                    )

            except:
                print("Erro ao ler JSON")

        else:
            print(f"Erro POST: {response.status_code}")
