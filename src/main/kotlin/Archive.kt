open class Archive(val name: String) : ReturnToString {
    val notes = mutableListOf<Note>()
    override fun getItem() = name
}