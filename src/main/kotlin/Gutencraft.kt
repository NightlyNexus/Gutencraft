fun pages(text: String): List<String> {
  val pages = mutableListOf<String>()
  var line = 1
  var column = 0f
  var cursor = 0
  var pageStartIndex = 0
  do {
    val index = text.indexOfAny(whitespace, cursor)
    if (index == -1) {
      val word = text.substring(cursor)
      val columnCount = word.columnCount()
      if (columnCount > 57f) {
        TODO()
      }
      if (column + columnCount > 57f) {
        if (line == 14) {
          val page = text.substring(pageStartIndex, cursor)
          pageStartIndex = cursor
          line = 1
          column = columnCount
          pages += page.trimEnd()
        } else {
          line++
          column = columnCount
        }
        val page = text.substring(pageStartIndex)
        pages += page.trimEnd()
      } else if (column + columnCount == 57f) {
        if (line == 14) {
          val page = text.substring(pageStartIndex, cursor + word.length)
          pageStartIndex = cursor + word.length
          line = 1
          column = 0f
          pages += page.trimEnd()
        } else {
          line++
          column = 0f
        }
      } else {
        column += columnCount
        val page = text.substring(pageStartIndex)
        pages += page.trimEnd()
      }
      cursor += word.length
      break
    }
    val word = text.substring(cursor, index)
    val columnCount = word.columnCount()
    if (columnCount > 57f) {
      TODO()
    }
    if (text[index] == ' ') {
      if (column + columnCount > 57f) {
        if (line == 14) {
          val page = text.substring(pageStartIndex, cursor)
          pageStartIndex = cursor
          line = 1
          column = columnCount + 2f // + for whitespace.
          pages += page.trimEnd()
        } else {
          line++
          column = columnCount + 2f // + for whitespace.
        }
      } else if (column + columnCount == 57f) {
        if (line == 14) {
          val page = text.substring(pageStartIndex, cursor + word.length + 1)
          pageStartIndex = cursor + word.length + 1 // + 1 for whitespace.
          line = 1
          column = 0f
          pages += page.trimEnd()
        } else {
          line++
          column = 0f
        }
      } else {
        column += columnCount + 2 // + for whitespace.
      }
      cursor += word.length + 1 // + 1 for whitespace.
    } else {
      if (column + columnCount > 57f) {
        if (line == 14) {
          val page = text.substring(pageStartIndex, cursor)
          pageStartIndex = cursor
          line = 2
          column = 0f
          pages += page.trimEnd()
        } else if (line == 13) {
          val page = text.substring(pageStartIndex, cursor + word.length + 1)
          pageStartIndex = cursor + word.length + 1 // + 1 for whitespace.
          line = 1
          column = 0f
          pages += page.trimEnd()
        } else {
          line += 2
          column = 0f
        }
      } else if (column + columnCount == 57f) {
        if (line == 14) {
          val page = text.substring(pageStartIndex, cursor + word.length + 1)
          pageStartIndex = cursor + word.length + 1 // + 1 for whitespace.
          line = 1
          column = 0f
          pages += page.trimEnd()
        } else {
          line++
          column = 0f
        }
      } else {
        if (line == 14) {
          val page = text.substring(pageStartIndex, cursor + word.length + 1)
          pageStartIndex = cursor + word.length + 1 // + 1 for whitespace.
          line = 1
          column = 0f
          pages += page.trimEnd()
        } else {
          line++
          column = 0f
        }
      }
      cursor += word.length + 1 // + 1 for whitespace.
    }
  } while (true)
  return pages
}

private fun String.columnCount(): Float {
  var sum = 0f
  for (c in this) {
    sum += c.columnCount()
  }
  return sum
}

private fun Char.columnCount(): Float {
  return when {
    this in 'a'..'e' -> {
      3f
    }
    this == 'f' -> {
      55 / 22f
    }
    this in 'g'..'h' -> {
      3f
    }
    this == 'i' -> {
      1f
    }
    this == 'j' -> {
      3f
    }
    this == 'k' -> {
      55 / 22f
    }
    this == 'l' -> {
      57 / 38f
    }
    this in 'm'..'s' -> {
      3f
    }
    this == 't' -> {
      2f
    }
    this in 'u'..'z' -> {
      3f
    }
    this in 'A'..'H' -> {
      3f
    }
    this == 'I' -> {
      2f
    }
    this in 'J'..'Z' -> {
      3f
    }
    this in '0'..'9' -> {
      3f
    }
    this == '\'' -> {
      1f
    }
    this == '"' -> {
      2f
    }
    this == '.' -> {
      1f
    }
    this == ',' -> {
      1f
    }
    this == '!' -> {
      1f
    }
    this == '?' -> {
      3f
    }
    this == ':' -> {
      1f
    }
    this == ';' -> {
      1f
    }
    this == '(' -> {
      2f
    }
    this == ')' -> {
      2f
    }
    this == '[' -> {
      2f
    }
    this == ']' -> {
      2f
    }
    this == '‘' -> {
      57 / 38f
    }
    this == '’' -> {
      57 / 38f
    }
    this == '“' -> {
      55 / 22f
    }
    this == '”' -> {
      55 / 22f
    }
    this == '*' -> {
      2f
    }
    this == '-' -> {
      3f
    }
    // en dash.
    this == '–' -> {
      56 / 16f
    }
    // em dash.
    this == '—' -> {
      54 / 12f
    }
    else -> {
      throw RuntimeException("Unhandled character $this")
    }
  }
}

private val whitespace = listOf(" ", "\n")