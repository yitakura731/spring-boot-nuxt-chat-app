<template>
  <div
    class="d-flex border-top justify-content-around align-items-center footer-area"
  >
    <div
      class="d-flex flex-column align-items-center btn-area p-2"
      @click="commitPageId('user')"
    >
      <font-awesome-icon icon="users" style="font-size: 3rem" />
      <a>友だち</a>
    </div>
    <div
      class="d-flex flex-column align-items-center btn-area"
      @click="commitPageId('room')"
    >
      <font-awesome-icon icon="comments" style="font-size: 3rem" />
      <a>ルーム</a>
    </div>
    <div
      class="d-flex flex-column align-items-center btn-area"
      @click="signOut"
    >
      <font-awesome-icon icon="door-open" style="font-size: 3rem" />
      <a>サインアウト</a>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { mapActions, mapMutations } from 'vuex';

export default Vue.extend({
  methods: {
    ...mapActions('auth', ['logout']),
    ...mapMutations({ commitError: 'common/error' }),
    ...mapMutations({ commitPageId: 'chat/pageId' }),
    async signOut(): Promise<void> {
      try {
        await this.logout();
      } catch (error) {
        this.commitError(error);
      } finally {
        this.$router.push('/login');
      }
    }
  }
});
</script>

<style scoped>
.footer-area {
  padding-top: 1rem;
  padding-bottom: 1rem;
  width: 100%;
}

.btn-area {
  cursor: pointer;
}
</style>
