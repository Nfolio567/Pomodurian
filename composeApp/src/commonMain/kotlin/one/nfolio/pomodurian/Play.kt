package one.nfolio.pomodurian

expect class Play {
  constructor(bufferSize: Int)
  fun fromPCMs(sounds: Sounds)
  fun start()
  fun stop()
  fun close()
}