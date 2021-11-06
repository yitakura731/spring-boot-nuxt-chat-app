export const actions = {
  async login(ctx, args) {
    return await this.$axios.$post(`/auth/signin`, {
      userId: args.userId,
      passwd: args.password
    });
  },

  async logout() {
    await this.$axios.$post(`/auth/signout`);
  },

  async getCurrentUser() {
    return await this.$axios.$get(`/auth/me`);
  }
};
