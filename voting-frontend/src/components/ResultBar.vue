<script setup>
defineProps(['option', 'result', 'barPercent'])
</script>

<template>
  <div class="result">
    <span>{{ option }}: {{ result }}</span>
    <div class="bar">
      <div :style="{ width: barPercent + '%' }" class="fill"></div>
    </div>
  </div>
</template>

<style scoped>
.bar {
  margin: 20px 0;
  background: #fbf2d5;
  height: 30px;
  position: relative;
}
.fill {
  height: 100%;
  background: #7fa99b;
}
</style>
