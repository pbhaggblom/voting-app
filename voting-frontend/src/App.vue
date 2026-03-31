<script setup>
import { ref, onMounted } from 'vue'
import { postVote, connectToResults } from './services/voteService'

const votes = ref({ A: 0, B: 0 })

const vote = (option) => {
  postVote(option).catch((error) => console.error('Could not post vote', error))
}

const calculateWidth = (count) => {
  const total = votes.value.A + votes.value.B
  return total === 0 ? 0 : (count / total) * 100
}

onMounted(() => {
  connectToResults((data) => {
    votes.value[data.option] = data.total
  })
})
</script>

<template>
  <div id="app">
    <h1>Vote</h1>

    <div class="options">
      <button @click="vote('A')">A</button>
      <button @click="vote('B')">B</button>
    </div>

    <div class="results">
      <span>A: {{ votes.A }}</span>
      <div class="bar">
        <div :style="{ width: calculateWidth(votes.A) + '%' }" class="fill a"></div>
      </div>
      <span>B: {{ votes.B }}</span>
      <div class="bar">
        <div :style="{ width: calculateWidth(votes.B) + '%' }" class="fill b"></div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.results,
.options {
  margin: 10px;
}
.bar {
  margin: 20px 0;
  background: #eee;
  height: 30px;
  position: relative;
}
.fill {
  height: 100%;
}
.fill.a {
  background: #42b983;
}
.fill.b {
  background: #165da8;
}
</style>
