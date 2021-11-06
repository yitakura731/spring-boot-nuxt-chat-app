<template>
  <div class="w-100 p-3">
    <div
      class="d-flex align-items-center p-2 w-100 member-item"
      @click="onClickRoom"
    >
      <div class="flex-fill d-flex flex-column justify-content-center">
        <div class="px-2" style="font-size: 1.4rem">
          {{ roomTitle }}
        </div>
        <div class="px-2 mt-1 text-secondary" style="font-size: 1.0rem">
          {{ room.latestMessage }}
        </div>
      </div>
      <div class="px-2" style="font-size: 1.0rem">
        {{ $formatDate(room.latestDate) }}
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { mapGetters } from 'vuex';
import { IMember, IRoom } from '~/types';

export default Vue.extend({
  props: {
    room: {
      type: Object as () => IRoom,
      required: true
    }
  },
  computed: {
    ...mapGetters('hospital', ['selectedStudyId']),
    roomTitle(): string {
      if (this.room) {
        return this.room.members
          .map((e: IMember) => e.name)
          .reduce((prev: string, curr: string) => prev + ', ' + curr);
      } else {
        return '';
      }
    }
  },
  methods: {
    onClickRoom(): void {
      this.$router.push({
        path: '/chat',
        query: { id: this.room.id }
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
