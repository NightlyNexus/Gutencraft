import kotlin.text.codePointAt as jvmCodePointAt

actual fun Int.charCount(): Int {
  return Character.charCount(this)
}

actual fun String.codePointAt(index: Int): Int {
  return jvmCodePointAt(index)
}

actual fun Int.codePointToString(): String {
  return String(IntArray(1) { this }, 0, 1)
}
