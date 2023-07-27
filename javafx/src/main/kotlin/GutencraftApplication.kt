import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos.CENTER_LEFT
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
import javafx.scene.layout.HBox
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
    val printBar = HBox(10.0).apply { alignment = CENTER_LEFT }
    val printJava = Button("Print for Java Edition")
    val orLabel = Label("or")
    val printBedrock = Button("Print for Bedrock Edition")
    printBar.children.add(printJava)
    printBar.children.add(orLabel)
    printBar.children.add(printBedrock)

    fun printResult(javaEdition: Boolean) {
      layout.children.remove(2, layout.children.size)
      val inputText = input.text
      val pages: List<String>
      try {
        pages = if (javaEdition) {
          pagesJava(inputText)
        } else {
          pagesBedrock(inputText)
        }
      } catch (e: UnsupportedCharacterException) {
        val errorMessage = errorMessage(inputText)
        val errorLabel = Label(errorMessage)
        errorLabel.textFill = Color.color(1.0, 0.0, 0.0)
        layout.children.add(errorLabel)
        return
      }
      val command = command(pages)
      layout.addItem(command, "Copy command")
      for (i in pages.indices) {
        val page = pages[i]
        layout.addItem(page, "${i + 1}. Copy")
      }
    }

    printJava.setOnAction {
      printResult(true)
      printJava.select()
      printBedrock.deselect()
    }
    printBedrock.setOnAction {
      printResult(false)
      printJava.deselect()
      printBedrock.select()
    }
    layout.children.add(input)
    layout.children.add(printBar)
    primaryStage.scene = Scene(scrollPane, 600.0, 300.0)
    primaryStage.show()
  }

  private fun VBox.addItem(content: String, copyLabel: String) {
    addLabel(content)
    addCopyButton(copyLabel, content)
  }

  private fun VBox.addLabel(label: String) {
    children.add(Label(label).apply {
      background =
        Background(
          BackgroundFill(
            Color.rgb(200, 200, 200),
            CornerRadii(0.0),
            Insets(0.0)
          )
        )
    })
  }

  private fun VBox.addCopyButton(label: String, copy: String) {
    children.add(Button(label).apply {
      setOnAction {
        val clipboard = Clipboard.getSystemClipboard()
        val content = ClipboardContent()
        content.putString(copy)
        clipboard.setContent(content)
      }
    })
  }

  private fun Button.select() {
    style = "-fx-base: #4EB02D;"
  }

  private fun Button.deselect() {
    style = ""
  }
}
