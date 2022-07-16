import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextArea
import javafx.scene.image.Image
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage

fun main() {
  Application.launch(GutencraftApplication::class.java)
}

class GutencraftApplication : Application() {
  override fun start(primaryStage: Stage) {
    primaryStage.title = "Gutencraft"
    primaryStage.icons += Image(
      GutencraftApplication::class.java.getResourceAsStream("book and quill.png")
    )
    val scrollPane = ScrollPane()
    val layout = VBox(10.0)
    scrollPane.content = layout
    val input = TextArea()
    val print = Button("Print")
    print.setOnAction {
      layout.children.remove(2, layout.children.size)
      val inputText = input.text
      val pages: List<String>
      try {
        pages = pages(inputText)
      } catch (e: UnsupportedCharacterException) {
        val errorMessage = errorMessage(inputText)
        val errorLabel = Label(errorMessage)
        errorLabel.textFill = Color.color(1.0, 0.0, 0.0)
        layout.children.add(errorLabel)
        return@setOnAction
      }
      for (i in pages.indices) {
        val page = pages[i]
        layout.children.add(Label(page).apply {
          background =
            Background(
              BackgroundFill(
                Color.rgb(200, 200, 200),
                CornerRadii(0.0),
                Insets(0.0)
              )
            )
        })
        layout.children.add(Button("${i + 1}. Copy").apply {
          setOnAction {
            val clipboard = Clipboard.getSystemClipboard()
            val content = ClipboardContent()
            content.putString(page)
            clipboard.setContent(content)
          }
        })
      }
    }
    layout.children.add(input)
    layout.children.add(print)
    primaryStage.scene = Scene(scrollPane, 600.0, 300.0)
    primaryStage.show()
  }

  private fun errorMessage(text: String): String {
    val unsupportedCharacters = LinkedHashSet<Int>()
    for (character in text) {
      if (!character.isSupportedCharacter()) {
        unsupportedCharacters += character
      }
    }
    val unsupportedCharactersSize = unsupportedCharacters.size
    return if (unsupportedCharactersSize == 1) {
      "Unsupported character (if Minecraft supports this character please email me at eric@nightlynexus.com):\n${
        unsupportedCharacters.first().codePointToString()
      }"
    } else {
      val errorMessagePrefix =
        "Unsupported characters (if Minecraft supports any of these characters please email me at eric@nightlynexus.com):"
      val errorMessage = StringBuilder(errorMessagePrefix.length + unsupportedCharactersSize * 2)
        .append(errorMessagePrefix)
      for (unsupportedCharacter in unsupportedCharacters) {
        errorMessage
          .append('\n')
          .append(unsupportedCharacter.codePointToString())
      }
      errorMessage.toString()
    }
  }

  private fun Int.codePointToString(): String {
    if (this == '\t'.code) {
      return "Tab character. Consider replacing with repeated spaces."
    }
    if (Character.isWhitespace(this)) {
      return "A whitespace character (code point: $this). Consider replacing with spaces."
    }
    return String(IntArray(1) { this }, 0, 1)
  }

  private operator fun String.iterator(): IntIterator = object : IntIterator() {
    private var index = 0

    override fun nextInt() = codePointAt(index).also {
      index += Character.charCount(it)
    }

    override fun hasNext(): Boolean = index < length
  }
}
