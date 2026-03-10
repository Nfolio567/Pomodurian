package one.nfolio.pomodurian

interface Platform {
  val name: String
}

expect fun getPlatform(): Platform