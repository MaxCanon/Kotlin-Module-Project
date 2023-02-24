class Note(val text: String, val archive: Archive) : ReturnToString {
    override fun getItem() = text
}