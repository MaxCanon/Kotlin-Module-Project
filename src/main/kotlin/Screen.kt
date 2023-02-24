import java.util.Scanner

object Screen {
    private val archive: MutableList<Archive> = mutableListOf()
    private val scan = Scanner(System.`in`)

    fun showScreen(states: States) {
        when (states) {
            is States.ArchivesMenu -> {
                printMenu(archive, "архив", "архивов")
                val vvod = input(archive)
                processResult(vvod, archive, {showScreen(States.ArchiveCreate)}, {showScreen(States.NotesMenu(archive[vvod -1]))}, {exit()})
            }
            is States.NotesMenu -> {
                printMenu(states.item.notes, "заметку", "заметок")
                val vvod = input(states.item.notes)
                processResult(vvod, states.item.notes, {showScreen(States.NoteCreate(states.item))}, {showScreen(States.NoteOpen(states.item.notes[vvod - 1]))}, {showScreen(States.ArchivesMenu)})
            }
            is States.ArchiveCreate -> {
                println("Введите название архива: ")
                val vvod = scan.nextLine()
                archive.add(Archive(vvod))
                println("Архив $vvod создан\n")
                showScreen(States.ArchivesMenu)
            }
            is States.NoteCreate -> {
                println("Введите название заметки: ")
                val vvod = scan.nextLine()
                states.item.notes.add(Note(vvod, states.item))
                println("заметка добавлена")
                showScreen(States.NotesMenu(states.item))
            }
            is States.NoteOpen -> {
                while (true) {
                    println("${states.item.getItem()}\nДля выхода введите 0 .")
                    val vvod = scan.nextLine()
                    if (vvod == "0") {
                        showScreen(States.NotesMenu(states.item.archive))
                        break
                    } else {
                        continue
                    }

                }
            }
        }
    }
    private fun printMenu(list: List<ReturnToString>, text: String, text2: String) {
        println("Список $text2\n0. Создать $text")
        list.forEachIndexed{ index, value ->
            println("${index + 1}. ${value.getItem()}")
        }
        println("${list.size + 1}. Выход")
    }
    private fun input(list: List<ReturnToString>) : Int {
        while (true) {
            try {
                val vvod = scan.nextLine().toInt()
                if (vvod < 0) {
                    println("Вы ввели неверное число.")
                } else if (vvod > list.size + 1) {
                    println("Введите числа от 0 до ${list.size + 1}.")
                } else {
                    return vvod
                }
            }
          catch (e: Exception){
              println("Пожалуйста, введите число от 0 до ${list.size + 1}.")
          }
        }
    }
    private fun processResult (vvod: Int, list: List<ReturnToString>, create: () -> Unit, show: (Int) -> Unit, exit: () -> Unit) {
        if (vvod == 0) {
            create()
        } else if (vvod > 0 && vvod <= list.size){
            show(vvod - 1)
        } else if (vvod == list.size + 1){
            exit()
        }
    }
    private fun exit() {
        println("Программа завершена")
        return
    }
}

