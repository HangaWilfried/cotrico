import { createWorker } from 'tesseract.js'
import { ref } from 'vue'

export function useOcr() {
  const progress = ref(0)
  const extractedText = ref('')
  const isProcessing = ref(false)

  const extractText = (image: File) => {
    isProcessing.value = true

    return createWorker('fra+eng', 1, {
      logger: (message) => {
        if (message.status === 'recognizing text') {
          progress.value = Math.round(message.progress * 100)
        }
      },
    })
      .then((worker) => worker.recognize(image))
      .then(({ data }) => {
        extractedText.value = data.text
        return data.text
      })
      .finally(() => {
        isProcessing.value = false
      })
  }

  return {
    extractText,
    extractedText,
    isProcessing,
    progress,
  }
}
