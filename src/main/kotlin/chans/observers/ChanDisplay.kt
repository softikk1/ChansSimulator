package chans.observers

import chans.ChansRepository
import registrationModel
import java.awt.Dimension
import java.awt.Font
import java.awt.Insets
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea

// Состояние, которое меняется при изменении другого состояния.
class ChanDisplay {
    private val textArea = JTextArea().apply {
        isEditable = false
        text = "Hello, world!"
        font = Font(Font.MONOSPACED, Font.BOLD, 16)
        margin = Insets(16, 16, 16, 16)
    }
    val jFrame = JFrame().apply {
        title = "Chans"
        isVisible = true
        size = Dimension(800, 600)
        isResizable = false
        add(JScrollPane(textArea))
    }

    fun show() {
        // Реализация наблюдателя.
        ChansRepository.getInstance(registrationModel).mutableObservableChans.registerObserver { value ->
            textArea.text = value.joinToString(",\n") {
                "${it.id} - [name] ${it.name}, [age] ${it.age}, [height] ${it.height}, [tsundere] ${if (it.tsundere) "Да" else "Нет"}"
            }
        }
    }
}