from locust import HttpUser, task, between
import random

class CaminhaoJoaoTest(HttpUser):
    wait_time = between(1, 2)
    host = "http://localhost:8080"

    @task
    def criar_e_buscar_caminhao(self):
        response = self.client.post(
            "/api/caminhao",
            json={
                "modelo": "Teste",
                "marca": "Volvo",
                "ano": 2022,
                "preco": 100000,
                "capacidadeCarga": 5000
            }
        )

        if response.status_code in [200, 201]:
            data = response.json()
            caminhao_id = data.get("id")

            if caminhao_id:
                self.client.get(f"/api/caminhao/{caminhao_id}")