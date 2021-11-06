<template>
  <b-button :disabled="isDisable" @click="execFunction">
    <div class="d-flex justify-content-center align-items-center">
      {{ label }}
      <span v-if="isLoading" class="ml-2">
        <b-spinner label="Loading..." />
      </span>
    </div>
  </b-button>
</template>

<script lang="ts">
import Vue from 'vue';
import { mapGetters } from 'vuex';

export default Vue.extend({
  props: {
    onClick: {
      required: true,
      type: Function
    },
    label: {
      required: true,
      type: String
    },
    disabled: {
      required: false,
      type: Boolean,
      default: false
    },
    manualLoading: {
      required: false,
      validator: obj => typeof obj === 'boolean' || obj == null,
      default: null
    }
  },
  data() {
    return {
      processing: false
    };
  },
  computed: {
    ...mapGetters('common', ['loading', 'transaction']),
    isDisable(): boolean {
      return this.processing || this.disabled;
    },
    isLoading(): boolean {
      if (this.manualLoading != null) {
        return this.manualLoading as any;
      } else {
        return this.loading || this.transaction;
      }
    }
  },
  watch: {
    isLoading(newVal: any) {
      this.processing = newVal;
    }
  },
  methods: {
    execFunction(): void {
      this.processing = true;
      this.onClick();
    }
  }
});
</script>
