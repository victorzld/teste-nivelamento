<template>
  <div class="container">
    <h1>Busca de Operadoras</h1>
    <input
      v-model="query"
      placeholder="Digite o nome da operadora"
      @keyup.enter="buscar"
    />
    <button @click="buscar">Buscar</button>

    <div v-if="resultados.length > 0">
      <h2>Resultados:</h2>
      <ul>
        <li v-for="operadora in resultados" :key="operadora['Registro_ANS']">
          <strong>{{ operadora["Razao_Social"] }}</strong> -
          {{ operadora["Nome_Fantasia"] || "Sem nome fantasia" }}
        </li>
      </ul>
    </div>

    <p v-else-if="buscou">Nenhum resultado encontrado.</p>
  </div>
</template>

<style scoped>
.container {
  max-width: 500px;
  margin: auto;
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  justify-items: center;
  gap: 20px;
}
input {
  padding: 8px;
  width: 100%;
  margin-bottom: 10px;
}
button {
  padding: 8px;
  cursor: pointer;
  width: 50%;
  margin: auto;
}
ul {
  list-style: none;
  padding: 0;
}
li {
  background: #f4f4f4;
  padding: 10px;
  margin-top: 5px;
  border-radius: 5px;
}
</style>

<script>
export default {
  data() {
    return {
      query: "",
      resultados: [],
      buscou: false,
    };
  },
  methods: {
    async buscar() {
      if (!this.query.trim()) return;
      try {
        const response = await fetch(
          `http://localhost:8000/buscar?query=${encodeURIComponent(this.query)}`
        );
        console.log("Resposta do servidor:", response);

        if (response.ok) {
          this.resultados = await response.json();
          console.log("Dados recebidos:", this.resultados);
        } else {
          this.resultados = [];
        }
      } catch (error) {
        console.error("Erro ao buscar operadoras:", error);
        this.resultados = [];
      }
      this.buscou = true;
    },
  },
};
</script>
