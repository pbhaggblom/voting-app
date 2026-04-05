<script setup>
import { postVote } from '../services/voteService'
defineProps(['option'])

const vote = (option) => {
  postVote(option).catch((error) => console.error('Could not post vote', error))
}
</script>

<template>
  <button class="btn btn-outline-secondary mx-3" @click="vote(option)">{{ option }}</button>
</template>
