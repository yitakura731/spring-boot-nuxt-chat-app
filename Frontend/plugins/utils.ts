import { Plugin } from '@nuxt/types';

const appUtil: Plugin = (ctx: any, inject: any) => {
  inject('formatDate', (input: Date): string => {
    if (input) {
      return ctx.$moment(input).format('YYYY/MM/DD');
    } else {
      return '';
    }
  });
  inject('formatDateOption', (input: Date): string | null => {
    if (input) {
      return ctx.$moment(input).format('YYYY-MM-DD');
    } else {
      return null;
    }
  });
  inject('formatYen', (input: number): string => {
    if (input == null) {
      return '';
    }
    const formatter = new Intl.NumberFormat('ja-JP');
    return formatter.format(input) + ' 円';
  });
  inject('generateId', (): string => {
    return (
      new Date().getTime().toString(16) +
      Math.floor(10000 * Math.random()).toString(8) +
      Math.floor(10000 * Math.random()).toString(8)
    );
  });
};

export default appUtil;
