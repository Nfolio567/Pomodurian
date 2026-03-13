package one.nfolio.pomodurian

import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.SourceDataLine
import kotlin.text.format

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

  actual fun fromPCMs(sounds: Sounds) {
    val soundBuffer = sounds.generator()
    line?.write(soundBuffer, 0, soundBuffer.size)
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