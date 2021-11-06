<template>
  <div
    v-if="user"
    class="d-flex align-items-center header-area border-bottom border-2"
  >
    <div class="flex-fill d-flex align-items-end p-2">
      <div class="mr-4">
        {{ user.name }}
      </div>
    </div>
    <div class="d-flex align-items-center">
      <b-dropdown
        variant="none"
        right
        toggle-class="text-decoration-none"
        no-caret
      >
        <template #button-content>
          <font-awesome-icon icon="user-circle" style="font-size: 24px" />
        </template>
        <b-dropdown-text>
          <strong>{{ user.name }}</strong>
          <hr />
          {{ user.email }}
        </b-dropdown-text>
      </b-dropdown>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { mapActions, mapMutations } from 'vuex';
import { IUser } from '~/types';

interface IData {
  user: IUser | null;
}

export default Vue.extend({
  data(): IData {
    return {
      user: null
    };
  },
  async mounted(): Promise<void> {
    try {
      this.user = await this.getCurrentUser();
    } catch (error) {
      this.commitError(error);
    }
  },
  methods: {
    ...mapActions('auth', ['logout', 'getCurrentUser']),
    ...mapMutations({ commitError: 'common/error' })
  }
});
</script>

<style scoped>
.header-area {
  background: aliceblue;
  font-weight: bold;
  font-size: 1.6rem;
  width: 100%;
}

.btn-area {
  cursor: pointer;
}
</style>
