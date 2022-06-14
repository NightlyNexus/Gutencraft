fun pages(text: String): List<String> {
  val pages = mutableListOf<String>()
  var line = 1
  var column = 0
  var cursor = 0
  var pageStartIndex = 0
  do {
    val index = text.indexOfAny(whitespace, cursor)
    if (index == -1) {
      val word = text.substring(cursor)
      val columnCount = word.columnCount()
      if (columnCount > lineWidth) {
        TODO()
      } else if (column + columnCount > lineWidth) {
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
      } else if (column + columnCount == lineWidth) {
        if (line == 14) {
          val page = text.substring(pageStartIndex, cursor + word.length)
          pageStartIndex = cursor + word.length
          line = 1
          column = 0
          pages += page.trimEnd()
        } else {
          line++
          column = 0
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
    if (text[index] == ' ') {
      if (columnCount > lineWidth) {
        TODO()
      } else if (column + columnCount > lineWidth) {
        if (line == 14) {
          val page = text.substring(pageStartIndex, cursor)
          pageStartIndex = cursor
          line = 1
          column = columnCount + spaceWidth // + for whitespace.
          pages += page.trimEnd()
        } else {
          line++
          column = columnCount + spaceWidth // + for whitespace.
        }
      } else if (column + columnCount == lineWidth) {
        if (line == 14) {
          val page = text.substring(pageStartIndex, cursor + word.length + 1)
          pageStartIndex = cursor + word.length + 1 // + 1 for whitespace.
          line = 1
          column = 0
          pages += page.trimEnd()
        } else {
          line++
          column = 0
        }
      } else {
        column += columnCount + spaceWidth // + for whitespace.
      }
      cursor += word.length + 1 // + 1 for whitespace.
    } else {
      if (column + columnCount == 0 && line == 1) {
        // Remove new lines at the start of pages.
        pageStartIndex += 1
      } else if (columnCount > lineWidth) {
        TODO()
      } else if (column + columnCount > lineWidth) {
        if (line == 14) {
          val page = text.substring(pageStartIndex, cursor)
          pageStartIndex = cursor
          line = 2 // The word carries over onto next page and then has a new line, so we are now on line 2.
          column = 0
          pages += page.trimEnd()
        } else if (line == 13) {
          val page = text.substring(pageStartIndex, cursor + word.length + 1)
          pageStartIndex = cursor + word.length + 1 // + 1 for whitespace.
          line = 1
          column = 0
          pages += page.trimEnd()
        } else {
          line += 2
          column = 0
        }
      } else if (column + columnCount == lineWidth) {
        if (line == 14) {
          val page = text.substring(pageStartIndex, cursor + word.length + 1)
          pageStartIndex = cursor + word.length + 1 // + 1 for whitespace.
          line = 1
          column = 0
          pages += page.trimEnd()
        } else {
          line++
          column = 0
        }
      } else {
        if (line == 14) {
          val page = text.substring(pageStartIndex, cursor + word.length + 1)
          pageStartIndex = cursor + word.length + 1 // + 1 for whitespace.
          line = 1
          column = 0
          pages += page.trimEnd()
        } else {
          line++
          column = 0
        }
      }
      cursor += word.length + 1 // + 1 for whitespace.
    }
  } while (true)
  return pages
}

private const val lineWidth = 114
private const val spaceWidth = 4

private fun String.columnCount(): Int {
  var sum = 0
  for (c in this) {
    sum += c.columnCount()
  }
  return sum
}

private fun Char.columnCount(): Int {
  return when {
    this in 'a'..'e' -> {
      6
    }
    this == 'f' -> {
      5
    }
    this in 'g'..'h' -> {
      6
    }
    this == 'i' -> {
      2
    }
    this == 'j' -> {
      6
    }
    this == 'k' -> {
      5
    }
    this == 'l' -> {
      3
    }
    this in 'm'..'s' -> {
      6
    }
    this == 't' -> {
      4
    }
    this in 'u'..'z' -> {
      6
    }
    this in 'A'..'H' -> {
      6
    }
    this == 'I' -> {
      4
    }
    this in 'J'..'Z' -> {
      6
    }
    this in '0'..'9' -> {
      6
    }
    this == '\'' -> {
      2
    }
    this == '"' -> {
      4
    }
    this == '.' -> {
      2
    }
    this == ',' -> {
      2
    }
    this == '!' -> {
      2
    }
    this == '¡' -> {
      2
    }
    this == '?' -> {
      6
    }
    this == '¿' -> {
      6
    }
    this == '&' -> {
      6
    }
    this == '@' -> {
      7
    }
    this == '#' -> {
      6
    }
    this == '$' -> {
      6
    }
    this == '%' -> {
      6
    }
    this == '^' -> {
      6
    }
    this == ':' -> {
      2
    }
    this == ';' -> {
      2
    }
    this == '(' -> {
      4
    }
    this == ')' -> {
      4
    }
    this == '[' -> {
      4
    }
    this == ']' -> {
      4
    }
    this == '/' -> {
      6
    }
    this == '\\' -> {
      6
    }
    this == '‘' -> {
      3
    }
    this == '’' -> {
      3
    }
    this == '“' -> {
      5
    }
    this == '”' -> {
      5
    }
    this == '<' -> {
      5
    }
    this == '>' -> {
      5
    }
    this == '*' -> {
      4
    }
    this == '+' -> {
      6
    }
    this == '=' -> {
      6
    }
    this == '_' -> {
      6
    }
    this == '-' -> {
      6
    }
    // en dash.
    this == '–' -> {
      7
    }
    // em dash.
    this == '—' -> {
      9
    }
    this == '…' -> {
      8
    }
    this == 'Á' -> {
      6
    }
    this == 'á' -> {
      6
    }
    this == 'É' -> {
      6
    }
    this == 'é' -> {
      6
    }
    this == 'Í' -> {
      4
    }
    this == 'í' -> {
      3
    }
    this == 'Ó' -> {
      6
    }
    this == 'ó' -> {
      6
    }
    this == 'Ñ' -> {
      6
    }
    this == 'ñ' -> {
      6
    }
    this == 'Ú' -> {
      6
    }
    this == 'ú' -> {
      6
    }
    this == 'Ü' -> {
      6
    }
    this == 'ü' -> {
      6
    }
    this == '~' -> {
      7
    }
    this == '«' -> {
      7
    }
    this == '»' -> {
      7
    }
    this == '©' -> {
      8
    }
    this == '®' -> {
      8
    }
    this == 'ø' -> {
      6
    }
    this == '£' -> {
      6
    }
    this == '¢' -> {
      6
    }
    this == 'Ø' -> {
      6
    }
    else -> {
      throw RuntimeException("Unhandled character $this")
    }
  }
}

private val whitespace = listOf(" ", "\n")
