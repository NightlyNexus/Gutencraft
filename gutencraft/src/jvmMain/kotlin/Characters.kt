import kotlin.text.codePointAt as jvmCodePointAt

actual fun Int.charCount(): Int {
  return Character.charCount(this)
}

actual fun String.codePointAt(index: Int): Int {
  return jvmCodePointAt(index)
}
