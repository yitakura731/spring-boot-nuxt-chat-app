<template>
  <div class="w-100" :class="customClazz">
    <div class="d-flex flex-column message-wapper">
      <div class="d-flex justify-content-start">
        <div class="ml-2">{{ item.owner }}</div>
      </div>
      <div class="message-area">
        {{ item.message }}
      </div>
      <div class="d-flex justify-content-end mt-2">
        <div class="mr-2">{{ item.date }}</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { ITalk } from '~/types';

export default Vue.extend({
  props: {
    item: {
      type: Object as () => ITalk,
      required: true
    }
  },
  computed: {
    customClazz() {
      if (this.item.own) {
        return 'd-flex justify-content-start';
      } else {
        return 'd-flex justify-content-end';
      }
    }
  }
});
</script>

<style scoped>
.message-wapper {
  width: 70%;
  margin-bottom: 2rem;
}
.message-area {
  background-color: white;
  padding: 1.5rem;
  border-radius: 2em;
  box-shadow: 3px 3px 3px lightgray;
}
.message-area:before {
  content: '';
  position: absolute;
  top: 8px;
  left: -20px;
  border: 12px solid transparent;
  border-right: 12px solid white;
}
</style>
