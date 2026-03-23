from locust import HttpUser, task, between
import random

marcas = ["Toyota", "Honda", "Ford", "Chevrolet", "Volkswagen"]
modelos = ["Sedan", "SUV", "Hatch", "Pickup", "Coupe"]

class CarroUser(HttpUser):
    wait_time = between(1, 3)
    carro_id = None

    headers = {
        "Content-Type": "application/json",
        "x-api-key": "minha-chave-secreta-123"
    }

    @task(1)
    def criar_carro(self):
        payload = {
            "marca": random.choice(marcas),
            "modelo": random.choice(modelos),
            "ano": random.randint(2000, 2024)
        }

        response = self.client.post(
            "/api/carro",
            json=payload,
            headers=self.headers
        )

        if response.status_code == 200:
            data = response.json()
            self.carro_id = data.get("id")

    @task(2)
    def buscar_carro(self):
        if self.carro_id:
            self.client.get(
                f"/api/carro/{self.carro_id}",
                headers=self.headers
            )
            