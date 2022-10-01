fun errorMessage(text: String): String {
  val unsupportedCharacters = LinkedHashSet<Int>()
  for (character in text) {
    if (!character.isSupportedCharacter()) {
      unsupportedCharacters += character
    }
  }
  val unsupportedCharactersSize = unsupportedCharacters.size
  return if (unsupportedCharactersSize == 1) {
    "Unsupported character (if Minecraft supports this character please email me at eric@nightlynexus.com):\n${
      unsupportedCharacters.first().codePointToErrorMessage()
    }"
  } else {
    val errorMessagePrefix =
      "Unsupported characters (if Minecraft supports any of these characters please email me at eric@nightlynexus.com):"
    val errorMessage = StringBuilder(errorMessagePrefix.length + unsupportedCharactersSize * 2)
      .append(errorMessagePrefix)
    for (unsupportedCharacter in unsupportedCharacters) {
      errorMessage
        .append('\n')
        .append(unsupportedCharacter.codePointToErrorMessage())
    }
    errorMessage.toString()
  }
}

private fun Int.codePointToErrorMessage(): String {
  if (this == '\t'.code) {
    return "Tab character. Consider replacing with repeated spaces."
  }
  // Assume this covers all whitespace characters.
  if (charCount() == 1 && toChar().isWhitespace()) {
    return "A whitespace character (code point: $this). Consider replacing with spaces."
  }
  return codePointToString()
}

private operator fun String.iterator(): IntIterator = object : IntIterator() {
  private var index = 0

  override fun nextInt() = codePointAt(index).also {
    index += it.charCount()
  }

  override fun hasNext(): Boolean = index < length
}
