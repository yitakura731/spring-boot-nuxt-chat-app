export const state = () => ({
  pageId: 'user',
  users: [],
  rooms: [],
  selectedUserId: null,
  selectedRoomId: null
});

export const mutations = {
  pageId(state, input) {
    state.pageId = input;
  },
  users(state, input) {
    state.users = input;
  },
  rooms(state, input) {
    state.rooms = input;
  },
  selectedUserId(state, input) {
    state.selectedUserId = input;
  },
  selectedRoomId(state, input) {
    state.selectedRoomId = input;
  }
};

export const getters = {
  pageId: state => {
    return state.pageId;
  },
  users: state => {
    return state.users;
  },
  rooms: state => {
    return state.rooms;
  },
  selectedMemberId: state => {
    return state.selectedMemberId;
  },
  selectedRoomId: state => {
    return state.selectedRoomId;
  },
  selectedRoom: state => {
    return state.rooms.filter(elem => {
      return elem.id === state.selectedRoomId;
    })[0];
  }
};

export const actions = {
  async fetchUsers({ commit }, args) {
    const url = `/users?offset=${args.offset}&limit=${args.limit}`;
    const response = await this.$axios.$get(url);
    if (response && response.length > 0) {
      commit('users', response);
      commit('selectedUserId', response[0].id);
    } else {
      commit('users', []);
      commit('selectedUserId', null);
    }
  },
  async fetchRooms({ commit }, args) {
    const url = `/rooms?offset=${args.offset}&limit=${args.limit}`;
    const response = await this.$axios.$get(url);
    if (response && response.length > 0) {
      commit('rooms', response);
      commit('selectedRoomId', response[0].id);
    } else {
      commit('rooms', []);
      commit('selectedRoomId', null);
    }
  },
  async fetchTalks(ctx, args) {
    const url = `/rooms/${args.roomId}/talks?offset=${args.offset}&limit=${args.limit}`;
    return await this.$axios.$get(url);
  },
  async fetchOrPostRoom({ commit }, args) {
    const url = `/rooms/`;
    const response = await this.$axios.$post(url, args);
    commit('rooms', [response]);
    commit('selectedRoomId', response.id);
    return response.id;
  }
};
