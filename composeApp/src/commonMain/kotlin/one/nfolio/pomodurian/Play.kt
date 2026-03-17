package one.nfolio.pomodurian

expect class Play {
  constructor(bufferSize: Int)
  fun fromPCMs(sounds: List<Sounds>)
  fun start()
  fun stop()
  fun close()
}