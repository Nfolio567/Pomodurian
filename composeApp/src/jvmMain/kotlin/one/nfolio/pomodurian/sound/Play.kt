package one.nfolio.pomodurian.sound

import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.SourceDataLine
import kotlin.math.tanh

actual class Play {
  val sampleRate = 44100f
  var bufferSize: Int
  val format = AudioFormat(sampleRate, 16, 1, true, false) // 44.1kHz 16bit Mono
  val line: SourceDataLine? = AudioSystem.getSourceDataLine(format)

  actual constructor(bufferSize: Int) {
    this.bufferSize = bufferSize
    line?.open(format, bufferSize * 2)
    line?.start()
  }

  actual fun fromPCMs(sounds: List<Sounds>) {
    val buffer = ByteArray(bufferSize * 2)

    for (i in 0 until bufferSize) {
      var mix = 0.0f
      for (j in sounds) {
        mix += j.generator() * j.volume
      }
      mix = tanh(mix)

      val sample = (mix * Short.MAX_VALUE).toInt()
      buffer[i * 2] = (sample and 0xFF).toByte()
      buffer[(i * 2) + 1] = ((sample shr 8) and 0xFF).toByte()
    }

    line?.write(buffer, 0, buffer.size)
  }

  actual fun start() {
    line?.start()
  }

  actual fun stop() {
    line?.stop()
  }

  actual fun close() {
    line?.drain()
    line?.close()
  }
}