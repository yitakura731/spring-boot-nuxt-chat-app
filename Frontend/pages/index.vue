<template>
  <app-portal />
</template>

<script lang="ts">
import Vue from 'vue';

export default Vue.extend({
  layout: 'main-layout',
  middleware: ['auth']
});
</script>
