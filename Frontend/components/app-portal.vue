<template>
  <div class="d-flex flex-column h-100 bg-light">
    <app-user v-if="pageId === 'user'" />
    <app-room v-if="pageId === 'room'" />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { mapGetters } from 'vuex';

export default Vue.extend({
  computed: {
    ...mapGetters('chat', ['pageId'])
  }
});
</script>
