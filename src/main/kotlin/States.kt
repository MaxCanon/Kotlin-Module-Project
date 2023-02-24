sealed interface States {
    object ArchivesMenu : States
    class NotesMenu(val item: Archive) : States
    object ArchiveCreate: States
    class NoteCreate(val item: Archive): States
    class NoteOpen(val item: Note) : States
}