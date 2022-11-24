import kotlin.text.codePointAt as jvmCodePointAt

internal actual fun Int.charCount(): Int {
  return Character.charCount(this)
}

internal actual fun String.codePointAt(index: Int): Int {
  return jvmCodePointAt(index)
}

internal actual fun Int.codePointToString(): String {
  return String(IntArray(1) { this }, 0, 1)
}
