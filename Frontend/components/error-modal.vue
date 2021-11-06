<template>
  <b-modal
    ref="errorModal"
    hide-backdrop
    hide-header
    hide-footer
    content-class="border-0 bg-warning"
    no-close-on-backdrop
  >
    <div class="w-100 d-flex flex-column">
      <div class="d-flex align-items-center">
        <div class="flex-fill">
          <strong>
            エラー：
            <span class="ml-2">{{ code }}</span>
            <span class="ml-2">{{ status }}</span>
          </strong>
        </div>
        <div class="ml-4 mr-1">
          <b-button variant="light" @click="hideModal()">
            <font-awesome-icon :icon="['fas', 'times']" />
          </b-button>
        </div>
      </div>
      <div class="flex-fill">
        <div class="border rounded overflow-auto m-2">
          <div :style="{ height: 100 + 'px' }" class="p-1 bg-light">
            <div>{{ message }}</div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script lang="ts">
import Vue from 'vue';
import { mapGetters } from 'vuex';

interface IData {
  message: string | null;
  status: string | null;
  code: number | null;
}

export default Vue.extend({
  data(): IData {
    return {
      message: null,
      status: null,
      code: null
    };
  },
  computed: {
    ...mapGetters('common', ['error'])
  },
  watch: {
    error(newVal: any): void {
      if (newVal) {
        if (newVal.response) {
          this.message = newVal.response.data;
          this.status = newVal.response.statusText;
          this.code = newVal.response.status;
        } else if (newVal.data) {
          this.message = newVal.data;
          this.status = newVal.statusText;
          this.code = newVal.status;
        } else {
          this.message = newVal;
          this.status = 'Unknown Error';
          this.code = 999;
        }
        this.showModal();
      }
    }
  },
  methods: {
    showModal(): void {
      (this.$refs.errorModal as any).show();
    },
    hideModal(): void {
      this.$store.commit('common/error', null);
      (this.$refs.errorModal as any).hide();
      this.message = null;
      this.status = null;
      this.code = null;
    }
  }
});
</script>
