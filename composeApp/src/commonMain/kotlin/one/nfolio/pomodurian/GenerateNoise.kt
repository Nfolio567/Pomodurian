package one.nfolio.pomodurian

import kotlin.random.Random

object GenerateNoise {
  private var brownLastPCM = 0.0

  private var pinkLastOutPCM = 0.0
  private var pink2LastOutPCM = 0.0
  private var pinkLastInPCM = 0.0
  private var pink2LastInPCM = 0.0

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

    val decay = 0.98
    val scale = 0.02
    for (i in 0 until bufferSize) {
      brownLastPCM += Random.nextDouble(-1.0, 1.0) * scale
      brownLastPCM *= decay
      val sample = (brownLastPCM.coerceIn(-1.0, 1.0) * Short.MAX_VALUE).toInt()
      buffer[i * 2] = (sample and 0xFF).toByte()
      buffer[(i * 2) + 1] = ((sample shr 8) and 0XFF).toByte()
    }

    return buffer
  }

  fun pink(bufferSize: Int): ByteArray {
    val buffer = ByteArray(bufferSize * 2)

    for (i in 0 until bufferSize) {
      val nowInPCM = Random.nextDouble(-1.0, 1.0)
      val nowOutPCM =
          0.04957526213389 * nowInPCM +
          -0.06305581334498 * pinkLastInPCM +
          0.01483220320740 * pink2LastInPCM -
          -1.80116083982126 * pinkLastOutPCM -
          0.80257737639225 * pink2LastOutPCM

      pink2LastInPCM = pinkLastInPCM
      pink2LastOutPCM = pinkLastOutPCM

      pinkLastInPCM = nowInPCM
      pinkLastOutPCM = nowOutPCM

      val sample = (nowOutPCM * Short.MAX_VALUE).toInt()
      buffer[i * 2] = (sample and 0xFF).toByte()
      buffer[(i * 2) + 1] = ((sample shr 8) and 0xFF).toByte()
    }

    return buffer
  }
}