import Cookies from 'js-cookie';

export default function({ store, redirect, route, $axios }) {
  $axios.onRequest(config => {
    store.commit('common/loading', true);
    const token = Cookies.get('DEMOAPP-XSRF-TOKEN');
    if (token) {
      config.headers.common['DEMOAPP-XSRF-TOKEN'] = token;
    }
  });

  $axios.onResponse(response => {
    store.commit('common/loading', false);
  });

  $axios.onError(error => {
    store.commit('common/loading', false);
    store.commit('common/transaction', false);
    if (error.response) {
      if (error.response.status === 401) {
        const message = error.response.data || error.response.data.message;
        store.commit('common/error', {
          message,
          status: error.response.status,
          code: 401
        });
        if (route.path !== '/login' && route.path !== '/login') {
          redirect('/login');
        }
      }
    }
  });
}
