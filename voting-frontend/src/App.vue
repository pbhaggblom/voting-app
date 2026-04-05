<script setup>
import { ref, onMounted, computed } from 'vue'
import { connectToResults, getInitialResults } from './services/voteService'
import VoteButton from './components/VoteButton.vue'
import ResultBar from './components/ResultBar.vue'

const votes = ref({})

const totalVotes = computed(() => {
  return Object.values(votes.value).reduce((sum, count) => sum + count, 0)
})

const calculateWidth = (count) => {
  return totalVotes.value === 0 ? 0 : (count / totalVotes.value) * 100
}

onMounted(async () => {
  const initialData = await getInitialResults()
  if (initialData) {
    votes.value = initialData
  }

  connectToResults((data) => {
    votes.value = {
      ...votes.value,
      [data.option]: data.total,
    }
  })
})
</script>

<template>
  <div id="app">
    <h1 class="text-center">Vote</h1>

    <div class="options m-5 d-flex justify-content-center">
      <div v-for="(_, label) in votes" :key="label">
        <VoteButton :option="label" />
      </div>
    </div>

    <div class="results m-5">
      <div v-for="(count, label) in votes" :key="label">
        <ResultBar :option="label" :result="count" :bar-percent="calculateWidth(count)" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
