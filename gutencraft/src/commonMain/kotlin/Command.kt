fun command(pages: List<String>): String {
  val command = StringBuilder()
    .append("/give @p written_book{pages:[")
  for (i in pages.indices) {
    val page = pages[i]
    command
      .append("'{\"text\":")
      .appendDoubleJson(page)
      .append("}'")
    if (i != pages.size - 1) {
      command.append(',')
    }
  }
  command.append("],title:\"<Insert book name>\",author:\"<Insert author>\"}")
  return command.toString()
}

private fun StringBuilder.appendDoubleJson(value: String) = apply {
  append('"')
  appendWithEscapedEscapes(StringBuilder().appendJson(value))
  append('"')
}

private fun StringBuilder.appendWithEscapedEscapes(value: CharSequence) = apply {
  for (i in value.indices) {
    val c = value[i]
    if (c == '\\') {
      append("\\\\")
    } else {
      append(c)
    }
  }
}

private fun StringBuilder.appendJson(value: CharSequence) = apply {
  val replacements = REPLACEMENT_CHARS
  var last = 0
  val length = value.length
  for (i in 0 until length) {
    val c = value[i]
    var replacement: String?
    if (c.code < 128) {
      replacement = replacements[c.code]
      if (replacement == null) {
        if (c.code <= 0x1f) {
          throw UnsupportedOperationException("We need String.format(\"\\\\u%04x\", i).")
        }
        continue
      }
    } else if (c == '\u2028') {
      replacement = "\\u2028"
    } else if (c == '\u2029') {
      replacement = "\\u2029"
    } else {
      continue
    }
    if (last < i) {
      append(value, last, i)
    }
    append(replacement)
    last = i + 1
  }
  if (last < length) {
    append(value, last, length)
  }
}

private val REPLACEMENT_CHARS: Array<String?> = arrayOfNulls<String>(128).apply {
  /*for (i in 0..0x1f) {
    this[i] = String.format("\\u%04x", i)
  }*/
  this['"'.code] = "\\\""
  this['\\'.code] = "\\\\"
  this['\t'.code] = "\\t"
  this['\b'.code] = "\\b"
  this['\n'.code] = "\\n"
  this['\r'.code] = "\\r"
  // Kotlin does not support '\f' so we have to use unicode escape.
  this['\u000C'.code] = "\\f"
}
