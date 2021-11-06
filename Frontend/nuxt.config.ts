import { NuxtConfig } from '@nuxt/types';

const publicDomain = process.env.PUBLIC_DOMAIN || 'localhost:3001';
const repositoryURL = process.env.REPOSITORY_URL || 'http://localhost:8080';

console.log(`publicDomain = ${publicDomain}`);
console.log(`repositoryURL = ${repositoryURL}`);

const nuxtConfig: NuxtConfig = {
  ssr: false,

  router: {
    linkActiveClass: 'active'
  },

  components: true,

  head: {
    title: 'My demo app',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      {
        hid: 'description',
        name: 'description',
        content: 'This is a demo app'
      }
    ],
    link: [{ rel: 'icon', type: 'image/x-icon', href: 'favicon.ico' }]
  },

  plugins: [
    '~/plugins/bootstrap-vue.js',
    '~/plugins/axios.js',
    '~/plugins/stomp.js',
    '~/plugins/utils.ts'
  ],

  fontawesome: {
    imports: [
      {
        set: '@fortawesome/free-solid-svg-icons',
        icons: ['fas']
      },
      {
        set: '@fortawesome/free-regular-svg-icons',
        icons: ['far']
      },
      {
        set: '@fortawesome/free-brands-svg-icons',
        icons: ['fab']
      }
    ]
  },

  env: {
    PUBLIC_DOMAIN: publicDomain
  },

  buildModules: ['@nuxt/typescript-build'],

  modules: [
    'bootstrap-vue/nuxt',
    '@nuxtjs/axios',
    'nuxt-fontawesome',
    '@nuxtjs/moment'
  ],

  axios: {
    proxy: true,
    prefix: '/api/v1'
  },

  proxy: {
    '/api': {
      target: `${repositoryURL}`,
      secure: false
    }
  },

  build: {
    extend(config, { isDev }) {
      if (isDev) {
        config.devtool = 'source-map';
      }
    }
  }
};

export default nuxtConfig;
