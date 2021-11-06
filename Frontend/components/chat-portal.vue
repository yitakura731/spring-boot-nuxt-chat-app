<template>
  <div class="d-flex flex-column talk-area vh-100">
    <div class="d-flex align-items-center p-3 btn-area" @click="onClickReturn">
      <font-awesome-icon icon="chevron-left" style="font-size: 2rem" />
      <div class="ml-5" style="font-size: 1.5rem">{{ roomTitle }}</div>
    </div>
    <div ref="listTalkItemParent" class="flex-fill">
      <div
        ref="listTalkItemChild"
        :style="{ height: height + 'px' }"
        class="overflow-auto p-2"
      >
        <div v-for="item in messages" :key="item.id">
          <chat-talk :item="item" />
        </div>
      </div>
    </div>
    <div class="d-flex align-items-center bg-white p-3 border-top">
      <b-form-input
        v-model="value"
        class="input-area"
        type="text"
        placeholder="input message"
      />
      <div class="ml-4 mr-2 btn-area" @click="onClickSendMessage">
        <font-awesome-icon icon="paper-plane" style="font-size: 2rem" />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { mapActions, mapGetters, mapMutations } from 'vuex';
import { IMember, ITalk, IUser } from '~/types';

interface IData {
  messages: ITalk[];
  value: string | null;
  user: IUser | null;
  height: Number;
}
export default Vue.extend({
  props: {
    roomId: {
      type: String,
      required: true
    }
  },
  data(): IData {
    return {
      height: 0,
      user: null,
      messages: [],
      value: null
    };
  },
  computed: {
    ...mapGetters('chat', ['selectedRoom']),
    roomTitle(): string {
      if (this.selectedRoom) {
        return this.selectedRoom.members
          .map((e: IMember) => e.name)
          .reduce((prev: string, curr: string) => prev + ', ' + curr);
      } else {
        return '';
      }
    }
  },
  updated(): void {
    this.moveScrollBottom();
  },
  async mounted(): Promise<void> {
    try {
      this.matchHeight();
      this.user = await this.getCurrentUser();
      const items = await this.fetchTalks({
        roomId: this.roomId,
        offset: 0,
        limit: 9999
      });
      items.forEach((e: any) => {
        let isOwn = false;
        if (this.user) {
          isOwn = this.user.id === e.user.id;
        }
        this.messages.push({
          id: e.id,
          date: this.$formatDate(e.date),
          message: e.message,
          owner: e.user.name,
          own: isOwn
        });
      });
      this.$stomp.onConnect = () => {
        this.$stomp.subscribe(
          `/topic/greetings/${this.roomId}`,
          (response: any) => {
            const resp = JSON.parse(response.body);
            let isOwn = false;
            if (this.user) {
              isOwn = this.user.id === resp.user.id;
            }
            this.messages.push({
              id: resp.id,
              date: this.$formatDate(resp.date),
              message: resp.message,
              owner: resp.user.name,
              own: isOwn
            });
          }
        );
      };
      this.$stomp.activate();
    } catch (error) {
      this.commitError(error);
    }
  },
  methods: {
    ...mapActions('chat', ['fetchTalks']),
    ...mapActions('auth', ['getCurrentUser']),
    ...mapMutations({ commitError: 'common/error' }),
    onClickSendMessage(): void {
      this.$stomp.publish({
        destination: `/app/hello/${this.roomId}`,
        body: this.value
      });
      this.value = null;
    },
    onClickReturn(): void {
      this.$stomp.deactivate();
      this.$router.push({
        path: '/'
      });
    },
    matchHeight(): void {
      const listTalkItemParent = this.$refs.listTalkItemParent;
      this.height = (listTalkItemParent as any).clientHeight;
    },
    moveScrollBottom(): void {
      const listTalkItemChild = this.$refs.listTalkItemChild;
      (listTalkItemChild as any).scrollTop = (listTalkItemChild as any).scrollHeight;
    }
  }
});
</script>

<style scoped>
.talk-area {
  background-color: rgb(213, 253, 253);
}

.input-area {
  border-radius: 2em;
  background-color: rgb(250, 250, 250);
  height: 3rem;
}

.btn-area {
  cursor: pointer;
}
</style>
