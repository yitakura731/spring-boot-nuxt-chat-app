export default async function({ store, redirect, route }) {
  let curruser = null;
  try {
    curruser = await store.dispatch('auth/getCurrentUser');
  } catch (exception) {
    curruser = null;
  }
  if (curruser && route.path === '/login') {
    redirect('/');
  }
  if (!curruser && route.path !== '/login') {
    redirect('/login');
  }
}
