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
      var sum = 0
      var wordIndex = 0
      var wordOverflowsLine = false
      while (wordIndex < word.length) {
        val codePoint = word.codePointAt(wordIndex)
        val codePointColumnCount = codePoint.columnCount()
        val nextSum = sum + codePointColumnCount
        if (nextSum > lineWidth) {
          if (!wordOverflowsLine) {
            wordOverflowsLine = true
            if (column > 0) {
              if (line == 14) {
                val pageEndIndex = cursor
                val page = text.substring(pageStartIndex, pageEndIndex)
                pageStartIndex = pageEndIndex
                line = 1
                column = 0
                pages += page
              } else {
                line++
                column = 0
              }
            }
          }
          if (line == 14) {
            val pageEndIndex = cursor + wordIndex
            val page = text.substring(pageStartIndex, pageEndIndex)
            pageStartIndex = pageEndIndex
            line = 1
            column = 0
            pages += page
          } else {
            line++
            column = 0
          }
          sum = codePointColumnCount
        } else {
          sum = nextSum
        }
        wordIndex += codePoint.charCount()
      }
      val columnCount = sum
      if (column + columnCount > lineWidth) {
        if (line == 14) {
          val page = text.substring(pageStartIndex, cursor)
          pageStartIndex = cursor
          line = 1
          column = columnCount
          page.trimEnd().let {
            if (it.isNotEmpty()) {
              pages += page.trimEnd()
            }
          }
        } else {
          line++
          column = columnCount
        }
        val page = text.substring(pageStartIndex)
        page.trimEnd().let {
          if (it.isNotEmpty()) {
            pages += page.trimEnd()
          }
        }
      } else if (column + columnCount == lineWidth) {
        if (line == 14) {
          val page = text.substring(pageStartIndex, cursor + word.length)
          pageStartIndex = cursor + word.length
          line = 1
          column = 0
          page.trimEnd().let {
            if (it.isNotEmpty()) {
              pages += page.trimEnd()
            }
          }
        } else {
          line++
          column = 0
          val page = text.substring(pageStartIndex)
          page.trimEnd().let {
            if (it.isNotEmpty()) {
              pages += page.trimEnd()
            }
          }
        }
      } else {
        column += columnCount
        val page = text.substring(pageStartIndex)
        page.trimEnd().let {
          if (it.isNotEmpty()) {
            pages += page.trimEnd()
          }
        }
      }
      cursor += word.length
      break
    }
    val word = text.substring(cursor, index)
    var sum = 0
    var wordIndex = 0
    var wordOverflowsLine = false
    while (wordIndex < word.length) {
      val codePoint = word.codePointAt(wordIndex)
      val codePointColumnCount = codePoint.columnCount()
      val nextSum = sum + codePointColumnCount
      if (nextSum > lineWidth) {
        if (!wordOverflowsLine) {
          wordOverflowsLine = true
          if (column > 0) {
            if (line == 14) {
              val pageEndIndex = cursor
              val page = text.substring(pageStartIndex, pageEndIndex)
              pageStartIndex = pageEndIndex
              line = 1
              column = 0
              pages += page
            } else {
              line++
              column = 0
            }
          }
        }
        if (line == 14) {
          val pageEndIndex = cursor + wordIndex
          val page = text.substring(pageStartIndex, pageEndIndex)
          pageStartIndex = pageEndIndex
          line = 1
          column = 0
          pages += page
        } else {
          line++
          column = 0
        }
        sum = codePointColumnCount
      } else {
        sum = nextSum
      }
      wordIndex += codePoint.charCount()
    }
    val columnCount = sum
    if (text[index] == ' ') {
      if (column + columnCount > lineWidth) {
        if (line == 14) {
          val page = text.substring(pageStartIndex, cursor)
          pageStartIndex = cursor
          line = 1
          column = columnCount + spaceWidth // + for whitespace.
          page.trimEnd().let {
            if (it.isNotEmpty()) {
              pages += page.trimEnd()
            }
          }
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
          page.trimEnd().let {
            if (it.isNotEmpty()) {
              pages += page.trimEnd()
            }
          }
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
      } else if (column + columnCount > lineWidth) {
        if (line == 14) {
          val page = text.substring(pageStartIndex, cursor)
          pageStartIndex = cursor
          // The word carries over onto next page and then has a new line, so we are now on line 2.
          line = 2
          column = 0
          page.trimEnd().let {
            if (it.isNotEmpty()) {
              pages += page.trimEnd()
            }
          }
        } else if (line == 13) {
          val page = text.substring(pageStartIndex, cursor + word.length + 1)
          pageStartIndex = cursor + word.length + 1 // + 1 for whitespace.
          line = 1
          column = 0
          page.trimEnd().let {
            if (it.isNotEmpty()) {
              pages += page.trimEnd()
            }
          }
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
          page.trimEnd().let {
            if (it.isNotEmpty()) {
              pages += page.trimEnd()
            }
          }
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
          page.trimEnd().let {
            if (it.isNotEmpty()) {
              pages += page.trimEnd()
            }
          }
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
  return this == ' '.code || this == '\n'.code || columnCountInternal() != -1
}

class UnsupportedCharacterException(val codePoint: Int) : Exception()

private const val lineWidth = 114
private const val spaceWidth = 4

private fun Int.columnCount(): Int {
  return columnCountInternal().also {
    if (it == -1) {
      throw UnsupportedCharacterException(this)
    }
  }
}

private fun Int.columnCountInternal(): Int {
  if (this == ' '.code || this == '\n'.code) {
    throw AssertionError()
  }
  return when {
    this in 'a'.code..'e'.code -> {
      6
    }
    this == 'f'.code -> {
      5
    }
    this in 'g'.code..'h'.code -> {
      6
    }
    this == 'i'.code -> {
      2
    }
    this == 'j'.code -> {
      6
    }
    this == 'k'.code -> {
      5
    }
    this == 'l'.code -> {
      3
    }
    this in 'm'.code..'s'.code -> {
      6
    }
    this == 't'.code -> {
      4
    }
    this in 'u'.code..'z'.code -> {
      6
    }
    this in 'A'.code..'H'.code -> {
      6
    }
    this == 'I'.code -> {
      4
    }
    this in 'J'.code..'Z'.code -> {
      6
    }
    this in '0'.code..'9'.code -> {
      6
    }
    this == '\''.code -> {
      2
    }
    this == '"'.code -> {
      4
    }
    this == '.'.code -> {
      2
    }
    this == ','.code -> {
      2
    }
    this == '!'.code -> {
      2
    }
    this == '¡'.code -> {
      2
    }
    this == '?'.code -> {
      6
    }
    this == '¿'.code -> {
      6
    }
    this == '&'.code -> {
      6
    }
    this == '@'.code -> {
      7
    }
    this == '#'.code -> {
      6
    }
    this == '$'.code -> {
      6
    }
    this == '%'.code -> {
      6
    }
    this == '^'.code -> {
      6
    }
    this == ':'.code -> {
      2
    }
    this == ';'.code -> {
      2
    }
    this == '('.code -> {
      4
    }
    this == ')'.code -> {
      4
    }
    this == '['.code -> {
      4
    }
    this == ']'.code -> {
      4
    }
    this == '/'.code -> {
      6
    }
    this == '\\'.code -> {
      6
    }
    this == '‘'.code -> {
      3
    }
    this == '’'.code -> {
      3
    }
    this == '“'.code -> {
      5
    }
    this == '”'.code -> {
      5
    }
    this == '„'.code -> {
      5
    }
    this == '<'.code -> {
      5
    }
    this == '>'.code -> {
      5
    }
    this == '*'.code -> {
      4
    }
    this == '+'.code -> {
      6
    }
    this == '='.code -> {
      6
    }
    this == '_'.code -> {
      6
    }
    this == '-'.code -> {
      6
    }
    // en dash.
    this == '–'.code -> {
      7
    }
    // em dash.
    this == '—'.code -> {
      9
    }
    // oghman space mark.
    this == ' '.code -> {
      9
    }
    this == '…'.code -> {
      8
    }
    this == 'Á'.code -> {
      6
    }
    this == 'á'.code -> {
      6
    }
    this == 'É'.code -> {
      6
    }
    this == 'é'.code -> {
      6
    }
    this == 'Í'.code -> {
      4
    }
    this == 'í'.code -> {
      3
    }
    this == 'Ó'.code -> {
      6
    }
    this == 'ó'.code -> {
      6
    }
    this == 'Ñ'.code -> {
      6
    }
    this == 'ñ'.code -> {
      6
    }
    this == 'Ú'.code -> {
      6
    }
    this == 'ú'.code -> {
      6
    }
    this == 'Ü'.code -> {
      6
    }
    this == 'ü'.code -> {
      6
    }
    this == '~'.code -> {
      7
    }
    this == '«'.code -> {
      7
    }
    this == '»'.code -> {
      7
    }
    this == '©'.code -> {
      8
    }
    this == '®'.code -> {
      8
    }
    this == 'ø'.code -> {
      6
    }
    this == '£'.code -> {
      6
    }
    this == '¢'.code -> {
      6
    }
    this == 'Ø'.code -> {
      6
    }
    this == 'Ą'.code -> {
      6
    }
    this == 'ą'.code -> {
      6
    }
    this == 'Ż'.code -> {
      6
    }
    this == 'ż'.code -> {
      6
    }
    this == 'Ę'.code -> {
      6
    }
    this == 'ę'.code -> {
      6
    }
    this == 'Ẽ'.code -> {
      6
    }
    this == 'ẽ'.code -> {
      6
    }
    this == 'Ł'.code -> {
      7
    }
    this == 'ł'.code -> {
      5
    }
    this == 'Ś'.code -> {
      6
    }
    this == 'ś'.code -> {
      6
    }
    this == 'Ń'.code -> {
      6
    }
    this == 'ń'.code -> {
      6
    }
    this == 'Ź'.code -> {
      6
    }
    this == 'ź'.code -> {
      6
    }
    this == 'Ć'.code -> {
      6
    }
    this == 'ć'.code -> {
      6
    }
    this in 0xFF01..0xFF5E -> {
      9
    }
    this == '。'.code -> {
      9
    }
    this in 0x4E00..0x9FFF -> {
      // Assume all common Chinese characters are supported in Minecraft and are 9-width.
      9
    }
    else -> {
      fun checkBackup(backup: String): Int {
        var i = 0
        while (i < backup.length) {
          val codePoint = backup.codePointAt(i)
          if (this == codePoint) {
            return 10 // Some large enough width.
          }
          i += codePoint.charCount()
        }
        return -1
      }
      checkBackup(asciiBackup).let {
        if (it != -1) return it
      }
      checkBackup(accentedBackup).let {
        if (it != -1) return it
      }
      checkBackup(nonLatinEuropeanBackup).let {
        if (it != -1) return it
      }
      -1
    }
  }
}

private val whitespace = charArrayOf(' ', '\n')

// https://minecraft.fandom.com/wiki/Language#Font

private val asciiBackup = """
  ! " # ${'$'} % & ' ( ) * + , - . /
  0 1 2 3 4 5 6 7 8 9 : ; < = > ?
  @ A B C D E F G H I J K L M N O
  P Q R S T U V W X Y Z [ \ ] ^ _
  ` a b c d e f g h i j k l m n o
  p q r s t u v w x y z { | } ~
  £ ƒ
  ª º ¬ « »
  ░ ▒ ▓ │ ┤ ╡ ╢ ╖ ╕ ╣ ║ ╗ ╝ ╜ ╛ ┐
  └ ┴ ┬ ├ ─ ┼ ╞ ╟ ╚ ╔ ╩ ╦ ╠ ═ ╬ ╧
  ╨ ╤ ╥ ╙ ╘ ╒ ╓ ╫ ╪ ┘ ┌ █ ▄ ▌ ▐ ▀
  ∅ ∈
  ≡ ± ≥ ≤ ⌠ ⌡ ÷ ≈ ° ∙ · √ ⁿ ² ■
""".trimIndent()

private val accentedBackup = """
  À Á Â Ã Ä Å Æ Ç È É Ê Ë Ì Í Î Ï
  Ð Ñ Ò Ó Ô Õ Ö Ù Ú Û Ü Ý à á â ã
  ä å æ ç ì í î ï ñ ò ó ô õ ö ù ú
  û ü ý ÿ Ā ā Ă ă Ą ą Ć ć Ĉ ĉ Ċ ċ
  Č č Ď ď Đ đ Ē ē Ĕ ĕ Ė ė Ę ę Ě ě
  Ĝ ĝ Ḡ ḡ Ğ ğ Ġ ġ Ģ ģ Ĥ ĥ Ħ ħ Ĩ ĩ
  Ī ī Ĭ ĭ Į į İ ı Ĵ ĵ Ķ ķ Ĺ ĺ Ļ ļ
  Ľ ľ Ŀ ŀ Ł ł Ń ń Ņ ņ Ň ň Ŋ ŋ Ō ō
  Ŏ ŏ Ő ő Œ œ Ŕ ŕ Ŗ ŗ Ř ř Ś ś Ŝ ŝ
  Ş ş Š š Ţ ţ Ť ť Ŧ ŧ Ũ ũ Ū ū Ŭ ŭ
  Ů ů Ű ű Ų ų Ŵ ŵ Ŷ ŷ Ÿ Ź ź Ż ż Ž
  ž Ǽ ǽ Ǿ ǿ Ș ș Ț ț Ά Έ Ή Ί Ό Ύ Ώ
  ΐ Ϊ Ϋ ά έ ή ί ΰ ϊ ϋ ό ύ ώ Ѐ Ё Ѓ
  Ї Ќ Ѝ Ў Й й ѐ ё ђ ѓ ї ћ ќ ѝ ў џ
  Ґ ґ Ḃ ḃ Ḋ ḋ Ḟ ḟ Ḣ ḣ Ḱ ḱ Ṁ ṁ Ṗ ṗ
  Ṡ ṡ Ṫ ṫ Ẁ ẁ Ẃ ẃ Ẅ ẅ Ỳ ỳ è é ê ë
  ŉ ǧ ǫ Џ ḍ ḥ ṛ ṭ Ẓ Ị ị Ọ ọ Ụ ụ №
  ȇ Ɣ ɣ ʃ ⁇ Ǳ ǲ ǳ Ǆ ǅ ǆ Ǉ ǈ Ǌ ǋ ǌ
  ℹ ᵫ Ꜳ ꜳ Ꜵ ꜵ Ꜷ ꜷ Ꜹ Ꜻ Ꜽ ꜽ Ꝏ ꝏ Ꝡ ꝡ
  ﬄ ﬆ ᚡ ᚵ Ơ ơ Ư ư Ắ ắ Ấ ấ Ế ế ố Ớ
  ớ Ứ ứ Ằ ằ Ầ ầ Ề ề ồ Ờ ờ Ừ ừ Ả ả
  Ẳ ẳ Ẩ ẩ Ẻ ẻ ổ Ở Ể ể Ỉ ỉ Ỏ ỏ Ổ ở
  Ủ ủ Ử ử Ỷ ỷ Ạ ạ Ặ ặ Ậ ậ Ẹ ẹ Ệ ệ
  Ộ ộ Ợ ợ Ự ự Ỵ ỵ Ố ƕ Ẫ ẫ Ỗ ỗ ữ ☞
  ☜ ☮ Ẵ ẵ Ẽ ẽ Ễ ễ Ồ Ỡ ỡ Ữ Ỹ ỹ Ҙ ҙ
  Ҡ ҡ Ҫ ҫ Ƕ ⚠ ⓪ ① ② ③ ④ ⑤ ⑥ ⑦ ⑧ ⑨
  ⑩ ⑪ ⑫ ⑬ ⑭ ⑮ ⑯ ⑰ ⑱ ⑲ ⑳ Ⓐ Ⓑ Ⓒ Ⓓ Ⓔ
  Ⓕ Ⓖ Ⓗ Ⓘ Ⓙ Ⓚ Ⓛ Ⓜ Ⓝ Ⓞ Ⓟ Ⓠ Ⓡ Ⓢ Ⓣ Ⓤ
  Ⓥ Ⓦ Ⓧ Ⓨ Ⓩ ⓐ ⓑ ⓒ ⓓ ⓔ ⓕ ⓖ ⓗ ⓘ ⓙ ⓚ
  ⓛ ⓜ ⓝ ⓞ ⓟ ⓠ ⓡ ⓢ ⓣ ⓤ ⓥ ⓦ ⓧ ⓨ ⓩ ̧
  ʂ ʐ ɶ Ǎ ǎ Ǟ ǟ Ǻ ǻ Ȃ ȃ Ȧ ȧ Ǡ ǡ Ḁ
  ḁ Ȁ ȁ Ḇ ḇ Ḅ ḅ ᵬ Ḉ ḉ Ḑ ḑ Ḓ ḓ Ḏ ḏ
  Ḍ ᵭ Ḕ ḕ Ḗ ḗ Ḙ ḙ Ḝ ḝ Ȩ ȩ Ḛ ḛ Ȅ ȅ
  Ȇ ᵮ Ǵ ǵ Ǧ Ḧ ḧ Ḩ ḩ Ḫ ḫ Ȟ ȟ Ḥ ẖ Ḯ
  ḯ Ȋ ȋ Ǐ ǐ Ȉ ȉ Ḭ ḭ ǰ ȷ Ǩ ǩ Ḳ ḳ Ḵ
  ḵ Ḻ ḻ Ḽ ḽ Ḷ ḷ Ḹ ḹ Ɫ Ḿ ḿ Ṃ ṃ ᵯ Ṅ
  ṅ Ṇ ṇ Ṋ ṋ Ǹ ǹ Ṉ ṉ ᵰ Ǭ ǭ Ȭ ȭ Ṍ ṍ
  Ṏ ṏ Ṑ ṑ Ṓ ṓ Ȏ ȏ Ȫ ȫ Ǒ ǒ Ȯ ȯ Ȱ ȱ
  Ȍ ȍ Ǫ Ṕ ṕ ᵱ Ȓ ȓ Ṙ ṙ Ṝ ṝ Ṟ ṟ Ȑ ȑ
  Ṛ ᵳ ᵲ Ṥ ṥ Ṧ ṧ Ṣ ṣ Ṩ ṩ ᵴ Ṱ ṱ Ṯ ṯ
  Ṭ ẗ ᵵ Ṳ ṳ Ṷ ṷ Ṹ ṹ Ṻ ṻ Ǔ ǔ Ǖ ǖ Ǘ
  ǘ Ǚ ǚ Ǜ ǜ Ṵ ṵ Ȕ ȕ Ȗ Ṿ ṿ Ṽ ṽ Ẇ ẇ
  Ẉ ẉ ẘ Ẍ ẍ Ẋ ẋ Ȳ ȳ Ẏ ẏ ẙ Ẕ ẕ Ẑ ẑ
  ẓ ᵶ Ǯ ǯ ẛ Ꜿ ꜿ Ǣ ǣ ᵺ ỻ ᴂ ᴔ ꭣ ȸ ʣ
  ʥ ʤ ʩ ʪ ʫ ȹ ʨ ʦ ʧ ꭐ ꭑ ₧ Ỻ אַ אָ ƀ
  Ƃ ƃ Ƈ ƈ Ɗ Ƌ ƌ Ɠ Ǥ ǥ Ɨ Ɩ ɩ Ƙ ƙ Ɲ
  Ƥ ƥ ɽ Ʀ Ƭ ƭ ƫ Ʈ ȗ Ʊ Ɯ Ƴ ƴ Ƶ ƶ Ƣ
  ƣ Ȣ ȣ ʭ ʮ ʯ ﬔ ﬕ ﬗ ﬖ ﬓ Ӑ ӑ Ӓ ӓ Ӷ
  ӷ Ҕ ҕ Ӗ ӗ Ҽ ҽ Ҿ ҿ Ӛ ӛ Ӝ ӝ Ӂ ӂ Ӟ
  ӟ Ӣ ӣ Ӥ ӥ Ӧ ӧ Ӫ ӫ Ӱ ӱ Ӯ ӯ Ӳ ӳ Ӵ
  ӵ Ӹ ӹ Ӭ ӭ Ѷ ѷ Ӕ Ӻ Ԃ Ꚃ Ꚁ Ꚉ Ԫ Ԭ Ꚅ
  Ԅ Ԑ Ӡ Ԇ Ҋ Ӄ Ҟ Ҝ Ԟ Ԛ Ӆ Ԯ Ԓ Ԡ Ԉ Ԕ
  Ӎ Ӊ Ԩ Ӈ Ҥ Ԣ Ԋ Ҩ Ԥ Ҧ Ҏ Ԗ Ԍ Ꚑ Ҭ Ꚋ
  Ꚍ Ԏ Ҳ Ӽ Ӿ Ԧ Ꚕ Ҵ Ꚏ Ҷ Ӌ Ҹ Ꚓ Ꚗ Ꚇ Ҍ
  Ԙ Ԝ ӕ ӻ ԃ ꚃ ꚁ ꚉ ԫ ԭ ꚅ ԅ ԑ ӡ ԇ ҋ
  ӄ ҟ ҝ ԟ ԛ ӆ ԯ ԓ ԡ ԉ ԕ ӎ ӊ ԩ ӈ ҥ
  ԣ ԋ ҩ ԥ ҧ ҏ ԗ ԍ ꚑ ҭ ꚋ ꚍ ԏ ҳ ӽ ӿ
  ԧ ꚕ ҵ ꚏ ҷ ӌ ҹ ꚓ ꚗ ꚇ ҍ ԙ ԝ Ἀ ἀ Ἁ
  ἁ Ἂ ἂ Ἃ ἃ Ἄ ἄ Ἅ ἅ Ἆ ἆ Ἇ ἇ Ὰ ὰ Ᾰ
  ᾰ Ᾱ ᾱ Ά ά ᾈ ᾀ ᾉ ᾁ ᾊ ᾂ ᾋ ᾃ ᾌ ᾄ ᾍ
  ᾅ ᾎ ᾆ ᾏ ᾇ ᾼ ᾴ ᾶ ᾷ ᾲ ᾳ Ἐ ἐ Ἑ ἑ Ἒ
  ἒ Ἓ ἓ Ἔ ἔ Ἕ ἕ Ὲ Έ ὲ έ Ἠ ἠ Ὴ ὴ Ἡ
  ἡ Ἢ ἢ Ἣ ἣ Ἤ ἤ Ἥ ἥ Ἦ ἦ Ἧ ἧ ᾘ ᾐ ᾙ
  ᾑ ᾚ ᾒ ᾛ ᾓ ᾜ ᾔ ᾝ ᾕ ᾞ ᾖ ᾟ ᾗ Ή ή ῌ
  ῃ ῂ ῄ ῆ ῇ Ὶ ὶ Ί ί Ἰ ἰ Ἱ ἱ Ἲ ἲ Ἳ
  ἳ Ἴ ἴ Ἵ ἵ Ἶ ἶ Ἷ ἷ Ῐ ῐ Ῑ ῑ ῒ ΐ ῖ
  ῗ Ὸ ὸ Ό ό Ὀ ὀ Ὁ ὁ Ὂ ὂ Ὃ ὃ Ὄ ὄ Ὅ
  ὅ Ῥ ῤ ῥ Ὺ ὺ Ύ ύ Ὑ ὑ Ὓ ὓ Ὕ ὕ Ὗ ὗ
  Ῠ ῠ Ῡ ῡ ϓ ϔ ῢ ΰ ῧ ὐ ὒ ὔ ῦ ὖ Ὼ ὼ
  Ώ ώ Ὠ ὠ Ὡ ὡ Ὢ ὢ Ὣ ὣ Ὤ ὤ Ὥ ὥ Ὦ ὦ
  Ὧ ὧ ᾨ ᾠ ᾩ ᾡ ᾪ ᾢ ᾫ ᾣ ᾬ ᾤ ᾭ ᾥ ᾮ ᾦ
  ᾯ ᾧ ῼ ῳ ῲ ῴ ῶ ῷ ☯ ☐ ☑ ☒ ƍ ƺ Ȿ ȿ
  Ɀ ɀ ᶀ Ꞔ ꞔ ᶁ ᶂ ᶃ ꞕ ᶄ ᶅ ᶆ ᶇ ᶈ ᶉ ᶊ
  ᶋ ᶌ ᶍ Ᶎ ᶎ ᶏ ᶐ ᶒ ᶓ ᶔ ᶕ ᶖ ᶗ ᶘ ᶙ ᶚ
  ẚ ⅒ ⅘ ₨ ₯
""".trimIndent()

private val nonLatinEuropeanBackup = """
  ¡ ‰ ­ · ₴ ≠ ¿ × Ø Þ һ ð ø þ Α Β
  Γ Δ Ε Ζ Η Θ Ι Κ Λ Μ Ν Ξ Ο Π Ρ Σ
  Τ Υ Φ Χ Ψ Ω α β γ δ ε ζ η θ ι κ
  λ μ ν ξ ο π ρ ς σ τ υ φ χ ψ ω Ђ
  Ѕ І Ј Љ Њ Ћ А Б В Г Д Е Ж З И К
  Л М Н О П Р С Т У Ф Х Ц Ч Ш Щ Ъ
  Ы Ь Э Ю Я а б в г д е ж з и к л
  м н о п р с т у ф х ц ч ш щ ъ ы
  ь э ю я є ѕ і ј љ њ – — ‘ ’ “ ”
  „ … ⁊ ← ↑ → ↓ ⇄ ＋ Ə ə ɛ ɪ Ү ү Ө
  ө ʻ ˌ ; ĸ ẞ ß ₽ € Ѣ ѣ Ѵ ѵ Ӏ Ѳ ѳ
  ⁰ ¹ ³ ⁴ ⁵ ⁶ ⁷ ⁸ ⁹ ⁺ ⁻ ⁼ ⁽ ⁾ ⁱ ™
  ʔ ʕ ⧈ ⚔ ☠ Қ қ Ғ ғ Ұ ұ Ә ә Җ җ Ң
  ң Һ א ב ג ד ה ו ז ח ט י כ ל מ ם
  נ ן ס ע פ ף צ ץ ק ר ¢ ¤ ¥ © ® µ
  ¶ ¼ ½ ¾ · ‐ ‚ † ‡ • ‱ ′ ″ ‴ ‵ ‶
  ‷ ‹ › ※ ‼ ‽ ⁂ ⁈ ⁉ ⁋ ⁎ ⁏ ⁑ ⁒ ⁗ ℗
  − ∓ ∞ ☀ ☁ ☈ Є ☲ ☵ ☽ ♀ ♂ ⚥ ♠ ♣ ♥
  ♦ ♩ ♪ ♫ ♬ ♭ ♮ ♯ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ʬ ⚡
  ⛏ ✔ ❄ ❌ ❤ ⭐ ⸘ ⸮ ⸵ ⸸ ⹁ ⹋ ⥝ ᘔ Ɛ ߈
  ϛ ㄥ Ɐ ᗺ Ɔ ᗡ Ǝ Ⅎ ⅁ Ʞ Ꞁ Ԁ Ꝺ ᴚ ⟘ ∩
  Ʌ ⅄ ɐ ɔ ǝ ɟ ᵷ ɥ ᴉ ɾ ʞ ꞁ ɯ ɹ ʇ ʌ
  ʍ ʎ Ա Բ Գ Դ Զ Է Թ Ժ Ի Լ Խ Ծ Կ Հ
  Ձ Ղ Ճ Մ Յ Ն Շ Ո Չ Ջ Ռ Ս Վ Տ Ր Ց
  Ւ Փ Ք Օ Ֆ ՙ ա բ գ դ ե զ է ը թ ժ
  ի լ խ ծ կ հ ձ ղ ճ մ յ ն շ ո չ պ
  ջ ռ ս վ տ ր ց ւ փ ք օ ֆ և ש ת Ը
  ՚ ՛ ՜ ՝ ՞ ՟ ՠ ֈ ֏ ¯ ſ Ʒ ʒ Ƿ ƿ Ȝ
  ȝ Ȥ ȥ ˙ Ꝛ ꝛ ‑ ⅋ ⏏ ⏩ ⏪ ⏭ ⏮ ⏯ ⏴ ⏵
  ⏶ ⏷ ⏸ ⏹ ⏺ ⏻ ⏼ ⏽ ⭘ ▲ ▶ ▼ ◀ ● ◦ ◘
  ⚓ ⛨ Ĳ ĳ ǉ Ꜩ ꜩ ꜹ ꜻ ﬀ ﬁ ﬂ ﬃ ﬅ � Ե
  Պ ᚠ ᚢ ᚣ ᚤ ᚥ ᚦ ᚧ ᚨ ᚩ ᚪ ᚫ ᚬ ᚭ ᚮ ᚯ
  ᚰ ᚱ ᚲ ᚳ ᚴ ᚶ ᚷ ᚸ ᚹ ᚺ ᚻ ᚼ ᚽ ᚾ ᚿ ᛀ
  ᛁ ᛂ ᛃ ᛄ ᛅ ᛆ ᛇ ᛈ ᛉ ᛊ ᛋ ᛌ ᛍ ᛎ ᛏ ᛐ
  ᛑ ᛒ ᛓ ᛔ ᛕ ᛖ ᛗ ᛘ ᛙ ᛚ ᛛ ᛜ ᛝ ᛞ ᛟ ᛠ
  ᛡ ᛢ ᛣ ᛤ ᛥ ᛦ ᛧ ᛨ ᛩ ᛪ ᛫ ᛬ ᛭ ᛮ ᛯ ᛰ
  ᛱ ᛲ ᛳ ᛴ ᛵ ᛶ ᛷ ᛸ ☺ ☻ ¦ ☹ ך ׳ ״ װ
  ױ ײ ־ ׃ ׆ ´ ¨ ᴀ ʙ ᴄ ᴅ ᴇ ꜰ ɢ ʜ ᴊ
  ᴋ ʟ ᴍ ɴ ᴏ ᴘ ꞯ ʀ ꜱ ᴛ ᴜ ᴠ ᴡ ʏ ᴢ §
  ɱ ɳ ɲ ʈ ɖ ɡ ʡ ɕ ʑ ɸ ʝ ʢ ɻ ʁ ɦ ʋ
  ɰ ɬ ɮ ʘ ǀ ǃ ǂ ǁ ɓ ɗ ᶑ ʄ ɠ ʛ ɧ ɫ
  ɨ ʉ ʊ ɘ ɵ ɤ ɜ ɞ ɑ ɒ ɚ ɝ Ɓ Ɖ Ƒ Ʃ
  Ʋ Ⴀ Ⴁ Ⴂ Ⴃ Ⴄ Ⴅ Ⴆ Ⴇ Ⴈ Ⴉ Ⴊ Ⴋ Ⴌ Ⴍ Ⴎ
  Ⴏ Ⴐ Ⴑ Ⴒ Ⴓ Ⴔ Ⴕ Ⴖ Ⴗ Ⴘ Ⴙ Ⴚ Ⴛ Ⴜ Ⴝ Ⴞ
  Ⴟ Ⴠ Ⴡ Ⴢ Ⴣ Ⴤ Ⴥ Ⴧ Ⴭ ა ბ გ დ ე ვ ზ
  თ ი კ ლ მ ნ ო პ ჟ რ ს ტ უ ფ ქ ღ
  ყ შ ჩ ც ძ წ ჭ ხ ჯ ჰ ჱ ჲ ჳ ჴ ჵ ჶ
  ჷ ჸ ჹ ჺ ჻ ჼ ჽ ჾ ჿ תּ שׂ פֿ פּ כּ ײַ יִ
  וֹ וּ בֿ בּ ꜧ Ꜧ ɺ ⱱ ʠ ʗ ʖ ɭ ɷ ɿ ʅ ʆ
  ʓ ʚ ₪ ₾ ֊ ⴀ ⴁ ⴂ ⴃ ⴄ ⴅ ⴆ ⴡ ⴇ ⴈ ⴉ
  ⴊ ⴋ ⴌ ⴢ ⴍ ⴎ ⴏ ⴐ ⴑ ⴒ ⴣ ⴓ ⴔ ⴕ ⴖ ⴗ
  ⴘ ⴙ ⴚ ⴛ ⴜ ⴝ ⴞ ⴤ ⴟ ⴠ ⴥ ⅛ ⅜ ⅝ ⅞ ⅓
  ⅔ ✉ ☂ ☔ ☄ ⛄ ☃ ⌛ ⌚ ⚐ ✎ ❣ ♤ ♧ ♡ ♢
  ⛈ ☰ ☱ ☳ ☴ ☶ ☷ ↔ ⇒ ⇏ ⇔ ⇵ ∀ ∃ ∄ ∉
  ∋ ∌ ⊂ ⊃ ⊄ ⊅ ∧ ∨ ⊻ ⊼ ⊽ ∥ ≢ ⋆ ∑ ⊤
  ⊥ ⊢ ⊨ ≔ ∁ ∴ ∵ ∛ ∜ ∂ ⋃ ⊆ ⊇ □ △ ▷
  ▽ ◁ ◆ ◇ ○ ◎ ☆ ★ ✘ ₀ ₁ ₂ ₃ ₄ ₅ ₆
  ₇ ₈ ₉ ₊ ₋ ₌ ₍ ₎ ∫ ∮ ∝ ⌀ ⌂ ⌘ 〒 ɼ
  Ƅ ƅ ẟ Ƚ ƚ ƛ Ƞ ƞ Ɵ Ƨ ƨ ƪ Ƹ ƹ ƻ Ƽ
  ƽ ƾ ȡ ȴ ȵ ȶ Ⱥ ⱥ Ȼ ȼ Ɇ ɇ Ⱦ ⱦ Ɂ ɂ
  Ƀ Ʉ Ɉ ɉ Ɋ ɋ Ɍ ɍ Ɏ ɏ ẜ ẝ Ỽ ỽ Ỿ ỿ
  Ꞩ ꞩ 𐌰 𐌱 𐌲 𐌳 𐌴 𐌵 𐌶 𐌷 𐌸 𐌹 𐌺 𐌻 𐌼 𐌽
  𐌾 𐌿 𐍀 𐍁 𐍂 𐍃 𐍄 𐍅 𐍆 𐍇 𐍈 𐍉 𐍊 🌧 🔥 🌊
  ⅐ ⅑ ⅕ ⅖ ⅗ ⅙ ⅚ ⅟ ↉ 🗡 🏹 🪓 🔱 🎣 🧪 ⚗
  ⯪ ⯫ Ɑ 🛡 ✂ 🍖 🪣 🔔 ⏳ ⚑ ₠ ₡ ₢ ₣ ₤ ₥
  ₦ ₩ ₫ ₭ ₮ ₰ ₱ ₲ ₳ ₵ ₶ ₷ ₸ ₹ ₺ ₻
  ₼ ₿
""".trimIndent()
