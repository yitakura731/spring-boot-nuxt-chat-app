<template>
  <div class="w-100 p-3">
    <div class="d-flex align-items-center p-1 w-100 bg-white member-item">
      <div class="flex-fill p-2 px-4" style="font-size: 1.4rem">
        {{ user.name }}
      </div>
      <div class="text-center mx-2 btn-area" @click="onClickTalk()">
        <font-awesome-icon
          style="font-size: 2.5rem"
          icon="comments"
          class="text-secondary"
        />
        <div>チャット</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { mapActions, mapMutations } from 'vuex';
import { IUser } from '~/types';

export default Vue.extend({
  props: {
    user: {
      type: Object as () => IUser,
      required: true
    }
  },
  methods: {
    ...mapActions('chat', ['fetchOrPostRoom']),
    ...mapMutations({ commitError: 'common/error' }),
    async onClickTalk(): Promise<void> {
      const roomId = await this.fetchOrPostRoom({
        userIds: [this.user.id]
      });
      this.$router.push({
        path: '/chat',
        query: { id: roomId }
      });
    }
  }
});
</script>

<style scoped>
.member-item {
  background-color: white;
  border: none;
  border-radius: 7px;
  height: 6rem;
  box-shadow: 5px 5px 5px lightgray;
  transition: 1s;
}
.btn-area {
  cursor: pointer;
}
.member-item:active {
  transform: scale(1.1);
}
</style>
