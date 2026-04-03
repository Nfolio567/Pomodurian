package one.nfolio.pomodurian.sound

import kotlin.random.Random

object GenerateNoise {
  private var brownLastPCM = 0.0

  private var pinkLastOutPCM = 0.0
  private var pink2LastOutPCM = 0.0
  private var pinkLastInPCM = 0.0
  private var pink2LastInPCM = 0.0

  fun white(): Float {
    val volume = 0.3
    val sample = Random.nextDouble(-1.0, 1.0) * volume

    return sample.toFloat()
  }

  fun brown(): Float {
    val decay = 0.98
    val scale = 0.02
    brownLastPCM += Random.nextDouble(-1.0, 1.0) * scale
    brownLastPCM *= decay

    return brownLastPCM.toFloat()
  }

  fun pink(): Float {
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

    return nowOutPCM.toFloat()
  }
}