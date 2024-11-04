fun command(pages: List<String>): String {
  val command = StringBuilder()
    .append("/give @p written_book[written_book_content={pages:[")
  for (i in pages.indices) {
    val page = pages[i]
    command
      .append("'{\"text\":\"")
      .appendMinecraftJson(page)
      .append("\"}'")
    if (i != pages.size - 1) {
      command.append(',')
    }
  }
  command.append("],title:\"<Insert book name>\",author:\"<Insert author>\"}]")
  return command.toString()
}

// Minecraft wants double escapes but wants a single escape for the single quotation mark.
// THIS IS MADE-UP MINECRAFT JSON. NOT REAL JSON.
private fun StringBuilder.appendMinecraftJson(value: CharSequence) = apply {
  val replacements = REPLACEMENT_CHARS
  var last = 0
  val length = value.length
  for (i in 0 until length) {
    val c = value[i]
    var replacement: String?
    if (c.code < 128) {
      replacement = replacements[c.code]
      if (replacement == null) {
        continue
      }
    } else if (c == '\u2028') {
      replacement = "\\\\u2028"
    } else if (c == '\u2029') {
      replacement = "\\\\u2029"
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
  this[0] = "\\\\u0000"
  this[1] = "\\\\u0001"
  this[2] = "\\\\u0002"
  this[3] = "\\\\u0003"
  this[4] = "\\\\u0004"
  this[5] = "\\\\u0005"
  this[6] = "\\\\u0006"
  this[7] = "\\\\u0007"
  this[8] = "\\\\u0008"
  this[9] = "\\\\u0009"
  this[10] = "\\\\u000a"
  this[11] = "\\\\u000b"
  this[12] = "\\\\u000c"
  this[13] = "\\\\u000d"
  this[14] = "\\\\u000e"
  this[15] = "\\\\u000f"
  this['"'.code] = "\\\\\""
  this['\\'.code] = "\\\\\\\\"
  this['\t'.code] = "\\\\t"
  this['\b'.code] = "\\\\b"
  this['\n'.code] = "\\\\n"
  this['\r'.code] = "\\\\r"
  // Kotlin does not support '\f' so we have to use unicode escape.
  this['\u000C'.code] = "\\\\f"
  // THIS IS MADE-UP MINECRAFT JSON. NOT REAL JSON.
  this['\''.code] = "\\'"
}
