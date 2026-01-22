package dam.jcpf.rickandmorty

data class Episodes (
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Episodes

        if (id != other.id) return false
        if (name != other.name) return false
        if (air_date != other.air_date) return false
        if (episode != other.episode) return false
        if (!characters.contentEquals(other.characters)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + air_date.hashCode()
        result = 31 * result + episode.hashCode()
        result = 31 * result + characters.contentHashCode()
        return result
    }
}