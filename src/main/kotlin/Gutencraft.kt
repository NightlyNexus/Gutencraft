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
          val page = text.substring(pageStartIndex)
          pages += page.trimEnd()
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

fun Int.isSupportedCharacter(): Boolean {
  return this == ' '.toInt() || columnCountInternal() != -1
}

class UnsupportedCharacterException(val character: Int): Exception()

private const val lineWidth = 114
private const val spaceWidth = 4

private operator fun String.iterator(): IntIterator = object : IntIterator() {
  private var index = 0

  override fun nextInt() = codePointAt(index).also {
    index += Character.charCount(it)
  }

  override fun hasNext(): Boolean = index < length
}

private fun String.columnCount(): Int {
  var sum = 0
  for (c in this) {
    sum += c.columnCount()
  }
  return sum
}

private fun Int.columnCount(): Int {
  return columnCountInternal().also {
    if (it == -1) {
      throw UnsupportedCharacterException(this)
    }
  }
}

private fun Int.columnCountInternal(): Int {
  return when {
    this in 'a'.toInt()..'e'.toInt() -> {
      6
    }
    this == 'f'.toInt() -> {
      5
    }
    this in 'g'.toInt()..'h'.toInt() -> {
      6
    }
    this == 'i'.toInt() -> {
      2
    }
    this == 'j'.toInt() -> {
      6
    }
    this == 'k'.toInt() -> {
      5
    }
    this == 'l'.toInt() -> {
      3
    }
    this in 'm'.toInt()..'s'.toInt() -> {
      6
    }
    this == 't'.toInt() -> {
      4
    }
    this in 'u'.toInt()..'z'.toInt() -> {
      6
    }
    this in 'A'.toInt()..'H'.toInt() -> {
      6
    }
    this == 'I'.toInt() -> {
      4
    }
    this in 'J'.toInt()..'Z'.toInt() -> {
      6
    }
    this in '0'.toInt()..'9'.toInt() -> {
      6
    }
    this == '\''.toInt() -> {
      2
    }
    this == '"'.toInt() -> {
      4
    }
    this == '.'.toInt() -> {
      2
    }
    this == ','.toInt() -> {
      2
    }
    this == '!'.toInt() -> {
      2
    }
    this == '¡'.toInt() -> {
      2
    }
    this == '?'.toInt() -> {
      6
    }
    this == '¿'.toInt() -> {
      6
    }
    this == '&'.toInt() -> {
      6
    }
    this == '@'.toInt() -> {
      7
    }
    this == '#'.toInt() -> {
      6
    }
    this == '$'.toInt() -> {
      6
    }
    this == '%'.toInt() -> {
      6
    }
    this == '^'.toInt() -> {
      6
    }
    this == ':'.toInt() -> {
      2
    }
    this == ';'.toInt() -> {
      2
    }
    this == '('.toInt() -> {
      4
    }
    this == ')'.toInt() -> {
      4
    }
    this == '['.toInt() -> {
      4
    }
    this == ']'.toInt() -> {
      4
    }
    this == '/'.toInt() -> {
      6
    }
    this == '\\'.toInt() -> {
      6
    }
    this == '‘'.toInt() -> {
      3
    }
    this == '’'.toInt() -> {
      3
    }
    this == '“'.toInt() -> {
      5
    }
    this == '”'.toInt() -> {
      5
    }
    this == '<'.toInt() -> {
      5
    }
    this == '>'.toInt() -> {
      5
    }
    this == '*'.toInt() -> {
      4
    }
    this == '+'.toInt() -> {
      6
    }
    this == '='.toInt() -> {
      6
    }
    this == '_'.toInt() -> {
      6
    }
    this == '-'.toInt() -> {
      6
    }
    // en dash.
    this == '–'.toInt() -> {
      7
    }
    // em dash.
    this == '—'.toInt() -> {
      9
    }
    this == '…'.toInt() -> {
      8
    }
    this == 'Á'.toInt() -> {
      6
    }
    this == 'á'.toInt() -> {
      6
    }
    this == 'É'.toInt() -> {
      6
    }
    this == 'é'.toInt() -> {
      6
    }
    this == 'Í'.toInt() -> {
      4
    }
    this == 'í'.toInt() -> {
      3
    }
    this == 'Ó'.toInt() -> {
      6
    }
    this == 'ó'.toInt() -> {
      6
    }
    this == 'Ñ'.toInt() -> {
      6
    }
    this == 'ñ'.toInt() -> {
      6
    }
    this == 'Ú'.toInt() -> {
      6
    }
    this == 'ú'.toInt() -> {
      6
    }
    this == 'Ü'.toInt() -> {
      6
    }
    this == 'ü'.toInt() -> {
      6
    }
    this == '~'.toInt() -> {
      7
    }
    this == '«'.toInt() -> {
      7
    }
    this == '»'.toInt() -> {
      7
    }
    this == '©'.toInt() -> {
      8
    }
    this == '®'.toInt() -> {
      8
    }
    this == 'ø'.toInt() -> {
      6
    }
    this == '£'.toInt() -> {
      6
    }
    this == '¢'.toInt() -> {
      6
    }
    this == 'Ø'.toInt() -> {
      6
    }
    else -> {
      -1
    }
  }
}

private val whitespace = listOf(" ", "\n")
