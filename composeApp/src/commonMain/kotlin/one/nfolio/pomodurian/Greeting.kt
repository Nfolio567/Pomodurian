package one.nfolio.pomodurian

class Greeting {
  private val platform = getPlatform()

  fun greet(): String {
    return "Hello, ${platform.name}!"
  }
}