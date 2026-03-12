package one.nfolio.pomodurian

import javax.sound.sampled.AudioFormat

actual object PlayNoise {
  actual fun white() {
    val sampleRate = 44100f
    val bufferSize = 1024

    val format = AudioFormat(sampleRate, 16, 1, true, false) // 44.1kHz 16bit Mono
  }

  actual fun brown() {
    TODO("Not yet implemented")
  }

  actual fun pink() {
    TODO("Not yet implemented")
  }
}