module.exports = {
  root: true,
  env: {
    browser: true,
    node: true
  },
  extends: [
    '@nuxtjs/eslint-config-typescript',
    'plugin:nuxt/recommended',
    'plugin:prettier/recommended',
    'prettier'
  ],
  plugins: ['prettier'],
  rules: {
    'linebreak-style': ['error', 'windows'],
    'vue/no-mutating-props': 'warn',
    'no-empty': 'warn',
    '@typescript-eslint/no-unused-vars': 'warn',
    semi: ['error', 'always'],
    'vue/html-self-closing': [
      'error',
      {
        html: {
          void: 'always'
        }
      }
    ]
  }
};
