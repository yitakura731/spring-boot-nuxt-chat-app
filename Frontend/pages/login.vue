<template>
  <div
    class="vw-100 vh-100 d-flex flex-column justify-content-center align-items-center contents-area"
  >
    <div class="login-area rounded m-3 p-3 w-75">
      <b-form-group label="ユーザーID" label-size="sm">
        <b-input
          v-model="userId"
          size="sm"
          placeholder=""
          @keydown.enter="localLogin"
        />
      </b-form-group>
      <b-form-group label="パスワード" label-size="sm">
        <b-input
          v-model="password"
          size="sm"
          type="password"
          placeholder=""
          @keydown.enter="localLogin"
        />
      </b-form-group>
      <div class="mt-3">
        <b-btn variant="info" size="sm" block @click="localLogin">
          <div class="d-flex justify-content-center align-items-center">
            ログイン
            <span v-if="loading" class="ml-2">
              <b-spinner small label="Loading..." />
            </span>
          </div>
        </b-btn>
      </div>
    </div>
    <div class="error-area mt-3">
      <b-alert
        v-if="error"
        show
        variant="danger"
        class="mt-1 text-center"
        dismissible
        @dismissed="onDismissed"
      >
        {{ errorMessage }}
      </b-alert>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { mapGetters, mapActions, mapMutations } from 'vuex';
interface IData {
  loading: boolean;
  userId: string | null;
  password: string | null;
}
export default Vue.extend({
  middleware: ['auth'],
  data(): IData {
    return {
      loading: false,
      userId: null,
      password: null
    };
  },
  computed: {
    ...mapGetters('common', ['error']),
    errorMessage(): string | null {
      if (this.error) {
        return this.error.message;
      } else {
        return null;
      }
    }
  },
  methods: {
    ...mapActions('auth', ['login', 'me']),
    ...mapMutations({ commitError: 'common/error' }),
    onDismissed(): void {
      this.commitError(null);
    },
    medpassLogin(): void {
      this.commitError({
        message: 'ごめんなさい。未実装です',
        status: 999,
        code: 999
      });
    },
    async localLogin(): Promise<void> {
      try {
        this.loading = true;
        await this.login({
          userId: this.userId,
          password: this.password
        });
        this.$router.push('/');
      } catch (error) {
        if (error.response) {
          this.commitError({
            message: error.response.data,
            status: error.response.status,
            code: 401
          });
        } else {
          this.commitError(error);
        }
      } finally {
        this.loading = false;
      }
    }
  }
});
</script>

<style scoped>
.contents-area {
  background-color: antiquewhite;
  background-size: cover;
}

.login-area {
  background-color: lightskyblue;
  opacity: 0.9;
}
</style>
