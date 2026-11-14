import { getPreferredLanguage } from '@/libs'
import { createI18n } from 'vue-i18n'

export const i18n = createI18n({
  legacy: false,
  fallbackLocale: 'fr',
  messages: {
    en: {},
    fr: {},
  },
  locale: getPreferredLanguage(),
})
