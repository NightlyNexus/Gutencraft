actual fun Int.charCount(): Int {
  val codePoint = this
  return js("String.fromCodePoint(codePoint).length") as Int
}

actual fun String.codePointAt(index: Int): Int {
  val string = this
  return js("string.codePointAt(index)") as Int
}

actual fun Int.codePointToString(): String {
  val codePoint = this
  return js("String.fromCodePoint(codePoint)") as String
}
