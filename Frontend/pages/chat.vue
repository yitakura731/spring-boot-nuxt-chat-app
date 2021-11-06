<template>
  <chat-portal :room-id="id" />
</template>

<script lang="ts">
import Vue from 'vue';

interface IData {
  id: string | (string | null)[];
}
export default Vue.extend({
  middleware: ['auth'],
  data(): IData {
    return {
      id: this.$route.query.id
    };
  }
});
</script>
