package one.nfolio.pomodurian

import kotlin.experimental.and
import kotlin.random.Random

object GenerateNoise {
  var lastPCM = 0.0

  fun white(bufferSize: Int): ByteArray {
    val buffer = ByteArray(bufferSize * 2)

    val volume = 0.3
    for (i in 0 until bufferSize) {
      val sample = (Random.nextDouble(-1.0, 1.0) * volume * Short.MAX_VALUE).toInt()
      buffer[i * 2] = (sample and 0xFF).toByte()
      buffer[(i * 2) + 1] = ((sample shr 8) and 0xFF).toByte()
    }

    return buffer
  }

  fun brown(bufferSize: Int): ByteArray {
    val buffer = ByteArray(bufferSize * 2)

    val decay = 0.99
    val scale = 0.02
    for (i in 0 until bufferSize) {
      lastPCM += Random.nextDouble(-1.0, 1.0) * scale
      lastPCM *= decay
      val sample = (lastPCM * Short.MAX_VALUE).toInt()
      buffer[i * 2] = (sample and 0xFF).toByte()
      buffer[(i * 2) + 1] = ((sample shr 8) and 0XFF).toByte()
    }

    return buffer
  }

  fun pink() {
    TODO("Not yet implemented")
  }
}