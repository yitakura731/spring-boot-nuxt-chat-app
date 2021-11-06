<template>
  <div class="d-flex flex-column vh-100">
    <app-header :style="{ height: 5 + 'rem' }" />
    <nuxt class="flex-fill" />
    <app-footer :style="{ height: 6 + 'rem' }" />
    <transition name="fade">
      <div v-if="isLoad" class="fade-area w-100 h-100">
        <loading-spinner />
      </div>
    </transition>
    <error-modal ref="errorModal" />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { mapGetters } from 'vuex';

export default Vue.extend({
  name: 'MainLayout',
  computed: {
    ...mapGetters('common', ['loading', 'transaction']),
    isLoad(): boolean {
      return this.loading || this.transaction;
    }
  }
});
</script>

<style>
html {
  font-size: 0.6rem;
}
.fade-area {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translateY(-50%) translateX(-50%);
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 1s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
