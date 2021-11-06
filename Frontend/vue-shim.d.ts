declare module '*.vue' {
  import Vue from 'vue';
  module 'vue/types/vue' {
    interface Vue {
      $formatDate(input: Date): string;
      $formatDateOption(input: Date): string | null;
      $formatYen(input: number): string;
      $generateId(): string;
      $stomp: any;
    }
  }
  export default Vue;
}
