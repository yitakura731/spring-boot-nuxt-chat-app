export const state = () => ({
  error: null,
  loading: false,
  transaction: false
});

export const mutations = {
  error(state, input) {
    state.error = input;
  },
  loading(state, input) {
    state.loading = input;
  },
  transaction(state, input) {
    state.transaction = input;
  }
};

export const getters = {
  error: state => {
    return state.error;
  },
  loading: state => {
    return state.loading;
  },
  transaction: state => {
    return state.transaction;
  }
};
