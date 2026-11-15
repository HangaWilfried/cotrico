import { LOCAL_STORAGE_KEYS } from './constants'

export function getPreferredLanguage(): string {
  const cachedLanguage = localStorage.getItem(LOCAL_STORAGE_KEYS.PREFERRED_LANGUAGE)

  if (cachedLanguage) {
    return cachedLanguage
  }

  const browserLanguage = navigator.language.split('-')[0]
  const locale = browserLanguage === 'en' ? 'en' : 'fr'

  localStorage.setItem(LOCAL_STORAGE_KEYS.PREFERRED_LANGUAGE, locale)

  return locale
}
