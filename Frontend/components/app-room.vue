<template>
  <div class="d-flex flex-column h-100 bg-light">
    <h3 class="px-4 pt-3 mb-0 font-weight-bold">
      ルーム一覧
    </h3>
    <div class="px-3 pt-2">
      <b-form-input placeholder="検索" />
    </div>
    <div ref="listRoomParent" class="flex-fill bg-light">
      <div :style="{ height: height + 'px' }" class="overflow-auto">
        <div v-for="room in rooms" :key="room.id">
          <app-room-item :room="room" />
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { mapGetters, mapActions, mapMutations } from 'vuex';

interface IData {
  height: number;
}
export default Vue.extend({
  data(): IData {
    return {
      height: 0
    };
  },
  computed: {
    ...mapGetters('chat', ['rooms'])
  },
  async mounted(): Promise<void> {
    this.matchHeight();
    try {
      await this.fetchRooms({
        offset: 0,
        limit: 9999
      });
    } catch (error) {
      this.commitError(error);
    }
  },
  methods: {
    ...mapActions('chat', ['fetchRooms']),
    ...mapMutations({ commitError: 'common/error' }),
    matchHeight(): void {
      const listRoomParent = this.$refs.listRoomParent;
      this.height = (listRoomParent as any).clientHeight;
    }
  }
});
</script>
